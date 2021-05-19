package howard.cinema.core.manage.tools.httpclient;

import howard.cinema.core.dao.entity.common.BaseEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.springframework.util.Assert;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FileUploadPara extends BaseEntity
{
	private String uri;// 不带参数的uri
	private Map<String, File> files = new HashMap<String, File>();// 文件
	private String requestEncoding = StandardCharsets.UTF_8.name();// 默认编码
	private List<? extends NameValuePair> params = new ArrayList<NameValuePair>();// 参数
	private String defaultResEncoding = StandardCharsets.UTF_8.name();// 返回的解析编码
	private int connTimeout = -1;// 连接超时毫秒数 负数忽略
	private int soTimeout = -1;// 响应超时毫秒数 负数忽略
	private HttpHost proxy;// 代理，为空时不使用代理
	private boolean useShortConnection = false;// 是否使用短连接

	public FileUploadPara()
	{

	}

	public FileUploadPara(String uri, int connTimeout, int soTimeout)
	{
		this.uri = uri;
		this.connTimeout = connTimeout;
		this.soTimeout = soTimeout;
	}

	public String getUri()
	{
		return uri;
	}

	public Map<String, File> getFiles()
	{
		return files;
	}

	public String getRequestEncoding()
	{
		return requestEncoding;
	}

	public List<? extends NameValuePair> getParams()
	{
		return params;
	}

	public String getDefaultResEncoding()
	{
		return defaultResEncoding;
	}

	public int getConnTimeout()
	{
		return connTimeout;
	}

	public int getSoTimeout()
	{
		return soTimeout;
	}

	public HttpHost getProxy()
	{
		return proxy;
	}

	public boolean isUseShortConnection()
	{
		return useShortConnection;
	}

	public FileUploadPara setUseShortConnection(boolean useShortConnection)
	{
		this.useShortConnection = useShortConnection;
		return this;
	}

	public FileUploadPara setProxy(HttpHost proxy)
	{
		this.proxy = proxy;
		return this;
	}

	public FileUploadPara setUri(String uri)
	{
		this.uri = uri;
		return this;
	}

	public FileUploadPara setFiles(Map<String, File> files)
	{
		this.files = files;
		return this;
	}

	public FileUploadPara setRequestEncoding(String requestEncoding)
	{
		this.requestEncoding = requestEncoding;
		return this;
	}

	public FileUploadPara setParams(List<? extends NameValuePair> params)
	{
		this.params = params;
		return this;
	}

	public FileUploadPara setDefaultResEncoding(String defaultResEncoding)
	{
		this.defaultResEncoding = defaultResEncoding;
		return this;
	}

	public FileUploadPara setConnTimeout(int connTimeout)
	{
		this.connTimeout = connTimeout;
		return this;
	}

	public FileUploadPara setSoTimeout(int soTimeout)
	{
		this.soTimeout = soTimeout;
		return this;
	}

	public HttpRequestPara toHttpRequestPara()
	{
		HttpRequestPara rs = new HttpRequestPara();
		Assert.notEmpty(getFiles(), "upload files can not be empty.");
		HttpPost post = new HttpPost(getUri());
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		for (Entry<String, File> en : getFiles().entrySet())
		{
			builder.addBinaryBody(en.getKey(), en.getValue());
		}
		for (NameValuePair nv : getParams())
		{
			builder.addTextBody(nv.getName(), nv.getValue(),
					ContentType.create(ContentType.DEFAULT_TEXT.getMimeType(), getRequestEncoding()));
		}
		post.setEntity(builder.build());
		rs.setRequest(post).setConnTimeout(connTimeout).setSoTimeout(soTimeout).setProxy(getProxy())
				.setUseShortConnection(useShortConnection);
		return rs;
	}

}
