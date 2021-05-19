package howard.cinema.core.manage.tools.httpclient;

import org.apache.http.HttpResponse;

import java.io.IOException;

public interface HttpResponseHandler<T>
{
	/**
	 * 
	 * @param response 响应信息
	 * @param info response中未经处理的原始信息
	 * @return
	 * @throws IOException
	 */
	public T handle(HttpResponse response, HttpResponseInfo info) throws IOException;
}
