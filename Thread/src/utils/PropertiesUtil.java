package utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author LittleTree
 * @version 1.1
 *
 */
public class PropertiesUtil extends PropertyUtils {

	@SuppressWarnings("rawtypes")
	protected static final ThreadLocal classFieldsCache = new ThreadLocal();

	/**
	 * 单个属性setter
	 * @param target 目标对象
	 * @param property 属性名
	 * @param value setter值
	 * @throws IntrospectionException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void setProperty(Object target, String property, Object value) {
		try {
			PropertyUtils.setProperty(target, property, value);
		} catch (Exception e) {
			throw new RuntimeException(
				"exception when set property:" + property + " to Class:" + target.getClass().getName(), e);
		}
	}

	/**
	 * 单个属性setter
	 * @param target
	 * @param property
	 * @param value
	 */
	public static void setPropertyIgnoreFit(Object target, String property, Object value) {
		try {
			if (target instanceof Map) {
				PropertyUtils.setProperty(target, property, value);
			} else
				BeanUtils.setProperty(target, property, value);
		} catch (Exception e) {
			throw new RuntimeException(
				"exception when set property:" + property + " to Class:" + target.getClass().getName(), e);
		}
	}

	/**
	 * 单个属性getter
	 * @param target getter的对象
	 * @param property 属性名
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Object getProperty(Object target, String property) {
		try {
			return PropertyUtils.getProperty(target, property);
		} catch (NestedNullException nullEx) {
			return null;
		} catch (IllegalArgumentException ex1) {
			if (ex1.getMessage().indexOf("Null") > -1)
				return null;
			else
				throw ex1;
		} catch (Exception ex) {
			throw new RuntimeException(
				"exception when get property:" + property + " from Class:" + target.getClass().getName(), ex);
		}
	}

	/**
	 * 是否基本属性
	 * @param field
	 * @return
	 */
	public static boolean isPrimitive(Field field) {
		return field.getClass().isPrimitive();
	}

	/**
	 * 变成数据库风格的变量名
	 * @param str
	 * @return
	 */
	public static String toDataBaseStyle(String str) {

		char temp = 0;
		StringBuffer tmp = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			temp = str.charAt(i);
			if (temp > 64 && temp < 91) {
				tmp.append("_" + Character.toLowerCase(temp));
			} else
				tmp.append(temp);
		}
		return tmp.toString();
	}

	/**
	 * 变成JavaBean风格的属性名
	 * @param str
	 * @return
	 */
	public static String toBeanStyle(String str) {

		char downLine = '_';
		str = str.toLowerCase();
		char temp = 0;
		StringBuffer tmp = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			temp = str.charAt(i);
			if (temp == downLine) {
				tmp.append(Character.toUpperCase(str.charAt(++i)));
			} else
				tmp.append(temp);
		}
		return tmp.toString();
	}

	/**
	 * 取得类的所有属性，包括父类属性
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Field[] getAllFields(Class clazz) {
		Field[] field = clazz.getDeclaredFields();
		Class superClass = clazz.getSuperclass();
		while (superClass != Object.class) {
			Field[] newField = superClass.getDeclaredFields();
			Field[] all = new Field[field.length + newField.length];
			System.arraycopy(field, 0, all, 0, field.length);
			System.arraycopy(newField, 0, all, field.length, newField.length);
			field = all;
			superClass = superClass.getSuperclass();
		}
		return field;
	}

	/**
	 * 取得类属性
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Field getField(Class clazz, String fieldName) {
		// 从缓存读取
		Field clazzField = getCacheField(clazz, fieldName);
		if (clazzField != null)
			return clazzField;

		clazzField = getFieldFromClass(clazz, fieldName);
		if (clazzField != null) {
			// 缓存起属性
			setCacheField(clazz, clazzField);
		}

		return clazzField;
		// throw new GlException("no field named "+ fieldName+" in Class
		// "+clazz.getName());
	}

	/**
	 * 读取类属性对象
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Field getFieldFromClass(Class clazz, String fieldName) {
		Field[] fields = PropertiesUtil.getAllFields(clazz);
		Field field = null;
		for (int i = 0; i < fields.length; i++) {
			field = fields[i];
			// 做大小写敏感的匹配,这个地方,以后再做考虑.
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}
		return null;
	}

	/**
	 * 缓存类属性
	 * @param clazz
	 * @param field
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void setCacheField(Class clazz, Field field) {
		Map cache = (Map) classFieldsCache.get();
		if (cache == null) {
			cache = new HashMap();
			classFieldsCache.set(cache);
		}

		cache.put(clazz.getName() + "." + field.getName(), field);
	}

	/**
	 * 读取缓存属性
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static Field getCacheField(Class clazz, String fieldName) {
		Map cache = (Map) classFieldsCache.get();
		if (cache == null)
			return null;

		return (Field) cache.get(clazz.getName() + "." + fieldName);
	}
}
