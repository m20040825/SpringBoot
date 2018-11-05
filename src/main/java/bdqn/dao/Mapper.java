package bdqn.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * 系统通用的DAO接口(mysql) <br/>
 * 但涉及到动态表名和列名时这样的参数需要我们在代码中手工进行处理来防止注入;
 *
 * @author 毛俐鹏；
 * @date 2018-01-13;
 */
public interface Mapper {
	/**
	 * 新增单个对象
	 *
	 * @param table 表名
	 * @param map   入参
	 * @return Integer 插入条数
	 * @throws DataIntegrityViolationException 数据类型异常
	 */
	Integer add(@Param("table") String table, @Param("map") Map<String, String> map) throws DataIntegrityViolationException;

	/** 新增对象集合 */
	Integer addList(@Param("table") String table, @Param("list") Object[] list);

	/** 根据对象删除 */
	Integer del(@Param("table") String table, @Param("prefix") String prefix, @Param("map") Map<String, String> map);

	/** 根据集合删除 */
	Integer delList(@Param("table") String table, @Param("primary") String primary, @Param("ids") Integer[] ids);

	/** 修改对象 */
	Integer update(@Param("table") String table, @Param("primary") String primary, @Param("valId") String valId, @Param("map") Map<String, String> map);

	/** 查询单个对象 */
	Map<String, Object> selectOne(@Param("table") String table, @Param("map") Map<String, String> map);

	/** 查询分页记录数 */
	Integer selectTotal(@Param("table") String table, @Param("prefix") String prefix, @Param("map") Map<String, String> map);

	/**
	 * 通用分页查询
	 *
	 * @param columns 字段集
	 * @param table   表名
	 * @param map     入参
	 * @param prefix  字段名前缀
	 * @param order   排序字段
	 * @param minPage 上页最大序数
	 * @param rows    分页条数
	 * @return List<Map>
	 */
	List<Map<String, Object>> selectList(@Param("columns") String columns, @Param("table") String table, @Param("map") Map<String, String> map, @Param("prefix") String prefix, @Param("order") String order, @Param("minPage") Integer minPage, @Param("rows") Integer rows);
}