package howard.cinema.core.manage.tools.httpclient;

import howard.cinema.core.dao.entity.common.BaseEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.util.Assert;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HttpRequestPara extends BaseEntity
{
	public static final int DEFAULT_CONN_TIMEOUT = 5000;
	public static final int DEFAULT_BUFFER_SIZE = 8192;
	public static final String DEFAULT_ENCODING = "UTF-8";
	/**
	 * @see {@link HttpGet} {@link HttpPost}
	 */
	private HttpUriRequest request;// 请求
	private boolean enableGzip;// 是否启用压缩
	private int connTimeout = DEFAULT_CONN_TIMEOUT;
	private int soTimeout = -1;// 响应超时时间，-1不超时
	private HttpClientInterceptor interceptor;// 拦截器
	private HttpHost proxy;// 代理，为空时不使用代理
	private boolean useShortConnection = false;// 使用短链接

	/**
	 * @param uri
	 *            URI {@link URLEncodedUtils#format(List, String)}
	 * @return
	 */
	public static HttpRequestPara newGetInstance(URI uri)
	{
		Assert.notNull(uri, "uri can not be null.");
		return new HttpRequestPara().setRequest(new HttpGet(uri));
	}

	/**
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public static HttpRequestPara newPostInstance(String url, List<? extends NameValuePair> params) throws IOException
	{
		return newPostInstance(url, DEFAULT_ENCODING, params);
	}

	/**
	 * 
	 * @param url
	 * @param requestEncoding
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public static HttpRequestPara newPostInstance(String url, String requestEncoding,
                                                                                     List<? extends NameValuePair> params) throws IOException
	{
		Assert.notNull(url, "url can not be null.");
		HttpPost post = new HttpPost(url);
		UrlEncodedFormEntity reqEntity = new UrlEncodedFormEntity(params, requestEncoding);
		post.setEntity(reqEntity);
		return new HttpRequestPara().setRequest(post);
	}

	public static HttpRequestPara newJsonPostInstance(String url, String requestJson) throws IOException
	{
		HttpPost post = new HttpPost(url);
		StringEntity reqEntity = new StringEntity(requestJson, ContentType.APPLICATION_JSON);
		post.setEntity(reqEntity);
		return new HttpRequestPara().setRequest(post);
	}

	public HttpUriRequest getRequest()
	{
		return request;
	}

	public boolean isEnableGzip()
	{
		return enableGzip;
	}

	public int getConnTimeout()
	{
		return connTimeout;
	}

	public int getSoTimeout()
	{
		return soTimeout;
	}

	public HttpClientInterceptor getInterceptor()
	{
		return interceptor;
	}

	public boolean isUseShortConnection()
	{
		return useShortConnection;
	}

	public HttpRequestPara setUseShortConnection(boolean useShortConnection)
	{
		this.useShortConnection = useShortConnection;
		return this;
	}

	public HttpHost getProxy()
	{
		return proxy;
	}

	public HttpRequestPara setRequest(HttpUriRequest request)
	{
		this.request = request;
		return this;
	}

	public HttpRequestPara setEnableGzip(boolean enableGzip)
	{
		this.enableGzip = enableGzip;
		return this;
	}

	public HttpRequestPara setConnTimeout(int connTimeout)
	{
		this.connTimeout = connTimeout;
		return this;
	}

	public HttpRequestPara setSoTimeout(int soTimeout)
	{
		this.soTimeout = soTimeout;
		return this;
	}

	public HttpRequestPara setInterceptor(HttpClientInterceptor interceptor)
	{
		this.interceptor = interceptor;
		return this;
	}

	public HttpRequestPara setProxy(HttpHost proxy)
	{
		this.proxy = proxy;
		return this;
	}

	protected boolean isHttps()
	{
		String schema = request.getURI().getScheme();
		return "https".equalsIgnoreCase(schema);
	}

	/**
	 * 可以通过覆盖此方法实现对连接参数的控制
	 * 
	 * @param ssf
	 * @return
	 */
	public HttpClientBuilder toClientBuilder(SSLConnectionSocketFactory ssf)
	{
		return toClientBuilder(ssf, 216, 5, 1121);
	}

	/**
	 * 
	 * @param ssf
	 * @param maxConnPerRoute
	 *            每个路由（服务端）最多的连接数
	 * @param connTimeToLiveMinutes
	 *            持久化连接的存活 时间（分钟）
	 * @param maxConnTotal
	 *            最多保持的连接数
	 * @return
	 */
	public HttpClientBuilder toClientBuilder(SSLConnectionSocketFactory ssf, int maxConnPerRoute,
			int connTimeToLiveMinutes, int maxConnTotal)
	{
		HttpClientBuilder builder = HttpClientBuilder.create();
		builder.setDefaultRequestConfig(toRequestConfig());
		if (isHttps()) builder.setSSLSocketFactory(ssf);// 使用ssl配置
		// 4.3以后会自动在interceptor中实现启用压缩和自动解压，所以不需要gzip的时候需要指定一下
		// if (!enableGzip)
		// builder.disableContentCompression();
		// proxy
		if (proxy != null) builder.setProxy(proxy);
		builder.setMaxConnPerRoute(maxConnPerRoute).setConnectionTimeToLive(connTimeToLiveMinutes, TimeUnit.MINUTES);
		builder.setMaxConnTotal(maxConnTotal);
		builder.evictIdleConnections(3, TimeUnit.MINUTES).evictExpiredConnections();// 关闭空闲连接
		return builder;
	}

	public RequestConfig toRequestConfig()
	{
		RequestConfig.Builder builder = RequestConfig.custom();
		if (connTimeout > 0)
		{
			builder.setConnectTimeout(connTimeout);
			builder.setConnectionRequestTimeout(connTimeout);
		}
		if (soTimeout > 0) builder.setSocketTimeout(soTimeout);
		if (proxy != null) builder.setProxy(proxy);
		builder.setContentCompressionEnabled(enableGzip);
		return builder.build();
	}

	/**
	 * 可以通过覆盖这个方法实现连接的保持方式
	 * 
	 * @return
	 */
	public String clientKey()
	{
		return "" + isHttps() + ((proxy == null) ? "" : ":" + proxy);
	}

}
