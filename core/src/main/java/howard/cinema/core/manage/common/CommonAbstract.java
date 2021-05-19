package howard.cinema.core.manage.common;

import howard.cinema.core.manage.tools.MyFormattingTuple;
import howard.cinema.core.manage.tools.MyMessageFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 *@name: CommonAbstract
 *@description: 通用抽象类
 *@author: huojiajin
 *@time: 2020/5/26 11:02
**/
public abstract class CommonAbstract
{
	protected Logger logger = LoggerFactory.getLogger(getClass());

	public static String subContent(String content)
	{
		return subContent(content, 200);
	}

	public static String subContent(String content, int max)
	{
		if (!StringUtils.hasText(content) || content.length() < (max + 1)) return content;
		return content.substring(0, max) + "...(has more)";
	}

	protected static final boolean hasText(CharSequence str)
	{
		return StringUtils.hasText(str);
	}

	protected static final boolean isEmpty(Object[] array)
	{
		return ObjectUtils.isEmpty(array);
	}

	protected static final boolean isEmpty(Collection<?> collection)
	{
		return CollectionUtils.isEmpty(collection);
	}

	protected static final boolean isEmpty(Map<?, ?> map)
	{
		return CollectionUtils.isEmpty(map);
	}

	public static final boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static void closeQuietly(Closeable closeable){
		if(closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将使用{}占位符的字符串转成日志所需要的字符串
	 *
	 * @param log
	 *            日志信息
	 * @param args
	 *            占位符参数（依次替换占位符,比实际占位符多则取前占位符个数个，少则后面的占位符不替换）
	 * @return 使用占位符替换后的字符串
	 */
	public static String toLogString(String log, Object... args)
	{
		if (!hasText(log) || args == null) return log;
		MyFormattingTuple ft = MyMessageFormatter.format(log, args);
		return ft.getMessage();
	}
}
