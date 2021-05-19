package howard.cinema.core.manage.tools;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.type.MapType;
import howard.cinema.core.dao.entity.common.BaseEntity;
import howard.cinema.core.manage.common.CommonAbstract;
import howard.cinema.core.manage.common.ObjectMapperBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.*;

/**
 *@name: JsonTools
 *@description: json工具类
 *@author: huojiajin
 *@time: 2020/5/26 10:36
**/
public abstract class JsonTools extends CommonAbstract
{
	public static final String FILTER_NAME = "myFilter";
	protected static Logger logger = LoggerFactory.getLogger(JsonTools.class);
	protected static ObjectMapper objectMapper;

	static
	{
		objectMapper = ObjectMapperBuilder.create().build();
	}

	public static void customerMapper(ObjectMapper mapper)
	{
        Assert.notNull(mapper, "mapper must specified.");
		objectMapper = mapper;
	}

	/**
	 * 转换成json字符串，可定义忽略的属性。<BR>
	 * 已经使用{@link JsonIgnore}标注的不用指定也会忽略。<BR>
	 * 
	 * @param entity
	 * @param ingoreProperties
	 * @return
	 * @throws IOException
	 */
	public final static String toJsonStr(BaseEntity entity, String... ingoreProperties) throws IOException
	{
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter(FILTER_NAME,
				SimpleBeanPropertyFilter.serializeAllExcept(ingoreProperties));
		return objectMapper.writer(filterProvider).writeValueAsString(entity);
	}

	/**
	 * 将entity对象转换成json字符串
	 * 
	 * @param entity
	 * @return json格式字符串
	 * @throws IOException
	 */
	public final static String toJsonStr(BaseEntity entity) throws IOException
	{
		return objectMapper.writeValueAsString(entity);
	}

	public final static String toJsonStr(String str)
	{
		try
		{
			return objectMapper.writeValueAsString(str);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public final static String toJsonStr(Number number)
	{
		try
		{
			return objectMapper.writeValueAsString(number);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public final static String toJsonStr(Boolean bool)
	{
		try
		{
			return objectMapper.writeValueAsString(bool);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 将集合对象转换成json字符串
	 * 
	 * @param <T>
	 *            集合内的对象类型必须继承entity
	 * @param collection
	 *            集合
	 * @return json格式字符串
	 * @throws IOException
	 */
	public final static <T> String toJsonStr(Collection<T> collection) throws IOException
	{
		return objectMapper.writeValueAsString(collection);
	}

	/**
	 * 将Map转换成Json字符串
	 * 
	 * @param map
	 *            map
	 * @return json格式字符串
	 * @throws IOException
	 */
	public final static String toJsonStr(Map<?, ?> map) throws IOException
	{
		return objectMapper.writeValueAsString(map);
	}

	/**
	 * 将集合对象转换成json字符串(尽量使用entity格式对象)
	 * 
	 * @param collection
	 *            集合
	 * @return json格式字符串
	 * @throws IOException
	 */
	public final static String strArray2JsonStr(Collection<String> collection) throws IOException
	{
		return objectMapper.writeValueAsString(collection);
	}

	/**
	 * 将集合对象转换成json字符串(尽量使用entity格式对象)
	 * 
	 * @param collection
	 *            集合
	 * @return json格式字符串
	 * @throws IOException
	 */
	public final static String boolArray2JsonStr(Collection<Boolean> collection) throws IOException
	{
		return objectMapper.writeValueAsString(collection);
	}

	/**
	 * 将集合对象转换成json字符串(尽量使用entity格式对象)
	 * 
	 * @param collection
	 *            集合
	 * @return json格式字符串
	 * @throws IOException
	 */
	public final static <T extends Number> String numberArray2JsonStr(Collection<T> collection) throws IOException
	{
		return objectMapper.writeValueAsString(collection);
	}

	/**
	 * 将json字符串转换为泛型对象
	 * @param jsonStr
	 * @param type
	 * @return
	 * @throws IOException
	 */
	public static <R> R json2Object(String jsonStr, TypeReference<R> type)throws IOException {
		if (!hasText(jsonStr)) return null;
		return objectMapper.readValue(jsonStr, type);
	}
	
	
	/**
	 *  将json字符串转换为泛型对象
	 */
	public static <R,T> R json2Object(String jsonStr,Class<R> wrapClazz, Class<T> modelClazz)
			throws IOException {
		JavaType valueType = objectMapper.getTypeFactory().constructParametricType(wrapClazz, modelClazz);
		return objectMapper.readValue(jsonStr, valueType);
	}
	
	/**
	 * 将json字符串转换成entity对象
	 * 
	 * @param <T>
	 *            对象类型
	 * @param jsonStr
	 *            json字符串
	 * @param clazz
	 *            转换后的对象类型，必须是继承BaseEntity的对象
	 * @return 转换后的对象
	 * @throws IOException
	 */
	public final static <T extends BaseEntity> T json2Object(String jsonStr, Class<T> clazz) throws IOException
	{
		if (!hasText(jsonStr)) return null;
		return objectMapper.readValue(jsonStr, clazz);
	}

//	public final static <E extends BaseEntity> Pagination json2Pagn(String jsonStr, Class<E> resultClazz)
//			throws IOException
//	{
//		if (!hasText(jsonStr) || resultClazz == null) return Pagination.emptyInstance();
//		int reFirst = jsonStr.indexOf("[");
//		int reLast = jsonStr.lastIndexOf("]");
//		if (reLast <= reFirst) throw new IllegalArgumentException("不是合法的Pagination Json 字符串.");
//		String reStr = jsonStr.substring(reFirst, reLast + 1);
//		Pagination pg = objectMapper.readValue(jsonStr, Pagination.class);
//		pg.setResult(json2List(reStr, resultClazz));
//		return pg;
//	}

	/**
	 * 将json字符串转换成集合对象(尽量使用继承BaseEntity的对象)
	 * 
	 * @param <E>
	 *            对象类型
	 * @param jsonStr
	 *            json字符串
	 * @param eleClazz
	 *            转换后的对象类型，必须是继承BaseEntity的对象
	 * @return 转换后的集合
	 * @throws IOException
	 */
	public final static <E> List<E> json2List(String jsonStr, Class<E> eleClazz) throws IOException
	{
		if (!hasText(jsonStr) || eleClazz == null) return new ArrayList<E>();
		JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, eleClazz);
		return objectMapper.readValue(jsonStr, type);
	}

	/**
	 * json字符串转换成Map对象
	 * 
	 * @param jsonStr
	 *            json字符串
	 * @param keyClass
	 *            key的对象类型
	 * @param valueClass
	 *            value的对象类型
	 * @return 转换后的Map
	 * @throws IOException
	 */
	public final static <K, V> Map<K, V> json2Map(String jsonStr, Class<K> keyClass, Class<V> valueClass)
			throws IOException
	{
		if (!hasText(jsonStr) || keyClass == null || valueClass == null) return new HashMap<K, V>();
		MapType type = objectMapper.getTypeFactory().constructMapType(Map.class, keyClass, valueClass);
		return objectMapper.readValue(jsonStr, type);
	}

	
	/**
	 * 格式化json-- from 杨境的JsonFormatTools
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static String formatJson(String jsonStr)
	{
		if (jsonStr == null || "".equals(jsonStr)) return "";
		StringBuilder sb = new StringBuilder();
		char last = '\0';
		char current = '\0';
		int indent = 0;
		for (int i = 0; i < jsonStr.length(); i++)
		{
			last = current;
			current = jsonStr.charAt(i);
			switch (current)
			{
			case '{':
			case '[':
				sb.append(current);
				sb.append('\n');
				// 如果是{ [ 下一行需要缩进
				indent++;
				addIndentBlank(sb, indent);
				break;
			case '}':
			case ']':
				sb.append('\n');
				indent--;
				addIndentBlank(sb, indent);
				sb.append(current);
				break;
			case ',':
				sb.append(current);
				if (last != '\\')
				{
					sb.append('\n');
					addIndentBlank(sb, indent);
				}
				break;
			default:
				sb.append(current);
			}
		}
		return sb.toString();
	}

	/**
	 * 添加空格
	 * 
	 * @param sb
	 * @param indent
	 */
	private static void addIndentBlank(StringBuilder sb, int indent)
	{
		// “\t”为“转义字符”,代表的是一个tab，也就是8个空格。
		for (int i = 0; i < indent; i++)
		{
			// sb.append('\t');
			// 八个空格太多了，改为4个空格
			sb.append("    ");
		}
	}
	
	/**
	 * json字符串转换为JsonNode对象（ 树结构） 
	 * @author 杨境
	 * @param jsonStr
	 * @return JsonNode
	 * @throws IOException
	 */
	public static JsonNode json2Node(String jsonStr) throws IOException {
		if (!hasText(jsonStr)) return null;
		return objectMapper.readTree(jsonStr);
	}
	
}
