package howard.cinema.core.dao.entity.common;

import com.fasterxml.jackson.annotation.JsonFilter;
import howard.cinema.core.manage.tools.JsonTools;
import howard.cinema.core.manage.tools.SecurityUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 *@name: BaseEntity
 *@description: 基础实体类
 *@author: huojiajin
 *@time: 2020/5/26 10:52
**/
@JsonFilter(JsonTools.FILTER_NAME)
public abstract class BaseEntity
{
	/**
	 * 取得指定属性的值
	 * 
	 * @param propertyType
	 *            属性的类型
	 * @param propertyName
	 *            属性的名字
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public final <T> T getPropertyValue(Class<T> propertyType, String propertyName)
	{
		Object value = getPropertyValue(propertyName);
		return (T) value;
	}

	public Object getPropertyValue(String propertyName) {
		Method method = getMethod(propertyName, true);
		try
		{
			return method.invoke(this);
		}
		catch (Exception e)
		{
			throw new RuntimeException("read property failed:" + propertyName, e);
		}
	}

	public final String toLikeStr(String propertyName)
	{
		String rs = getPropertyValue(String.class, propertyName);
		return StringUtils.hasText(rs) ? "%" + rs + "%" : null;
	}

	public final String toLeftLikeStr(String propertyName)
	{
		String rs = getPropertyValue(String.class, propertyName);
		return StringUtils.hasText(rs) ? "%" + rs : null;
	}

	public final String toRightLikeStr(String propertyName)
	{
		String rs = getPropertyValue(String.class, propertyName);
		return StringUtils.hasText(rs) ? rs + "%" : null;
	}

	/**
	 * 设置指定属性的值
	 * 
	 * @param propertyName
	 *            属性的名字
	 * @param propertyValue
	 *            属性的值
	 */
	public final void setPropertyValue(String propertyName, Object propertyValue)
	{
		Method method = getMethod(propertyName, false);
		try
		{
			method.invoke(this, propertyValue);
		}
		catch (Exception e)
		{
			throw new RuntimeException("write property failed:" + propertyName, e);
		}
	}

	private final Method getMethod(String propertyName, boolean getMethod)
	{
		PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(getClass(), propertyName);
		if (pd == null) throw new RuntimeException("can not find property:" + propertyName);
		Method method = getMethod ? pd.getReadMethod() : pd.getWriteMethod();
		return method;
	}

	public String toString()
	{
		return toJson();
	}

	/**
	 * 当前实体转换成json格式后的MD5摘要值
	 * 
	 * @return md5
	 * @see {@link #toJson()}
	 */
	public final String jsonMD5()
	{
		String str = toJson();
		if (str == null) throw new RuntimeException("can not covert this instance to json");
		return SecurityUtil.encryptStr(str, SecurityUtil.HashType.MD5, true);
	}

	/**
	 * 当前实体转换成json格式字符串
	 * 
	 * @return 转换失败时返回null
	 */
	public final String toJson()
	{
		return toJson(false);
	}

	/**
	 * 当前实体转换成json格式字符串
	 * 
	 * @return 转换失败时返回null
	 */
	public final String toJson(boolean format)
	{
		try
		{
			String jsonStr = JsonTools.toJsonStr(this);
			return format ? JsonTools.formatJson(jsonStr) : jsonStr;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	protected static <T extends BaseEntity> T fromJson(String jsonStr, Class<T> clazz)
	{
		try
		{
			return JsonTools.json2Object(jsonStr, clazz);
		}
		catch (IOException e)
		{
			throw new RuntimeException("无法转换成:(" + clazz + ")JSON格式错误:" + jsonStr);
		}
	}

	protected boolean equals(String child, String other)
	{
		return objEquals(child, other);
	}

	protected int hashCode(String id)
	{
		return objHashCode(id);
	}

	protected boolean equals(Long child, Long other)
	{
		return objEquals(child, other);
	}

	protected int hashCode(Long id)
	{
		return objHashCode(id);
	}

	protected boolean equals(Integer child, Integer other)
	{
		return objEquals(child, other);
	}

	protected int hashCode(Integer id)
	{
		return objHashCode(id);
	}

	protected boolean objEquals(Object child, Object other)
	{
		if (child == other) return true;
		if (child == null || other == null) return false;
		return child.equals(other);
	}

	protected int objHashCode(Object id)
	{
		if (id == null) return super.hashCode();
		return id.hashCode();
	}
}
