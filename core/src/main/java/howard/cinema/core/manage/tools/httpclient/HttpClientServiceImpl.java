package howard.cinema.core.manage.tools.httpclient;

import howard.cinema.core.manage.common.CommonAbstract;
import org.apache.http.*;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.springframework.util.Assert;

import javax.annotation.PreDestroy;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class HttpClientServiceImpl extends AbstractHelper implements HttpClientService
{
	private static final String HTTP_RESPONSE_INFO = "httpclient.response-info";
	protected SSLConnectionSocketFactory ssf;
	protected Map<String, CloseableHttpClient> clients = new ConcurrentHashMap<>();
	private Object lock = new Object();

	public HttpClientServiceImpl(HttpSslConfig config) throws KeyManagementException, NoSuchAlgorithmException
	{
		Assert.notNull(config, "config can not be null.");
		this.ssf = config.toSSLSocketFactory();
	}

	public synchronized void reBuildClient(HttpRequestPara para, int maxConnPerRoute, int connTimeToLiveMinutes,
			int maxConnTotal)
	{
		String clientKey = para.clientKey();
		CloseableHttpClient client = clients.get(clientKey);
		if (client != null)
		{
			closeQuietly(client);
			clients.remove(clientKey);
		}
		prepareClient(para, maxConnPerRoute, connTimeToLiveMinutes, maxConnTotal);
		logger.info("重建HttpClient连接池，key:{} maxConnPerRoute:{} connTimeToLiveMinutes:{} maxConnTotal:{}", clientKey,
				maxConnPerRoute, connTimeToLiveMinutes, maxConnTotal);
	}

	@Override
	public <T> T fileDownload(HttpRequestPara para, FileDownloadHandler<T> handler) throws IOException
	{
		Assert.notNull(handler, "handler must be specified.");
		return httpRequest(para, new HttpResponseHandler<T>() {
			@Override
			public T handle(HttpResponse response, HttpResponseInfo info) throws IOException
			{
				HttpEntity entity = response.getEntity();
				if (entity == null)
					throw new IOException("No response:" + para.getRequest().getURI().toString());
				StatusLine sl = response.getStatusLine();
				int code = sl.getStatusCode();
				if (code >= 400) throw new IOException(
						"Request Error:" + code + "\t" + para.getRequest().getURI().toString());
				boolean gzip = isContentGziped(response);
				HttpEntity resEntity = gzip ? new GzipDecompressingEntity(entity) : entity;
				Header[] headers = response.getAllHeaders();
				Header header = response.getFirstHeader("Content-Disposition");
				InputStream is = resEntity.getContent();
				try
				{
					return handler.handle(is, getFilename(header), headers, para.getRequest().getURI());
				}
				finally
				{
					closeQuietly(is);
				}
			}

		});
	}

	@Override
	public <T> T httpRequest(HttpRequestPara para, HttpResponseHandler<T> handler) throws IOException
	{
		long begin = System.currentTimeMillis();
		Assert.notNull(handler, "handler can not be null.");
		CloseableHttpClient client = prepareClient(para);
		if (para.getInterceptor() != null) para.getInterceptor().process(para.getRequest());
		HttpContext context = new HttpClientContext();
		HttpUriRequest uriRequest = para.getRequest();// 每次請求使用自定義的config
		if (uriRequest instanceof HttpRequestBase)
		{
			((HttpRequestBase) uriRequest).setConfig(para.toRequestConfig());
		}
		CloseableHttpResponse response = client.execute(uriRequest, context);
		try
		{
			HttpResponseInfo info = (HttpResponseInfo) context.getAttribute(HTTP_RESPONSE_INFO);
			T rs = handler.handle(response, info);
			if (logger.isDebugEnabled())
				logger.debug("耗时:{} ms   HttpResponseInfo:{}", (System.currentTimeMillis() - begin), info);
			return rs;
		}
		finally
		{
			closeQuietly(response); 
			if (para.isUseShortConnection()) closeQuietly(client);// 使用短连接时直接关闭
		}
	}

	protected CloseableHttpClient prepareClient(HttpRequestPara para)
	{
		return prepareClient(para, 200, 3, 600);
	}

	protected CloseableHttpClient prepareClient(HttpRequestPara para, int maxConnPerRoute, int connTimeToLiveMinutes,
			int maxConnTotal)
	{
		if (para.isUseShortConnection())
		{
			return newClient(para, 0, -1, 0);
		}
		String clientKey = para.clientKey();
		CloseableHttpClient client = clients.get(clientKey);
		if (client != null) return client;
		synchronized (lock)
		{
			if (clients.get(clientKey) != null) return clients.get(clientKey);
			client = newClient(para, maxConnPerRoute, connTimeToLiveMinutes, maxConnTotal);
			clients.put(clientKey, client);
		}
		return client;
	}

	private CloseableHttpClient newClient(HttpRequestPara para, int maxConnPerRoute, int connTimeToLiveMinutes,
			int maxConnTotal)
	{
		MyHttpResponseInterceptor myInterceptor = new MyHttpResponseInterceptor();
		HttpClientBuilder builder = para.toClientBuilder(ssf, maxConnPerRoute, connTimeToLiveMinutes, maxConnPerRoute);
		// 增加interceptor 显示压缩情况
		builder.addInterceptorFirst(myInterceptor);
		return builder.build();
	}

	private static class MyHttpResponseInterceptor extends CommonAbstract implements HttpResponseInterceptor
	{
		@Override
		public void process(HttpResponse response, HttpContext context) throws HttpException, IOException
		{
			boolean gzip = isContentGziped(response);
			HttpEntity entity = response.getEntity();
			long contentLength = entity == null ? 0 : entity.getContentLength();
			context.setAttribute(HTTP_RESPONSE_INFO, new HttpResponseInfo(gzip, contentLength));
		}
	}

	public static boolean isContentGziped(HttpResponse response)
	{
		Header header = response.getFirstHeader(HTTP.CONTENT_ENCODING);
		if (header == null) return false;
		String str = header.getValue();
		return str == null ? false : str.toLowerCase().indexOf("gzip") != -1;
	}

	@Override
	@PreDestroy
	public void closeAll()
	{
		for (Entry<String, CloseableHttpClient> en : clients.entrySet())
		{
			closeQuietly(en.getValue());
			clients.remove(en.getKey());
			logger.info("close  CloseableHttpclient , the key is :{}", en.getKey());
		}
	}

    public static void closeQuietly(Closeable closeable)
    {
        try
        {
            if (closeable != null) closeable.close();
        }
        catch (IOException ioe)
        {
            // ignore
        }
    }

}
