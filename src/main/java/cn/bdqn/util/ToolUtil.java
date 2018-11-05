package cn.bdqn.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

/**
 * 数据截取及类型转化的工具类<br>
 *
 * @author 毛俐鹏;
 * @date 2018-01-21;
 */
public final class ToolUtil {
	/** 生成32位UUID */
	public static synchronized String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 将List转化为String格式
	 */
	public static String listToStr(List<String> strList) {
		if (null != strList && !strList.isEmpty()) {
			StringBuilder builder = new StringBuilder(128);
			for (String str : strList)
				builder.append(str).append(",");
			return builder.substring(0, builder.length() - 1).toString();
		} else
			return "";
	}

	/** 将String转化为List */
	public static List<String> strToList(String columns) {
		if (null != columns && columns != "") {
			columns.trim();
			List<String> strList = new ArrayList<String>();
			for (String str : columns.split(","))
				strList.add(str);
			return strList;
		} else
			throw new RuntimeException("columns不能为空!");
	}

	/** 将Map<String,Object>转化为Map<String,String> */
	public static void toStrMap(Map<String, Object> map) {
		if (null != map && !map.isEmpty()) {
			Iterator<Entry<String, Object>> iter = map.entrySet().iterator();
			Entry<String, Object> entry = null;
			Object value = null;
			// 迭代Map;
			while (iter.hasNext()) {
				entry = iter.next();
				value = entry.getValue();
				if (String.class != value.getClass())
					// 去掉时间格式的.0;
					if (21 == value.toString().length())
						map.put(entry.getKey(), value.toString().substring(0, 19));
			}
			return;
		}
	}

	/** 转化时间格式 */
	public static List<Map<String, Object>> toStrMapList(List<Map<String, Object>> mapList) {
		if (null != mapList && !mapList.isEmpty())
			for (Map<String, Object> map : mapList)
				toStrMap(map);
		return mapList;
	}

	/**
	 * 获取bean实体类简写名
	 *
	 * @param bean 实体对象;
	 */
	public static String getBeanName(Object bean) {
		if (null != bean && bean != "")
			return bean.getClass().getSimpleName();
		else
			throw new RuntimeException(FinalUtil.PARAMS_IS_NULL_EXCEPTION);
	}

	/** 将首字母转化为小写 */
	public static StringBuilder getBeanIdAbb(String table) {
		if (null != table && table != "")
			return new StringBuilder(16).append(table.substring(0, 1).toLowerCase()).append(table.substring(1));
		else
			throw new RuntimeException(FinalUtil.PARAMS_IS_NULL_EXCEPTION);
	}

	/** 拼接Service实现类的id */
	public static String getBeanId(String table) {
		if (null == table || table == "")
			return FinalUtil.SERVICE_IMPL_ID;
		else {
			StringBuilder tableBuilder = getBeanIdAbb(table).append(FinalUtil.SERVICE_IMPL_ID);
			return tableBuilder.toString();
		}
	}

	/**
	 * 生成一个系统时间的字符串<br>
	 * 格式为：yyyy-MM-dd<br>
	 */
	public static String getDateStr() {
		return ToolUtil.getDateStr(null);
	}

	/**
	 * 生成一个系统时间的字符串
	 *
	 * @param format 时间格式
	 * @return timeString(时间格式的字符串)
	 */
	public static String getDateStr(String format) {
		if (null == format || format == "")
			return new SimpleDateFormat(FinalUtil.DATE).format(new Date());
		else
			return new SimpleDateFormat(format).format(new Date());
	}

	/** 打印集合元素 */
	public static void sysoList(List<?> list) {
		if (null != list && list.size() > 0)
			for (Object obj : list)
				System.out.println("map==>" + obj);
		else
			throw new NullPointerException("list has no element!");
		System.out.println("list.size==>" + list.size());
	}

	/** 判断是否为纯数字 */
	public static boolean isNumber(String str) {
		for (int i = 0; i < str.length(); i++)
			if (str.charAt(i) < '0' || str.charAt(i) > '9')
				return false;
		return true;
	}

	public static void main(String[] args) {

		List<String> strList = new ArrayList<String>();
		for (int i = 0; i < 10; i++)
			strList.add(Integer.toString(i));
		System.out.println(ToolUtil.listToStr(strList));
	}
}