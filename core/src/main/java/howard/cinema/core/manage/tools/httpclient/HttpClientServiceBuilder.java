package howard.cinema.core.manage.tools.httpclient;

import howard.cinema.core.manage.common.CommonAbstract;

import java.io.File;

public class HttpClientServiceBuilder extends CommonAbstract
{
	/**
	 * 默认实例，不验证服务端证书是否正确
	 * 
	 * @return
	 */
	public static HttpClientService defaultInstance()
	{
		try
		{
			return new HttpClientServiceImpl(HttpSslConfig.defaultConfig());
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 客户端验证的实例
	 * 
	 * @param certFile
	 *            证书文件
	 * @param certName
	 *            证书名字
	 * @return
	 * @throws Exception
	 */
	public static HttpClientService clientAuthInstance(File certFile, String certName) throws Exception
	{
		return new HttpClientServiceImpl(HttpSslConfig.clientAuthConfig(certFile, certName));
	}
}
