package howard.cinema.core.manage.tools.httpclient;

import howard.cinema.core.manage.common.CommonAbstract;
import org.apache.http.HttpResponse;

public abstract class AbstractResponseHandler extends CommonAbstract
{
	protected final boolean isContentGziped(HttpResponse response)
	{
		return HttpClientHelper.isContentGziped(response);
	}

}
