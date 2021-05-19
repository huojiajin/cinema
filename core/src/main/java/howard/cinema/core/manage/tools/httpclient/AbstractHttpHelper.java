package howard.cinema.core.manage.tools.httpclient;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.logging.log4j.core.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class AbstractHttpHelper extends AbstractHelper
{
	// protected static SSLConnectionSocketFactory ssf;
	// private static Logger logger = LoggerFactory.getLogger(AbstractHelper.class);
	// protected static Map<String, CloseableHttpClient> clients = new ConcurrentHashMap<>();
	// private static final String HTTP_RESPONSE_INFO = "httpclient.response-info";
	// private static Object lock = new Object();
	protected static HttpClientService client;

	static
	{
		client = HttpClientServiceBuilder.defaultInstance();
	}

	public static HttpClientService getClient()
	{
		return client;
	}

	protected static URI getURI(String uriStr, HttpServletRequest request) throws URISyntaxException
	{
		URI uri = new URI(uriStr);
		List<NameValuePair> params = params2List(request);
		String schema = uri.getScheme();
		schema = StringUtils.hasText(schema) ? schema : "http";
		URI rsUri = new URIBuilder().setScheme(schema).setHost(uri.getHost()).setPort(uri.getPort())
				.setPath(uri.getPath()).setParameters(params).build();
		return rsUri;
	}

	protected static List<NameValuePair> params2List(HttpServletRequest request)
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		Map<String, String[]> paramMap = request.getParameterMap();
		for (Entry<String, String[]> en : paramMap.entrySet())
		{
			String name = en.getKey();
			String[] values = en.getValue();
			if (Assert.isEmpty(values)) continue;
			for (String value : values)
			{
				if (hasText(value)) params.add(new BasicNameValuePair(name, value));
			}
		}
		return params;
	}

	public static String getFilename(Header header)
	{
		if (header == null || header.getElements() == null || header.getElements().length == 0) return null;
		for (HeaderElement ele : header.getElements())
		{
			if (isEmpty(ele.getParameters())) continue;
			for (NameValuePair nv : ele.getParameters())
			{
				if (nv.getName() != null && nv.getName().toLowerCase().contains("filename")) return nv.getValue();
			}
		}
		return null;
	}

	public static <T> T httpRequest(HttpUriRequest request, int connTimeout, HttpResponseHandler<T> handler,
			int soTimeout) throws IOException
	{
		return httpRequest(request, false, connTimeout, handler, soTimeout);
	}

	public static boolean isContentGziped(HttpResponse response)
	{
		Header header = response.getFirstHeader(HTTP.CONTENT_ENCODING);
		if (header == null) return false;
		String str = header.getValue();
		return str == null ? false : str.toLowerCase().indexOf("gzip") != -1;
	}

	public static <T> T httpRequest(HttpUriRequest request, boolean enableGzip, int connTimeout,
			HttpResponseHandler<T> handler, int soTimeout) throws IOException
	{
		return httpRequest(request, enableGzip, connTimeout, handler, soTimeout, null);
	}

	/**
	 * http请求
	 * 
	 * @param request
	 *            请求
	 * @param connTimeout
	 *            连接超时毫秒数 负数忽略
	 * @param enableGzip
	 *            是否要求服务端启用gzip压缩
	 * @param handler
	 *            返回内容处理的回调接口
	 * @param soTimeout
	 *            响应超时毫秒数 负数忽略
	 * @param interceptor
	 *            请求拦截器
	 * @return 处理结果
	 * @throws IOException
	 */
	public static <T> T httpRequest(HttpUriRequest request, boolean enableGzip, int connTimeout,
			HttpResponseHandler<T> handler, int soTimeout, HttpClientInterceptor interceptor) throws IOException
	{
		HttpRequestPara para = new HttpRequestPara().setRequest(request).setEnableGzip(enableGzip)
				.setConnTimeout(connTimeout).setSoTimeout(soTimeout).setInterceptor(interceptor);
		return httpRequest(para, handler);
	}

	/**
	 * http请求
	 * 
	 * @param para
	 *            请求参数
	 * @param handler
	 *            返回内容处理的回调接口
	 * @return 处理结果
	 * @throws IOException
	 */
	public static <T> T httpRequest(HttpRequestPara para, HttpResponseHandler<T> handler) throws IOException
	{
		return client.httpRequest(para, handler);
	}

	public static void closeAllClients()
	{
		client.closeAll();
	}

	/**
	 * 子类可以通过此方法重建连接池
	 * 
	 * @param para
	 *            请求参数
	 * @param maxConnPerRoute
	 *            每个路由允许的最大连接，默认值是200
	 * @param connTimeToLiveMinutes
	 *            连接的存活时间，默认3分钟
	 * @param maxConnTotal
	 *            连接池允许的最大连接数，默认是600
	 */
	public synchronized void reBuildClient(HttpRequestPara para, int maxConnPerRoute, int connTimeToLiveMinutes,
			int maxConnTotal)
	{
		client.reBuildClient(para, maxConnPerRoute, connTimeToLiveMinutes, maxConnTotal);
	}

}
