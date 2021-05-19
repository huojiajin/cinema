package howard.cinema.core.manage.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import howard.cinema.core.manage.tools.JsonTools;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 *@name: ObjectMapperBuilder
 *@description: 数据转换
 *@author: huojiajin
 *@time: 2020/5/26 10:39
**/
public class ObjectMapperBuilder extends CommonAbstract
{
	private final String DEFAULT_FILTER_NAME = JsonTools.FILTER_NAME;
	private final ObjectMapper objectMapper;
	private String filterName;
	private SimpleDateFormat dateFormat;
	private LocalDateTimeDeserializer localDateTimeDeserializer;
	private LocalDateDeserializer localDateDeserializer;
	private LocalTimeDeserializer localTimeDeserializer;

	protected ObjectMapperBuilder()
	{
		objectMapper = new ObjectMapper();
	}

	public static ObjectMapperBuilder create()
	{
		return new ObjectMapperBuilder();
	}

	public ObjectMapperBuilder setDateFormat(SimpleDateFormat dateFormat)
	{
		this.dateFormat = dateFormat;
		return this;
	}

	public ObjectMapperBuilder setLocalDateTimeDeserializer(LocalDateTimeDeserializer localDateTimeDeserializer)
	{
		this.localDateTimeDeserializer = localDateTimeDeserializer;
		return this;
	}

	public ObjectMapperBuilder setLocalDateDeserializer(LocalDateDeserializer localDateDeserializer)
	{
		this.localDateDeserializer = localDateDeserializer;
		return this;
	}

	public ObjectMapperBuilder setLocalTimeDeserializer(LocalTimeDeserializer localTimeDeserializer)
	{
		this.localTimeDeserializer = localTimeDeserializer;
		return this;
	}

	public ObjectMapperBuilder setFilterName(String filterName)
	{
		this.filterName = filterName;
		return this;
	}

	public ObjectMapper build()
	{
		// 设置日期解析格式
		objectMapper.setDateFormat(dateFormat == null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") : dateFormat);
		objectMapper.setTimeZone(TimeZone.getDefault());
		// 注册java time模块
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		javaTimeModule.addDeserializer(LocalDateTime.class, localDateTimeDeserializer == null
				? new LocalDateTimeDeserializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : localDateTimeDeserializer);
		javaTimeModule.addDeserializer(LocalDate.class, localDateDeserializer == null
				? new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE) : localDateDeserializer);
		javaTimeModule.addDeserializer(LocalTime.class, localTimeDeserializer == null
				? new LocalTimeDeserializer(DateTimeFormatter.ISO_LOCAL_TIME) : localTimeDeserializer);
		objectMapper.registerModule(javaTimeModule);
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		// enum用toString方法
		objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
		objectMapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
		// 属性不存在时不报错
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		//按字母顺序排序属性
		objectMapper.enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
		// 默认filter
		FilterProvider filterProvider = new SimpleFilterProvider().addFilter(
				hasText(filterName) ? filterName : DEFAULT_FILTER_NAME,
				SimpleBeanPropertyFilter.serializeAllExcept(""));
		objectMapper.setFilterProvider(filterProvider);
		return objectMapper;
	}
}
