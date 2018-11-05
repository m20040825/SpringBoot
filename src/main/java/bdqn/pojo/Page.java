package bdqn.pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import bdqn.service.Service;
import bdqn.util.FinalUtil;
import bdqn.util.ToolUtil;

/**
 * 封装分页信息与查询条件
 *
 * @author 毛俐鹏;
 * @date 2017-儿童节;
 */
public class Page implements Serializable {
	private static final Logger logger = Logger.getLogger(Page.class);
	private static final long serialVersionUID = 2048870873971601911L;
	private static final String TABLE = "base_";// 表名前缀名;
	private static final String PRIMARY = "_id";// 主键后缀名;
	private static final String URL = "selectPage";// 响应url后缀名;
	private static final String UPDATE_TIME = "_update_time DESC";// 默认排序字段;
	private String table = null;// 表名简写;
	private String valueId = null;// 主键值;
	private String order = null;// 排序字段和排序顺序;
	private String columns = "*";// 查询字段;
	private Integer page = 1;// 当前页面数；
	private Integer rows = 3;// 分页条数,设置为零时不分页；
	private Integer total = 0;// 总条数；
	private Integer totalPage = 1;// 总页数；<0是==1;
	private Map<String, String> map = new HashMap<String, String>();// 动态获取bean对象的属性和值；
	private StringBuilder where = null;// 拼接入参的查询条件;
	private String beanId = FinalUtil.SERVICE_IMPL_ID;// 拼接出实现类的id;
	private Service service = null;// 封装Service对象;
	private HttpSession session = null;// Session对象;
	private Boolean isOperate = Boolean.TRUE;// 是否添加操作列;

	/** 分页信息的构造 */
	public Page() {
		super();
	}

	/** 分页信息的构造 */
	public Page(String table, Map<String, String> map) {
		super();
		this.table = table;
		this.map = map;
	}

	public Integer getPage() {
		return page = page >= 0 ? page : 1;
	}

	// 添加可输入的页码;
	public void setPage(Integer page) {
		if (page > this.totalPage)
			this.page = this.totalPage;
		else if (page < 0)
			this.page = 0;
		else
			this.page = page;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getTotalPage() {
		if (total > 1)
			return totalPage = total % rows == 0 ? total / rows : total / rows + 1;
		else
			return 1;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getMinPage() {
		return rows * (page - 1);
	}

	public void setMinPage(Integer minPage) {
	}

	public Integer getMaxPage() {
		return page * rows;
	}

	public void setMaxPage(Integer maxPage) {
	}

	public String getTable() {
		if (table.contains("_"))
			return table;
		else
			return new StringBuilder(ToolUtil.getBeanIdAbb(table)).insert(0, TABLE).toString();
	}

	// SELECT * FROM base_user AS bu WHERE bu.u_r_id!=3;
	// map.put("u_r_id!",3);
	/** 做不等于查询 */
	public String getNoTable() {
		return getTable() + "!";
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getPrimary() {
		int beginIndex = table.lastIndexOf("_") + 1;
		int endIndex = table.lastIndexOf("_") + 2;
		return new StringBuilder(table.substring(beginIndex, endIndex)).append(PRIMARY).toString();
	}

	public String getValueId() {
		return valueId;
	}

	public void setValueId(String valueId) {
		this.valueId = valueId;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public String getUrl() {
		int beginIndex = table.lastIndexOf("_") + 1;
		table = table.substring(beginIndex);
		StringBuilder tableBuilder = ToolUtil.getBeanIdAbb(table);
		String url = String.valueOf(tableBuilder.append("/").append(Page.URL));
		// 不可取的方式；
		setUrl(url);
		return url;
	}

	public void setUrl(String url) {
	}

	/** 遍历并拼接查询条件 */
	public StringBuilder getWhere() {
		where = new StringBuilder("'");
		Iterator<Entry<String, String>> iter = null;
		if (null != map) {
			iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<String, String> entry = iter.next();
				String key = entry.getKey();
				String value = entry.getValue();
				where.append("and ").append(key).append(" like ''%").append(value).append("%'' ");
			}
		} else
			new RuntimeException(FinalUtil.ERROR);
		where.append("'");
		logger.debug("whereSql==>" + where);
		return where;
	}

	public void setWhere(StringBuilder where) {
		this.where = where;
	}

	public String getBeanId() {
		return beanId;
	}

	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}

	/** 动态调用Service层 */
	public Service getService() {
		try {
			if (null != table)
				service = (Service) FinalUtil.CTX.getBean(ToolUtil.getBeanId(table));
		} catch (Exception e) {
			service = FinalUtil.SERVICE;
		} finally {
			logger.debug("最终走的Service实现类==>" + service);
		}
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	/**
	 * 默认按照更新时间排序
	 *
	 * @return
	 */
	public String getOrder() {
		if (order == null) {
			int beginIndex = table.lastIndexOf("_") + 1;
			int endIndex = beginIndex + 1;
			return new StringBuilder(16).append(table.substring(beginIndex, endIndex)).append(Page.UPDATE_TIME).toString();
		} else
			return order;
	}

	/**
	 * 排序字段和排序顺序;
	 *
	 * @example 按id降序排列 u_id DESC
	 * @param order
	 */
	public void setOrder(String order) {
		this.order = order;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public String getPrefix() {
		int beginIndex = table.lastIndexOf("_") + 1;
		int endIndex = table.lastIndexOf("_") + 2;
		return table.substring(beginIndex, endIndex);
	}

	public void setPrefix(String prefix) {
	}

	public Boolean getIsOperate() {
		return isOperate;
	}

	public void setIsOperate(Boolean isOperate) {
		this.isOperate = isOperate;
	}
}