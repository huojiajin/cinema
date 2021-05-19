package howard.cinema.core.manage.tools.httpclient;

import howard.cinema.core.dao.entity.common.BaseEntity;
import howard.cinema.core.manage.tools.JsonTools;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public interface HttpClientService
{
	/**
	 * 可以通过此方法重建连接池
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
	public void reBuildClient(HttpRequestPara para, int maxConnPerRoute, int connTimeToLiveMinutes, int maxConnTotal);

	/**
	 * 通过post方式下载文件
	 * 
	 * @param url
	 *            URL
	 * @param connTimeout
	 *            连接超时毫秒数 负数忽略
	 * @param handler
	 * @param soTimeout
	 *            响应超时毫秒数 负数忽略
	 * @param requestEncoding
	 *            请求参数编码
	 * @param params
	 *            post请求参数
	 * @return
	 * @throws IOException
	 *             下载异常时抛出
	 */
	default public <T> T postFileDownload(final String url, HttpHost proxy, int connTimeout,
			final FileDownloadHandler<T> handler, int soTimeout, String requestEncoding,
			List<? extends NameValuePair> params) throws IOException
	{
		return postFileDownload(url, proxy, connTimeout, handler, soTimeout, requestEncoding, params, false);
	}

	default public <T> T postFileDownload(final String url, HttpHost proxy, int connTimeout,
			final FileDownloadHandler<T> handler, int soTimeout, String requestEncoding,
			List<? extends NameValuePair> params, boolean useShortConnection) throws IOException
	{
		Assert.notNull(params, "params can not be null.");
		HttpRequestPara para = HttpRequestPara.newPostInstance(url, requestEncoding, params).setConnTimeout(connTimeout)
				.setProxy(proxy).setSoTimeout(soTimeout).setEnableGzip(true).setUseShortConnection(useShortConnection);
		return fileDownload(para, handler);
	}

	/**
	 * 通过post方式下载文件
	 * 
	 * @param url
	 *            URL
	 * @param connTimeout
	 *            连接超时毫秒数 负数忽略
	 * @param handler
	 * @param soTimeout
	 *            响应超时毫秒数 负数忽略
	 * @param requestEncoding
	 *            请求参数编码
	 * @param params
	 *            post请求参数
	 * @return
	 * @throws IOException
	 *             下载异常时抛出
	 */
	default public <T> T postFileDownload(final String url, int connTimeout, final FileDownloadHandler<T> handler,
			int soTimeout, String requestEncoding, List<? extends NameValuePair> params) throws IOException
	{
		return postFileDownload(url, null, connTimeout, handler, soTimeout, requestEncoding, params);
	}

	/**
	 * 通过URI下载文件
	 * 
	 * @param uri
	 *            下载文件的URI
	 * @param connTimeout
	 *            连接超时毫秒数 负数忽略
	 * @param handler
	 *            下载文件的回调接口
	 * @param soTimeout
	 *            响应超时毫秒数 负数忽略
	 * @return 文件下载完成后的返回结果
	 * @throws IOException
	 *             下载异常时抛出
	 */
	default public <T> T fileDownload(final URI uri, HttpHost proxy, int connTimeout,
			final FileDownloadHandler<T> handler, int soTimeout) throws IOException
	{
		HttpRequestPara para = HttpRequestPara.newGetInstance(uri).setConnTimeout(connTimeout).setSoTimeout(soTimeout)
				.setEnableGzip(true).setProxy(proxy);
		return fileDownload(para, handler);
	}

	/**
	 * 通过URI下载文件
	 * 
	 * @param uri
	 *            下载文件的URI
	 * @param connTimeout
	 *            连接超时毫秒数 负数忽略
	 * @param handler
	 *            下载文件的回调接口
	 * @param soTimeout
	 *            响应超时毫秒数 负数忽略
	 * @return 文件下载完成后的返回结果
	 * @throws IOException
	 *             下载异常时抛出
	 */
	default public <T> T fileDownload(final URI uri, int connTimeout, final FileDownloadHandler<T> handler,
			int soTimeout) throws IOException
	{
		return fileDownload(uri, null, connTimeout, handler, soTimeout);
	}

	public <T> T fileDownload(HttpRequestPara para, final FileDownloadHandler<T> handler) throws IOException;

	/**
	 * 使用POST的方式请求指定URI
	 * 
	 * @param url
	 *            url例如：<code>http://127.0.0.1/ccc/sample/json/show.do</code>
	 * @param requestJson
	 *            请求参数
	 * @param defaultResEncoding
	 *            返回信息的默认编码，如果返回中已经指定了编码，<br>
	 *            则使用返回的编码{@link HttpServletResponse#getCharacterEncoding()}
	 * @return 返回信息的字符串
	 * @throws IOException
	 *             请求错误时抛出此异常
	 */
	default public String jsonPost(String url, String requestJson, String defaultResEncoding) throws IOException
	{
		return jsonPost(url, null, requestJson, defaultResEncoding);
	}

	default public String jsonPost(String url, HttpHost proxy, String requestJson, String defaultResEncoding)
			throws IOException
	{
		return jsonPost(url, proxy, true, HttpRequestPara.DEFAULT_CONN_TIMEOUT, requestJson, -1, defaultResEncoding,
				null);
	}

	/**
	 * json post请求
	 * 
	 * @param url
	 * @param proxy
	 * @param enableGzip
	 * @param connTimeout
	 * @param requestJson
	 * @param soTimeout
	 * @param defaultResEncoding
	 * @param interceptor
	 * @return
	 * @throws IOException
	 * @see {@link HttpRequestPara#newJsonPostInstance(String, String)}
	 */
	default public String jsonPost(String url, HttpHost proxy, boolean enableGzip, int connTimeout, String requestJson,
			int soTimeout, String defaultResEncoding, HttpClientInterceptor interceptor) throws IOException
	{
		return jsonPost(url, proxy, enableGzip, connTimeout, requestJson, soTimeout, defaultResEncoding, interceptor,
				false);
	}

	default public String jsonPost(String url, HttpHost proxy, boolean enableGzip, int connTimeout, String requestJson,
			int soTimeout, String defaultResEncoding, HttpClientInterceptor interceptor, boolean useShortConnection)
			throws IOException
	{
		HttpRequestPara para = HttpRequestPara.newJsonPostInstance(url, requestJson).setConnTimeout(connTimeout)
				.setSoTimeout(soTimeout).setEnableGzip(enableGzip).setInterceptor(interceptor).setProxy(proxy)
				.setUseShortConnection(useShortConnection);
		return httpRequest(para, new Response2StringHandler(defaultResEncoding));
	}

	default public String jsonPost(HttpRequestPara para) throws IOException
	{
		return jsonPost(para, StandardCharsets.UTF_8);
	}

	/**
	 * json post请求
	 * 
	 * @param para
	 * @param defaultResEncoding
	 * @return
	 * @throws IOException
	 * @see {@link HttpRequestPara#newJsonPostInstance(String, String)}
	 */
	default public String jsonPost(HttpRequestPara para, Charset defaultResEncoding) throws IOException
	{
		return httpRequest(para, new Response2StringHandler(defaultResEncoding));
	}

	default public String jsonPost(String uri, String requestJson) throws IOException
	{
		return jsonPost(uri, requestJson, StandardCharsets.UTF_8.name());
	}

	default public <E extends BaseEntity> E jsonPost(Class<E> resultClass, String uri, String requestJson)
			throws IOException
	{
		String rs = jsonPost(uri, requestJson);
		if (!StringUtils.hasText(rs)) return null;
		return JsonTools.json2Object(rs, resultClass);
	}

	/**
	 * 文件上传
	 * 
	 * @param uri
	 *            不带参数的uri例如：<code>http://127.0.0.1/ccc/sample/json/show.do</code>
	 * @param files
	 *            被上传的文件
	 * @param requestEncoding
	 *            请求参数的编码
	 * @param params
	 *            其他参数
	 * @param defaultResEncoding
	 *            返回信息的默认编码，如果返回中已经指定了编码，<br>
	 *            则使用返回的编码{@link HttpServletResponse#getCharacterEncoding()}
	 * @return 返回信息的字符串
	 * @throws IOException
	 */
	default public String fileUpload(String uri, Map<String, File> files, String requestEncoding,
			List<? extends NameValuePair> params, String defaultResEncoding) throws IOException
	{
		return fileUpload(uri, null, files, requestEncoding, params, defaultResEncoding);
	}

	/**
	 * 文件上传
	 * 
	 * @param uri
	 *            不带参数的uri例如：<code>http://127.0.0.1/ccc/sample/json/show.do</code>
	 * @param files
	 *            被上传的文件
	 * @param requestEncoding
	 *            请求参数的编码
	 * @param params
	 *            其他参数
	 * @param defaultResEncoding
	 *            返回信息的默认编码，如果返回中已经指定了编码，<br>
	 *            则使用返回的编码{@link HttpServletResponse#getCharacterEncoding()}
	 * @return 返回信息的字符串
	 * @throws IOException
	 */
	default public String fileUpload(String uri, HttpHost proxy, Map<String, File> files, String requestEncoding,
			List<? extends NameValuePair> params, String defaultResEncoding) throws IOException
	{
		FileUploadPara para = new FileUploadPara(uri, -1, -1);
		para.setDefaultResEncoding(defaultResEncoding).setRequestEncoding(requestEncoding).setFiles(files)
				.setParams(params).setProxy(proxy);
		return fileUpload(para);
	}

	/**
	 * 文件上传
	 * 
	 * @param para
	 *            上传参数，具体参见{@link FileUploadPara}的属性注释
	 * @param handler
	 *            请求返回的回调处理器
	 * @return 返回信息的字符串
	 * @throws IOException
	 */
	default public <T> T fileUpload(FileUploadPara para, HttpResponseHandler<T> handler) throws IOException
	{
		return httpRequest(para.toHttpRequestPara(), handler);
	}

	default public String fileUpload(FileUploadPara para) throws IOException
	{
		return fileUpload(para, new Response2StringHandler(para.getDefaultResEncoding()));
	}

	default public <T> T httpPost(String url, int connTimeout, String requestEncoding,
			List<? extends NameValuePair> params, HttpResponseHandler<T> handler, int soTimeout) throws IOException
	{
		return httpPost(url, false, connTimeout, requestEncoding, params, handler, soTimeout);
	}

	/**
	 * 使用POST的方式请求指定URI
	 * 
	 * @param url
	 *            不带参数的url例如：<code>http://127.0.0.1/ccc/sample/json/show.do</code>
	 * @param enableGzip
	 *            是否请求服务端启用gzip压缩
	 * @param connTimeout
	 *            连接超时毫秒数 负数忽略
	 * @param requestEncoding
	 *            请求参数的编码
	 * @param params
	 *            请求参数
	 * @param handler
	 *            返回内容处理的回调接口
	 * @param soTimeout
	 *            响应超时毫秒数 负数忽略
	 * @return 处理结果
	 * @throws IOException
	 */
	default public <T> T httpPost(String url, boolean enableGzip, int connTimeout, String requestEncoding,
			List<? extends NameValuePair> params, HttpResponseHandler<T> handler, int soTimeout) throws IOException
	{
		return httpPost(url, enableGzip, connTimeout, requestEncoding, params, handler, soTimeout, null);
	}

	/**
	 * 使用POST的方式请求指定URI
	 * 
	 * @param url
	 *            不带参数的url例如：<code>http://127.0.0.1/ccc/sample/json/show.do</code>
	 * @param enableGzip
	 *            是否请求服务端启用gzip压缩
	 * @param connTimeout
	 *            连接超时毫秒数 负数忽略
	 * @param requestEncoding
	 *            请求参数的编码
	 * @param params
	 *            请求参数
	 * @param handler
	 *            返回内容处理的回调接口
	 * @param soTimeout
	 *            响应超时毫秒数 负数忽略
	 * @param interceptor
	 * @return 处理结果
	 * @throws IOException
	 */
	default public <T> T httpPost(String url, boolean enableGzip, int connTimeout, String requestEncoding,
			List<? extends NameValuePair> params, HttpResponseHandler<T> handler, int soTimeout,
			HttpClientInterceptor interceptor) throws IOException
	{
		return httpPost(url, null, enableGzip, connTimeout, requestEncoding, params, handler, soTimeout, interceptor);
	}

	default public <T> T httpPost(String url, HttpHost proxy, boolean enableGzip, int connTimeout,
			String requestEncoding, List<? extends NameValuePair> params, HttpResponseHandler<T> handler, int soTimeout,
			HttpClientInterceptor interceptor) throws IOException
	{
		return httpPost(url, proxy, enableGzip, connTimeout, requestEncoding, params, handler, soTimeout, interceptor,
				false);
	}

	default public <T> T httpPost(String url, HttpHost proxy, boolean enableGzip, int connTimeout,
			String requestEncoding, List<? extends NameValuePair> params, HttpResponseHandler<T> handler, int soTimeout,
			HttpClientInterceptor interceptor, boolean useShortConnection) throws IOException
	{
		HttpRequestPara para = HttpRequestPara.newPostInstance(url, requestEncoding, params).setEnableGzip(enableGzip)
				.setConnTimeout(connTimeout).setSoTimeout(soTimeout).setInterceptor(interceptor).setProxy(proxy)
				.setUseShortConnection(useShortConnection);
		return httpRequest(para, handler);
	}

	/**
	 * 使用GET的方式请求指定URI
	 * 
	 * @param uri
	 *            URI {@link URLEncodedUtils#format(List, String)}
	 * @param defaultResEncoding
	 *            返回信息的默认编码，如果返回中已经指定了编码，<br>
	 *            则使用返回的编码{@link HttpServletResponse#getCharacterEncoding()}
	 * @return 返回信息的字符串
	 * @throws IOException
	 *             请求错误时抛出此异常
	 */
	default public String httpGet(URI uri, String defaultResEncoding) throws IOException
	{
		return httpGet(uri, HttpRequestPara.DEFAULT_CONN_TIMEOUT, new Response2StringHandler(defaultResEncoding), -1);
	}

	default public <T> T httpGet(URI uri, int connTimeout, HttpResponseHandler<T> handler, int soTimeout)
			throws IOException
	{
		return httpGet(uri, false, connTimeout, handler, soTimeout);
	}

	/**
	 * 使用GET的方式请求指定URI
	 * 
	 * @param uri
	 *            URI {@link URLEncodedUtils#format(List, String)}
	 * @param enableGzip
	 *            是否请求服务端启用gzip压缩
	 * @param connTimeout
	 *            连接超时毫秒数 负数忽略
	 * @param handler
	 *            返回内容处理的回调接口
	 * @param soTimeout
	 *            响应超时毫秒数 负数忽略
	 * @return 处理结果
	 * @throws IOException
	 * @see {@link Response2StringHandler}
	 */
	default public <T> T httpGet(URI uri, boolean enableGzip, int connTimeout, HttpResponseHandler<T> handler,
			int soTimeout) throws IOException
	{
		return httpGet(uri, enableGzip, connTimeout, handler, soTimeout, null);
	}

	/**
	 * 使用GET的方式请求指定URI
	 * 
	 * @param uri
	 *            URI {@link URLEncodedUtils#format(List, String)}
	 * @param enableGzip
	 *            是否请求服务端启用gzip压缩
	 * @param connTimeout
	 *            连接超时毫秒数 负数忽略
	 * @param handler
	 *            返回内容处理的回调接口
	 * @param soTimeout
	 *            响应超时毫秒数 负数忽略
	 * @param interceptor
	 * @return 处理结果
	 * @throws IOException
	 * @see {@link Response2StringHandler}
	 */
	default public <T> T httpGet(URI uri, boolean enableGzip, int connTimeout, HttpResponseHandler<T> handler,
			int soTimeout, HttpClientInterceptor interceptor) throws IOException
	{
		return httpGet(uri, null, enableGzip, connTimeout, handler, soTimeout, interceptor);
	}

	default public <T> T httpGet(URI uri, HttpHost proxy, boolean enableGzip, int connTimeout,
			HttpResponseHandler<T> handler, int soTimeout, HttpClientInterceptor interceptor) throws IOException
	{
		return httpGet(uri, proxy, enableGzip, connTimeout, handler, soTimeout, interceptor, false);
	}

	default public <T> T httpGet(URI uri, HttpHost proxy, boolean enableGzip, int connTimeout,
			HttpResponseHandler<T> handler, int soTimeout, HttpClientInterceptor interceptor,
			boolean useShortConnection) throws IOException
	{
		HttpRequestPara para = HttpRequestPara.newGetInstance(uri).setEnableGzip(enableGzip).setConnTimeout(connTimeout)
				.setSoTimeout(soTimeout).setInterceptor(interceptor).setProxy(proxy)
				.setUseShortConnection(useShortConnection);
		return httpRequest(para, handler);
	}

	/**
	 * 关闭连接池中的所有连接
	 * 
	 * @see {@link @PreDestroy}
	 */
	public void closeAll();

	default public <T> T httpRequest(HttpUriRequest request, int connTimeout, HttpResponseHandler<T> handler,
			int soTimeout) throws IOException
	{
		return httpRequest(request, false, connTimeout, handler, soTimeout);
	}

	default public <T> T httpRequest(HttpUriRequest request, boolean enableGzip, int connTimeout,
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
	default public <T> T httpRequest(HttpUriRequest request, boolean enableGzip, int connTimeout,
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
	public <T> T httpRequest(HttpRequestPara para, HttpResponseHandler<T> handler) throws IOException;

	default String httpRequest(HttpUriRequest request, int connTimeout, final String defaultResEncoding, int soTimeout)
			throws IOException
	{
		return httpRequest(request, true, connTimeout, new Response2StringHandler(defaultResEncoding), soTimeout);
	}

	default String httpRequest(HttpUriRequest request, final String defaultResEncoding) throws IOException
	{
		return httpRequest(request, HttpRequestPara.DEFAULT_CONN_TIMEOUT, defaultResEncoding, -1);
	}

	default public String getFilename(Header header)
	{
		if (header == null || header.getElements() == null || header.getElements().length == 0) return null;
		for (HeaderElement ele : header.getElements())
		{
			if (ObjectUtils.isEmpty(ele.getParameters())) continue;
			for (NameValuePair nv : ele.getParameters())
			{
				if (nv.getName() != null && nv.getName().toLowerCase().contains("filename")) return nv.getValue();
			}
		}
		return null;
	}
}
