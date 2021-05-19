package howard.cinema.core.manage.tools.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.util.Assert;

import java.io.IOException;
import java.nio.charset.Charset;

public class Response2StringHandler extends AbstractResponseHandler implements HttpResponseHandler<String>
{
	private final String defaultResEncoding;

	public Response2StringHandler(Charset defaultResEncoding)
	{
		Assert.notNull(defaultResEncoding, "default charset can not be null.");
		this.defaultResEncoding = defaultResEncoding.name();
	}

	public Response2StringHandler(String defaultResEncoding)
	{
		this.defaultResEncoding = defaultResEncoding;
	}

	@Override
	public String handle(HttpResponse response, HttpResponseInfo info) throws IOException
	{
		HttpEntity entity = response.getEntity();
		if (entity == null)
		{
			logger.info("Http response's entity is null.");
			return "";
		}
		StatusLine sl = response.getStatusLine();
		int code = sl.getStatusCode();
		boolean gzip = isContentGziped(response);
		HttpEntity resEntity = gzip ? new GzipDecompressingEntity(entity) : entity;
		String res = EntityUtils.toString(resEntity, defaultResEncoding);
		int rawLen = res == null ? 0 : res.length();
		logger.debug("response entity length,transfer length:{} ;raw lengthr:{}", info.getContentLength(), rawLen);
		if (code >= 400) throw new IOException("Request Error:" + sl + "\n" + res);
		return res;
	}

}
