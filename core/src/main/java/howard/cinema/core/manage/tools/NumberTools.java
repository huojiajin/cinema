package howard.cinema.core.manage.tools;

import org.springframework.util.NumberUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public abstract class NumberTools extends NumberUtils
{
	private static final long ONE_KB = 1024L;
	private static final long ONE_MB = 1024L * 1024;
	private static final long ONE_GB = 1024L * 1024 * 1024;
	private static final long ONE_TB = 1024L * 1024 * 1024 * 1024;
	private static final DecimalFormat FORMAT = new DecimalFormat(",##0.00");

	/**
	 * 将字节转换成容易让人理解的字符串
	 * 
	 * @param bytes
	 *            字节数
	 * @return eg:10.12K 1.45M
	 */
	public static String bytesAsHumanStr(long bytes)
	{
		if (bytes < ONE_KB) return bytes + "B";
		if (bytes >= ONE_KB && bytes < ONE_MB)
		{
			double kb = Double.valueOf(bytes) / ONE_KB;
			return FORMAT.format(kb) + "K";
		}
		if (bytes >= ONE_MB && bytes < ONE_GB)
		{
			double mb = Double.valueOf(bytes) / ONE_MB;
			return FORMAT.format(mb) + "M";
		}
		if (bytes >= ONE_GB && bytes < ONE_TB)
		{
			double gb = Double.valueOf(bytes) / ONE_GB;
			return FORMAT.format(gb) + "G";
		}
		double tb = Double.valueOf(bytes) / ONE_TB;
		return FORMAT.format(tb) + "T";
	}

	/**
	 * 将double转换成%的格式
	 * 
	 * @param value
	 *            数值
	 * @param fraction
	 *            保留的小数点位数
	 * @return 百分比格式的字符串
	 */
	public static String asPercentStr(double value, int fraction)
	{
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(fraction);
		return nf.format(value);
	}

	public static int bytes2int(byte[] b)
	{
		return bytes2int(b, 0);
	}

	public static int bytes2int(byte[] b, int offset)
	{
		return b[(offset + 3)] & 0xFF | (b[(offset + 2)] & 0xFF) << 8 | (b[(offset + 1)] & 0xFF) << 16
				| (b[offset] & 0xFF) << 24;
	}

	// public static int bytes2int2(byte[] b, int offset)
	// {
	// return b[(offset + 2)] & 0xFF | (b[(offset + 1)] & 0xFF) << 8 | (b[offset] & 0xFF) << 16;
	// }

	public static long bytes2long(byte[] b)
	{
		return bytes2long(b, 0);
	}

	public static long bytes2long(byte[] b, int offset)
	{
		return b[(offset + 7)] & 0xFF | (b[(offset + 6)] & 0xFF) << 8 | (b[(offset + 5)] & 0xFF) << 16
				| (b[(offset + 4)] & 0xFF) << 24 | (b[(offset + 3)] & 0xFF) << 32 | (b[(offset + 2)] & 0xFF) << 40
				| (b[(offset + 1)] & 0xFF) << 48 | b[offset] << 56;
	}

	public static byte[] int2bytes(int n)
	{
		byte[] b = new byte[4];
		int2bytes(n, b, 0);
		return b;
	}

	public static void int2bytes(int n, byte[] buf, int offset)
	{
		buf[offset] = (byte) (n >> 24);
		buf[(offset + 1)] = (byte) (n >> 16);
		buf[(offset + 2)] = (byte) (n >> 8);
		buf[(offset + 3)] = (byte) n;
	}

	public static byte[] short2byte(int n)
	{
		byte[] b = new byte[2];
		short2byte(n, b, 0);
		return b;
	}

	public static void short2byte(int n, byte[] buf, int offset)
	{
		buf[offset] = (byte) (n >> 8);
		buf[(offset + 1)] = (byte) n;
	}

	public static short bytes2short(byte[] b, int offset)
	{
		return (short) (b[(offset + 1)] & 0xFF | (b[offset] & 0xFF) << 8);
	}

	public static short bytes2short(byte[] b)
	{
		return bytes2short(b, 0);
	}

	public static short byte2tinyint(byte[] b, int offset)
	{
		return (short) (b[offset] & 0xFF);
	}

	/**
	 * 长整型转换成二进制数组
	 * 
	 * @param n
	 *            长整型数值
	 * @return 返回8位的二进制字节
	 */
	public static byte[] long2bytes(long n)
	{
		byte[] b = new byte[8];
		long2bytes(n, b, 0);
		return b;
	}

	/**
	 * 长整型转换成二进制数组，可以指定偏移量
	 * 
	 * @param n
	 *            长整型数值
	 * @param buf
	 *            存放长整型二进制的数组
	 * @param offset
	 *            存放开始的偏移量
	 */
	public static void long2bytes(long n, byte[] buf, int offset)
	{
		buf[offset] = (byte) (int) (n >> 56);
		buf[(offset + 1)] = (byte) (int) (n >> 48);
		buf[(offset + 2)] = (byte) (int) (n >> 40);
		buf[(offset + 3)] = (byte) (int) (n >> 32);
		buf[(offset + 4)] = (byte) (int) (n >> 24);
		buf[(offset + 5)] = (byte) (int) (n >> 16);
		buf[(offset + 6)] = (byte) (int) (n >> 8);
		buf[(offset + 7)] = (byte) (int) n;
	}

	public static String getString(byte[] src, int srcPos, int destPos, int length)
	{
		byte[] tmp = new byte[length];
		System.arraycopy(src, srcPos, tmp, destPos, length);
		return new String(tmp).trim();
	}
}
