package cn.bdqn.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

/**
 * Java反射机制，动态给对象属性赋值取值
 *
 * @author 深度开元;
 * @date 2017-05-10;
 * @author Panda Baby;
 * @date 2018-07-26;
 * @since 1.8;
 */
public final class ClassReflectUtil {
	private static Logger logger = Logger.getLogger(ClassReflectUtil.class);

	/**
	 * 将bean转化为Map
	 *
	 * @param bean 实体类;
	 * @return Map
	 */
	public static Map<String, String> getFieldValue(Object bean) {
		Class<?> cls = bean.getClass();
		Map<String, String> map = new HashMap<String, String>();
		// Method[] methods = cls.getDeclaredMethods();
		// 取出bean及其所有父类里的所有方法
		Method[] methods = cls.getMethods();
		// 获取bean及其所有父类里的所有属性；
		ArrayList<Field> fieldList = new ArrayList<Field>();
		while (cls != null) {
			fieldList.addAll(Arrays.asList(cls.getDeclaredFields()));
			cls = cls.getSuperclass();
		}
		// 获取原来(入参时)的bean的class属性;
		cls = bean.getClass();
		// 遍历bean中的属性,并插入到map中;
		for (Field field : fieldList) {
			try {
				String fieldType = field.getType().getSimpleName();
				String fieldGetName = parGetName(field.getName());
				if (!checkGetMet(methods, fieldGetName)) {
					continue;
				}
				Method fieldGetMet = cls.getMethod(fieldGetName, new Class[] {});
				Object fieldVal = fieldGetMet.invoke(bean, new Object[] {});
				String result = null;
				if ("Date".equals(fieldType)) {
					result = fmtDate((Date) fieldVal);
				} else if (fieldVal != null) {
					result = String.valueOf(fieldVal);
				}
				if (result != null && "" != result) {
					map.put(field.getName(), result);
				}
			} catch (Exception e) {
				continue;
			}
		}
		return map;
	}

	/**
	 * set属性的值到Bean
	 *
	 * @param bean
	 * @param map
	 */
	public static void setFieldValue(Object bean, Map<String, String> map) {
		Class<?> cls = bean.getClass();
		List<Field> fields = new ArrayList<Field>();
		// 取出bean及其所有父类里的所有方法
		Method[] methods = cls.getMethods();
		// 取出bean及其所有父类里的所有属性(含private)
		while (cls != null) {
			fields.addAll(Arrays.asList(cls.getDeclaredFields()));
			cls = cls.getSuperclass();
		}
		// 获取原来(入参时)的bean的class属性;
		cls = bean.getClass();
		// 遍历bean中的属性,并给bean赋值;
		for (Field field : fields) {
			try {
				String fieldSetName = parSetName(field.getName());
				if (!checkSetMet(methods, fieldSetName)) {
					continue;
				}
				Method fieldSetMet = cls.getMethod(fieldSetName, field.getType());
				String value = map.get(field.getName());
				if (null != value && "" != value) {
					String fieldType = field.getType().getSimpleName();
					if ("String".equals(fieldType)) {
						fieldSetMet.invoke(bean, value);
					} else if ("Date".equals(fieldType)) {
						Date temp = parseDate(value);
						fieldSetMet.invoke(bean, temp);
					} else if ("Integer".equals(fieldType) || "int".equals(fieldType)) {
						Integer intval = Integer.parseInt(value);
						fieldSetMet.invoke(bean, intval);
					} else if ("Long".equalsIgnoreCase(fieldType)) {
						Long temp = Long.parseLong(value);
						fieldSetMet.invoke(bean, temp);
					} else if ("Double".equalsIgnoreCase(fieldType)) {
						Double temp = Double.parseDouble(value);
						fieldSetMet.invoke(bean, temp);
					} else if ("Boolean".equalsIgnoreCase(fieldType)) {
						Boolean temp = Boolean.parseBoolean(value);
						fieldSetMet.invoke(bean, temp);
					} else if ("UUID".equals(fieldType)) {
						UUID temp = UUID.fromString(value);
						fieldSetMet.invoke(bean, temp);
					} else {
						logger.error("not supper type" + fieldType);
					}
				}
			} catch (Exception e) {
				continue;
			}
		}
	}

	/**
	 * 格式化string为Date
	 *
	 * @param datestr
	 * @return date
	 */
	private static Date parseDate(String datestr) {
		if (null == datestr || "" == datestr) {
			return null;
		}
		try {
			String fmtstr = null;
			if (datestr.indexOf(':') > 0) {
				fmtstr = FinalUtil.DATETIME;
			} else {
				fmtstr = FinalUtil.DATE;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(fmtstr, Locale.UK);
			return sdf.parse(datestr);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 日期转化为String
	 *
	 * @param date
	 * @return date string
	 */
	private static String fmtDate(Date date) {
		if (null == date) {
			return null;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(FinalUtil.DATETIME, Locale.US);
			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 判断是否存在某属性的 set方法
	 *
	 * @param methods
	 * @param fieldSetMet
	 * @return boolean
	 */
	private static boolean checkSetMet(Method[] methods, String fieldSetMet) {
		for (Method met : methods) {
			if (fieldSetMet.equals(met.getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否存在某属性的 get方法
	 *
	 * @param methods
	 * @param fieldGetMet
	 * @return boolean
	 */
	private static boolean checkGetMet(Method[] methods, String fieldGetMet) {
		for (Method met : methods) {
			if (fieldGetMet.equals(met.getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 拼接某属性的 get方法
	 *
	 * @param fieldName
	 * @return String
	 */
	private static String parGetName(String fieldName) {
		if (null == fieldName || "" == fieldName) {
			return null;
		}
		return new StringBuilder("get").append(fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1)).toString();
	}

	/**
	 * 拼接在某属性的 set方法
	 *
	 * @param fieldName
	 * @return String
	 */
	private static String parSetName(String fieldName) {
		if (null == fieldName || "" == fieldName) {
			return null;
		}
		return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}
}
