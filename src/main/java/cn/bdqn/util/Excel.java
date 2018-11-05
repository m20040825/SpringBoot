package cn.bdqn.util;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * excel封装类;
 *
 * @author 毛俐鹏;
 * @date 2018-1-6;
 * @未添加有参构造
 */
public class Excel implements Serializable {
	private static final long serialVersionUID = 5799184042426973156L;
	private String title = "";// 标题；
	private String fields = "";// 数据库字段集合；
	private String columns = "";// 列名集合；
	private String columnsWidth = null;// 每一列宽度；
	private String fileName = "";// 下载的excel文件名;
	private File excelFile = null;// 下载的excel文件；
	private List<Map<String, Object>> mapList = null;// 导出的数据;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getColumns() {
		return columns;
	}

	/**
	 * 请使用英文逗号隔开
	 *
	 * @param columns 列名
	 */
	public void setColumns(String columns) {
		this.columns = columns;
	}

	public String getColumnsWidth() {
		return columnsWidth;
	}

	public void setColumnsWidth(String columnsWidth) {
		this.columnsWidth = columnsWidth;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public File getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}

	public List<Map<String, Object>> getMapList() {
		return mapList;
	}

	/**
	 * 自动转化日期格式；
	 *
	 * @param mapList
	 */
	public void setMapList(List<Map<String, Object>> mapList) {
		this.mapList = ToolUtil.toStrMapList(mapList);
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}
}
