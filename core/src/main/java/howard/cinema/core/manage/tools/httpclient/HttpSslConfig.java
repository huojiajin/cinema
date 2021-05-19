package howard.cinema.core.manage.tools.httpclient;

import howard.cinema.core.dao.entity.common.BaseEntity;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.springframework.util.Assert;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class HttpSslConfig extends BaseEntity
{
	private TrustManager[] trustManagers;
	private HostnameVerifier verifier;
	private KeyManager[] keyManagers;
	private SecureRandom sr;

	public TrustManager[] getTrustManagers()
	{
		return trustManagers;
	}

	public HostnameVerifier getVerifier()
	{
		return verifier;
	}

	public KeyManager[] getKeyManagers()
	{
		return keyManagers;
	}

	public SecureRandom getSr()
	{
		return sr;
	}

	public HttpSslConfig setTrustManagers(TrustManager[] tm)
	{
		Assert.notNull(tm, "trust manager can not be null.");
		this.trustManagers = tm;
		return this;
	}

	public HttpSslConfig setVerifier(HostnameVerifier verifier)
	{
		Assert.notNull(verifier, "hostname verifier can not be null.");
		this.verifier = verifier;
		return this;
	}

	public HttpSslConfig setKeyManagers(KeyManager[] km)
	{
		this.keyManagers = km;
		return this;
	}

	public HttpSslConfig setSr(SecureRandom sr)
	{
		this.sr = sr;
		return this;
	}

	public static HttpSslConfig defaultConfig()
	{
		TrustManager tm = new X509TrustManager() {
			@Override
			public X509Certificate[] getAcceptedIssuers()
			{
				return null;
			}

			@Override
			public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException
			{}

			@Override
			public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException
			{}
		};
		return new HttpSslConfig().setTrustManagers(new TrustManager[] { tm }).setVerifier(new HostnameVerifier() {
			@Override
			public boolean verify(String string, SSLSession ssls)
			{
				return true;
			}
		});
	}

	public static HttpSslConfig clientAuthConfig(File certFile, String certName) throws Exception
	{
		if (!certFile.exists()) throw new IOException("证书文件不存在:" + certFile.getAbsolutePath());
		HttpSslConfig config = new HttpSslConfig();
		config.setVerifier(new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session)
			{
				return hostname.equals(session.getPeerHost());
			}
		});
		FileInputStream fis = new FileInputStream(certFile);
		final X509Certificate cert = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(fis);
		fis.close();
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		ks.load(null);
		ks.setCertificateEntry(certName, cert);

		TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		factory.init(ks);
		TrustManager[] trustManagers = factory.getTrustManagers();
		config.setTrustManagers(trustManagers);
		return config;
	}

	public SSLConnectionSocketFactory toSSLSocketFactory() throws KeyManagementException, NoSuchAlgorithmException
	{
		SSLContext ctx = SSLContext.getInstance("TLS");
		ctx.init(keyManagers, trustManagers, sr);
		return new SSLConnectionSocketFactory(ctx, verifier);
	}
}
