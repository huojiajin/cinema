package howard.cinema.core.manage.tools;

import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 *@name: CollectionTools
 *@description: 集合工具类
 *@author: huojiajin
 *@time: 2020/5/26 10:35
**/
public abstract class CollectionTools extends CollectionUtils
{
	public final static <K, V> HashMap<K, V> newHashMap()
	{
		return new HashMap<K, V>();
	}

	public final static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap()
	{
		return new ConcurrentHashMap<K, V>();
	}

	/**
	 * 从<code>list<code>中取出第一个元素，<code>list<code>为空时返回<code>null</code>
	 * 
	 * @param list
	 * @return
	 */
	public final static <T> T extractOne(List<T> list)
	{
		return isEmpty(list) ? null : list.get(0);
	}

	/**
	 * 使用converter转换源List中的元素
	 *
	 * @param fromList
	 * @param converter
	 * @return
	 * @see 1.8以后可以使用{@link Stream#map(java.util.function.Function)}实现
	 */
//	public static <F, T> List<T> convertList(Collection<F> fromList, final ResultConverter<F, T> converter)
//	{
//		return fromList.stream().map(from -> converter.convert(from)).collect(Collectors.toList());
//	}

	/**
	 * 将list分割为指定分页大小的多个小list
	 * 
	 * @param list
	 * @param pageSize
	 *            每页大小
	 * @return
	 */
	public static <E> List<List<E>> subList(List<E> list, int pageSize)
	{
		Assert.notNull(list, "list can not be null.");
		int size = list.size();
		List<List<E>> rs = new ArrayList<List<E>>();
		if (size <= pageSize)
		{
			rs.add(list);
			return rs;
		}
		int pageCount = (size % pageSize == 0) ? size / pageSize : (size / pageSize + 1);
		for (int i = 0; i < pageCount; i++)
		{
			int lastSize = (i + 1) * pageSize;
			if (lastSize > size) lastSize = size;
			rs.add(list.subList(i * pageSize, lastSize));
		}
		return rs;
	}
}
