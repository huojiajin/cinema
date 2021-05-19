package howard.cinema.core.manage.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 *@name: SecurityUtil
 *@description: 加解密工具类
 *@author: huojiajin
 *@time: 2020/5/26 10:38
**/
public abstract class SecurityUtil
{
	protected static Logger logger = LoggerFactory.getLogger(SecurityUtil.class);
	private static char[] digit = { 'C', 'h', 'e', 'n', 'P', 'e', 'i', 'A', 'n', '8', '1', '0', '2', '1', '6', 'C' };
	private static char[] commonDigit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };
	private static String DEFAULT_KEY = "hxservicemanage2020";

	/**
	 * 使用aes加密数据
	 * 
	 * @param data
	 *            加密前数据
	 * @param key
	 *            加密口令
	 * @return
	 */
	public static byte[] aesEncrypt(byte[] data, String key)
	{
		return aesOperation(data, Cipher.ENCRYPT_MODE, key);
	}

	public static byte[] aesEncrypt(byte[] data)
	{
		return aesEncrypt(data, DEFAULT_KEY);
	}

	/**
	 * 使用aes解密数据
	 * 
	 * @param data
	 *            解密前数据
	 * @param key
	 *            加密口令
	 * @return
	 */
	public static byte[] aesDecrypt(byte[] data, String key)
	{
		return aesOperation(data, Cipher.DECRYPT_MODE, key);
	}

	public static byte[] aesDecrypt(byte[] data)
	{
		return aesDecrypt(data, DEFAULT_KEY);
	}

	private static byte[] aesOperation(byte[] data, int encryptMode, String key)
	{
		try
		{
			Assert.notNull(data, "data can not be null.");
			Cipher cipher = getAESCiper(encryptMode, key.getBytes());
			return cipher.doFinal(data);
		}
		catch (Exception e)
		{
			logger.error("", e);
			throw new RuntimeException("AES encrypt/decrypt failed");
		}
	}

	private static Cipher getAESCiper(int encryptMode, byte[] seed)
	{
		try
		{
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(seed);
			kgen.init(128, random);
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec keySpec = new SecretKeySpec(enCodeFormat, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
			cipher.init(encryptMode, keySpec);// 初始化
			return cipher;
		}
		catch (Exception e)
		{
			logger.error("", e);
			throw new RuntimeException("Create Cipher instance failed.");
		}
	}

	/**
	 * 字符串摘要方法。传入一个字符串，返回经过SHA-1摘要后的一个字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String encryptBySHA(String str)
	{
		return encryptStr(str, HashType.SHA_1, false);
	}

	/**
	 * 字符串摘要方法。传入一个字符串，返回经过MD5摘要后的一个字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String encryptByMD5(String str)
	{
		return encryptStr(str, HashType.MD5, false);
	}

	public static String hash(File file, HashType type, boolean useCommonDigit)
	{
		Assert.notNull(file, "file can not be null.");
		try
		{
			InputStream in = new FileInputStream(file);
			return hash(in, type, useCommonDigit);
		}
		catch (FileNotFoundException e)
		{
			logger.error("", e);
			return "";
		}

	}

	public static String hash(InputStream in, HashType type, boolean useCommonDigit)
	{
		Assert.notNull(in, "inputstream can not be null.");
		try
		{
			byte[] buffer = new byte[1024];
			MessageDigest md5 = type.getDigest();
			int numRead = 0;
			while ((numRead = in.read(buffer)) > 0)
			{
				md5.update(buffer, 0, numRead);
			}
			in.close();
			return toHexString(md5.digest(), useCommonDigit);
		}
		catch (Exception e)
		{
			logger.error("", e);
			return "";
		}
	}

	public static String encryptStr(String str, HashType type, boolean useCommonDigit)
	{
		return encryptStr(str, type, "UTF-8", useCommonDigit);
	}

	public static String encryptStr(String str, HashType type, String encoding, boolean useCommonDigit)
	{
		String enc = StringUtils.hasText(encoding) ? encoding : "UTF-8";
		try
		{
			byte b[] = hash(str.getBytes(enc), type);
			return toHexString(b, useCommonDigit);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 对二进制数据进行hash计算
	 * 
	 * @param data
	 * @param type
	 * @return
	 */
	public static byte[] hash(byte[] data, HashType type)
	{
		MessageDigest md = type.getDigest();
		md.update(data);
		return md.digest();
	}

	public static String toHexString(byte[] b, boolean useCommonDigit)
	{
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++)
		{
			if (useCommonDigit)
			{
				sb.append(commonDigit[(b[i] & 0xf0) >>> 4]);
				sb.append(commonDigit[b[i] & 0x0f]);
			}
			else
			{
				sb.append(digit[(b[i] & 0xf0) >>> 4]);
				sb.append(digit[b[i] & 0x0f]);
			}
		}
		return sb.toString();
	}

	public enum HashType
	{
		MD5("MD5") {},
		SHA_1("SHA-1") {},
		SHA_256("SHA-256") {},
		SHA_384("SHA-384") {},
		SHA_512("SHA-512") {},;

		private String value;

		private HashType(String value)
		{
			this.value = value;
		}

		public MessageDigest getDigest()
		{
			try
			{
				return MessageDigest.getInstance(getValue());
			}
			catch (NoSuchAlgorithmException e)
			{
				throw new RuntimeException("没有这种算法:" + value);
			}
		}

		public String getValue()
		{
			return value;
		}

	}
}
