package howard.cinema.core.manage.tools.httpclient;

import howard.cinema.core.manage.common.CommonAbstract;
import org.apache.commons.lang3.SystemUtils;

public abstract class AbstractHelper extends CommonAbstract
{
	public static final int BUFFER = 1024;
	protected static final String DEFAULT_ENCODING = SystemUtils.IS_OS_WINDOWS ? "GBK" : "UTF-8";
}
