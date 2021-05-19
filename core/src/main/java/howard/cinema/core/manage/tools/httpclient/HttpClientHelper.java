package howard.cinema.core.manage.tools.httpclient;

import howard.cinema.core.dao.entity.common.BaseEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Peream <br>
 *         Create Time：2011-6-30 下午3:45:37<br>
 *         <a href="mailto:peream@gmail.com">peream@gmail.com</a>
 * @since 1.0
 * @version 1.0
 */
public abstract class HttpClientHelper extends AbstractHttpHelper
{
	protected static Logger logger = LoggerFactory.getLogger(HttpClientHelper.class);

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
	public static String httpGet(URI uri, String defaultResEncoding) throws IOException
	{
		return client.httpGet(uri, HttpRequestPara.DEFAULT_CONN_TIMEOUT, new Response2StringHandler(defaultResEncoding),
				-1);
	}

	public static <T> T httpGet(URI uri, int connTimeout, HttpResponseHandler<T> handler, int soTimeout)
			throws IOException
	{
		return client.httpGet(uri, false, connTimeout, handler, soTimeout);
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
	public static <T> T httpGet(URI uri, boolean enableGzip, int connTimeout, HttpResponseHandler<T> handler,
			int soTimeout) throws IOException
	{
		return client.httpGet(uri, enableGzip, connTimeout, handler, soTimeout, null);
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
	public static <T> T httpGet(URI uri, boolean enableGzip, int connTimeout, HttpResponseHandler<T> handler,
			int soTimeout, HttpClientInterceptor interceptor) throws IOException
	{
		return client.httpGet(uri, enableGzip, connTimeout, handler, soTimeout, interceptor);
	}

	public <T> T httpGet(URI uri, HttpHost proxy, boolean enableGzip, int connTimeout, HttpResponseHandler<T> handler,
			int soTimeout, HttpClientInterceptor interceptor) throws IOException
	{
		return client.httpGet(uri, proxy, enableGzip, connTimeout, handler, soTimeout, interceptor);
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
	public static <T> T postFileDownload(final String url, HttpHost proxy, int connTimeout,
			final FileDownloadHandler<T> handler, int soTimeout, String requestEncoding,
			List<? extends NameValuePair> params) throws IOException
	{
		return client.postFileDownload(url, connTimeout, handler, soTimeout, requestEncoding, params);
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
	public static <T> T postFileDownload(final String url, int connTimeout, final FileDownloadHandler<T> handler,
			int soTimeout, String requestEncoding, List<? extends NameValuePair> params) throws IOException
	{
		return client.postFileDownload(url, null, connTimeout, handler, soTimeout, requestEncoding, params);
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
	public static <T> T fileDownload(final URI uri, HttpHost proxy, int connTimeout,
			final FileDownloadHandler<T> handler, int soTimeout) throws IOException
	{
		return client.fileDownload(uri, proxy, connTimeout, handler, soTimeout);
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
	public static <T> T fileDownload(final URI uri, int connTimeout, final FileDownloadHandler<T> handler,
			int soTimeout) throws IOException
	{
		return client.fileDownload(uri, null, connTimeout, handler, soTimeout);
	}

	public static <T> T fileDownload(HttpRequestPara para, final FileDownloadHandler<T> handler) throws IOException
	{
		return client.fileDownload(para, handler);
	}

	/**
	 * 使用POST的方式请求指定URI
	 * 
	 * @param uri
	 *            不带参数的uri例如：<code>http://127.0.0.1/ccc/sample/json/show.do</code>
	 * @param requestEncoding
	 *            请求参数的编码
	 * @param params
	 *            请求参数
	 * @param defaultResEncoding
	 *            返回信息的默认编码，如果返回中已经指定了编码，<br>
	 *            则使用返回的编码{@link HttpServletResponse#getCharacterEncoding()}
	 * @return 返回信息的字符串
	 * @throws IOException
	 *             请求错误时抛出此异常
	 */
	public static String httpPost(String uri, String requestEncoding, List<? extends NameValuePair> params,
			String defaultResEncoding) throws IOException
	{
		return client.httpPost(uri, HttpRequestPara.DEFAULT_CONN_TIMEOUT, requestEncoding, params,
				new Response2StringHandler(defaultResEncoding), -1);
	}

	/**
	 * 使用POST的方式请求指定URI
	 * 
	 * @param uri
	 *            uri例如：<code>http://127.0.0.1/ccc/sample/json/show.do</code>
	 * @param requestJson
	 *            请求参数
	 * @param defaultResEncoding
	 *            返回信息的默认编码，如果返回中已经指定了编码，<br>
	 *            则使用返回的编码{@link HttpServletResponse#getCharacterEncoding()}
	 * @return 返回信息的字符串
	 * @throws IOException
	 *             请求错误时抛出此异常
	 */
	public static String jsonPost(String uri, String requestJson, String defaultResEncoding) throws IOException
	{
		return client.jsonPost(uri, requestJson, defaultResEncoding);
	}

	public static String jsonPost(String uri, String requestJson) throws IOException
	{
		return client.jsonPost(uri, requestJson);
	}

	public static <E extends BaseEntity> E jsonPost(Class<E> resultClass, String uri, String requestJson)
			throws IOException
	{
		return client.jsonPost(resultClass, uri, requestJson);
	}

	public static String jsonPost(String url, HttpHost proxy, String requestJson, String defaultResEncoding)
			throws IOException
	{
		return client.jsonPost(url, proxy, requestJson, defaultResEncoding);
	}

	public static String jsonPost(String url, HttpHost proxy, boolean enableGzip, int connTimeout, String requestJson,
			int soTimeout, String defaultResEncoding, HttpClientInterceptor interceptor) throws IOException
	{
		return client.jsonPost(url, proxy, enableGzip, connTimeout, requestJson, soTimeout, defaultResEncoding,
				interceptor);
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
	public static String fileUpload(String uri, Map<String, File> files, String requestEncoding,
			List<? extends NameValuePair> params, String defaultResEncoding) throws IOException
	{
		return client.fileUpload(uri, null, files, requestEncoding, params, defaultResEncoding);
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
	public static String fileUpload(String uri, HttpHost proxy, Map<String, File> files, String requestEncoding,
			List<? extends NameValuePair> params, String defaultResEncoding) throws IOException
	{
		return client.fileUpload(uri, proxy, files, requestEncoding, params, defaultResEncoding);
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
	public static <T> T fileUpload(FileUploadPara para, HttpResponseHandler<T> handler) throws IOException
	{
		return client.httpRequest(para.toHttpRequestPara(), handler);
	}

	public static String fileUpload(FileUploadPara para) throws IOException
	{
		return client.fileUpload(para, new Response2StringHandler(para.getDefaultResEncoding()));
	}

	public static <T> T httpPost(String url, int connTimeout, String requestEncoding,
			List<? extends NameValuePair> params, HttpResponseHandler<T> handler, int soTimeout) throws IOException
	{
		return client.httpPost(url, false, connTimeout, requestEncoding, params, handler, soTimeout);
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
	public static <T> T httpPost(String url, boolean enableGzip, int connTimeout, String requestEncoding,
			List<? extends NameValuePair> params, HttpResponseHandler<T> handler, int soTimeout) throws IOException
	{
		return client.httpPost(url, enableGzip, connTimeout, requestEncoding, params, handler, soTimeout, null);
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
	public static <T> T httpPost(String url, boolean enableGzip, int connTimeout, String requestEncoding,
			List<? extends NameValuePair> params, HttpResponseHandler<T> handler, int soTimeout,
			HttpClientInterceptor interceptor) throws IOException
	{
		return client.httpPost(url, enableGzip, connTimeout, requestEncoding, params, handler, soTimeout, interceptor);
	}

	public <T> T httpPost(String url, HttpHost proxy, boolean enableGzip, int connTimeout, String requestEncoding,
			List<? extends NameValuePair> params, HttpResponseHandler<T> handler, int soTimeout,
			HttpClientInterceptor interceptor) throws IOException
	{
		return client.httpPost(url, proxy, enableGzip, connTimeout, requestEncoding, params, handler, soTimeout,
				interceptor);
	}

}
