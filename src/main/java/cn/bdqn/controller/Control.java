package cn.bdqn.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import cn.bdqn.pojo.Page;
import cn.bdqn.util.ClassReflectUtil;
import cn.bdqn.util.ToolUtil;

/**
 * 通用分页方案（分层混乱）
 *
 * @author 毛俐鹏;
 * @date 2017-06-06;
 */
public class Control {
	/** 分页的信使分子 */
	public Page page = new Page();

	/** 通过反射将查询条件封装到Page中 */
	public void setPage(Object bean) {
		if (null != bean) {
			this.page.setTable(ToolUtil.getBeanName(bean));
			this.page.setMap(ClassReflectUtil.getFieldValue(bean));
		} else
			throw new NullPointerException("pojo不能为空!");
	}

	/** 封装请求参数 */
	public static Map<String, String> getRequestParams(HttpServletRequest request) {
		Iterator<Entry<String, String[]>> iter = request.getParameterMap().entrySet().iterator();
		Map<String, String> map = new HashMap<String, String>();
		Entry<String, String[]> entry = null;
		String value = null;
		// 对于GET请求进行转码，将iso编码转为utf-8；
		if ("GET".equals(request.getMethod()))
			while (iter.hasNext()) {
				entry = iter.next();
				value = entry.getValue()[0].trim();
				if ("" != value) {
					try {
						value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					map.put(entry.getKey(), value);
				}
			}
		else
			while (iter.hasNext()) {
				entry = iter.next();
				value = entry.getValue()[0].trim();
				if ("" != value)
					map.put(entry.getKey(), value);
			}
		return map;
	}
}