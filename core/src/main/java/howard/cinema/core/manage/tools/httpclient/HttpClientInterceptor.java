package howard.cinema.core.manage.tools.httpclient;

import org.apache.http.client.methods.HttpUriRequest;

public interface HttpClientInterceptor
{
	/**
	 * 发送http请求前做些操作，比如增加header等
	 * 
	 * @param request
	 */
	public void process(final HttpUriRequest request);
}
