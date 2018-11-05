package bdqn.service;

import java.util.List;
import java.util.Map;

import bdqn.pojo.Page;

/**
 * 系统通用Service接口<br>
 * Integer(mybatis)会将null转换为0<br>
 * 
 * @author 毛俐鹏;
 * @since jdk1.7.0_51;
 * @date 2018-02-28;
 */
public interface Service {

	/** 新增对象 */
	Integer add(Object bean);

	/** 新增集合 */
	Integer add(Object[] beans);

	/** 按id删除,最常用方法 */
	Integer del(int[] ids);

	/** 删除对象 */
	Integer del(Object bean);

	/** 修改对象 */
	Integer update(Object bean);

	/** 通用唯一验证或查单个对象 */
	Map<String, Object> selectOne(Map<String, String> map);

	/** 查询总记录数 */
	Integer selectTotal(Object bean);

	/**
	 * 系统通用分页查询
	 * 
	 * @param page
	 *            分页对象
	 * @return Map 返回总条数和List集合
	 */
	Map<String, Object> selectPage(Page page);

	/**
	 * 查询集合
	 * 
	 * @param bean
	 *            查询条件
	 * @return List 返回元素为Map的集合
	 */
	List<Map<String, Object>> selectList(Object bean);

	/**
	 * 查询集合
	 * 
	 * @param map
	 *            查询条件
	 * @return 返回子元素为Map的List集合
	 */
	List<Map<String, Object>> selectList(Map<String, String> map);

	/** 导入Excel文件 */
	Integer importExcel(Map<String, Object> map);

	/** 导出Excel文件 */
	Integer exportExcel(Map<String, String> map, String filePath, String fileName);
}
