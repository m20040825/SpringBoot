package bdqn.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.StringUtils;

/**
 * excel导入导出
 *
 * @author Panda Baby
 * @see Excel
 * @since 1.7
 * @date 2018-09-05
 */
public final class ExcelUtil {

	/**
	 * 获取一行数据
	 *
	 * @param row     一行单元格对象
	 * @param columns 字段名
	 * @return
	 */
	private static Map<String, String> getCellValue(HSSFRow row, String[] fields) {
		int j = 0;
		HashMap<String, String> map = new HashMap<String, String>();
		for (String field : fields) {
			if (field != null && "" != field) {
				HSSFCell cell = row.getCell(j);
				Object value = null;
				if (cell != null)
					if (CellType.STRING == cell.getCellTypeEnum()) {
						cell.setCellType(CellType.STRING);
						value = cell.getStringCellValue().trim();
					} else if (CellType.NUMERIC == cell.getCellTypeEnum()) {
						cell.setCellType(CellType.NUMERIC);
						value = cell.getNumericCellValue();
					} else if (CellType.ERROR == cell.getCellTypeEnum())
						value = "";
				if (!StringUtils.isEmpty(value))
					map.put(field, value.toString());
			}
			j++;
		}
		return map;
	}

	/**
	 * 导入excel
	 *
	 * @param inputStream 文件流;
	 * @param columnStr   字段集合;
	 * @return List<map>
	 * @throws NullPointerException
	 * @throws IOException
	 */
	public static List<Map<String, String>> importExcel(InputStream inputStream, String fieldStr) throws NullPointerException, IOException {
		HSSFRow row = null;
		HSSFWorkbook workbook = null;
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		workbook = new HSSFWorkbook(inputStream);// 读取文件；

		// 读取第一页,循环获取单元格的值；
		HSSFSheet sheet = workbook.getSheetAt(0);
		String[] fields = fieldStr.split(",");
		int i = sheet.getFirstRowNum() + 2;
		int lastRowNum = sheet.getLastRowNum() + 1;
		// 此处应该抛出异常;
		if (2 == lastRowNum) {
			if (workbook != null)
				workbook.close();
			return mapList;
		}

		try {
			for (; i < lastRowNum; i++) {
				Map<String, String> map = null;
				row = sheet.getRow(i);
				// 读取一行格数据；
				map = getCellValue(row, fields);
				// 将读取的数据放到list集合中去；
				mapList.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (workbook != null)
				workbook.close();
			if (inputStream != null)
				inputStream.close();
		}
		return mapList;
	}

	/**
	 * 导出excel
	 *
	 * @param excel excel对象；
	 * @return Integer 导出数据条数；
	 */
	public static Integer exportExcel(Excel excel) {
		// 新建excel文件；
		File excelFile = excel.getExcelFile();
		if (!excelFile.exists())
			excelFile.mkdirs();
		excelFile = new File(excelFile.getPath(), excel.getFileName());
		FileOutputStream outputStream = null;
		try {
			excelFile.createNewFile();
			outputStream = new FileOutputStream(excelFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// 创建工作簿对象
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 创建单元格样式
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setShrinkToFit(Boolean.TRUE);
		style.setWrapText(Boolean.FALSE);
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 10);
		style.setFont(font);

		// 创建标题行；
		excel.setColumns(new StringBuilder(excel.getColumns()).insert(0, "序号,").toString());
		String[] columnsArray = excel.getColumns().split(",");
		HSSFSheet sheet = workbook.createSheet(excel.getTitle());
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnsArray.length - 1));

		// 设置列的宽度；
		int i = 1;
		if (null == excel.getColumnsWidth())
			for (; i < excel.getColumns().length() + 1; i++)
				sheet.setColumnWidth(i, 80 * 100);
		else
			for (String columnWidth : excel.getColumnsWidth().split(",")) {
				sheet.setColumnWidth(i, 100 * Integer.parseInt(columnWidth));
				i++;
			}

		// 创建一个sheet页；
		HSSFCellStyle tCellStyle = workbook.createCellStyle();
		tCellStyle.setAlignment(HorizontalAlignment.CENTER);
		font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 18);
		tCellStyle.setFont(font);
		HSSFRow rowt = sheet.createRow(0);
		HSSFCell tCell = rowt.createCell(0);
		tCell.setCellStyle(tCellStyle);
		tCell.setCellValue(excel.getTitle());

		// 创建标题行
		HSSFCellStyle ttCellStyle = workbook.createCellStyle();
		ttCellStyle.setAlignment(HorizontalAlignment.CENTER);
		ttCellStyle.setBorderBottom(BorderStyle.THIN);
		ttCellStyle.setBorderLeft(BorderStyle.THIN);
		ttCellStyle.setBorderRight(BorderStyle.THIN);
		ttCellStyle.setBorderTop(BorderStyle.THIN);
		font.setFontHeightInPoints((short) 18);
		ttCellStyle.setFont(font);

		// 创建标题行
		HSSFCellStyle bottomCellStyle = workbook.createCellStyle();
		bottomCellStyle.setAlignment(HorizontalAlignment.LEFT);
		bottomCellStyle.setBorderBottom(BorderStyle.THIN);
		bottomCellStyle.setBorderLeft(BorderStyle.THIN);
		bottomCellStyle.setBorderRight(BorderStyle.THIN);
		bottomCellStyle.setBorderTop(BorderStyle.THIN);
		font.setFontHeightInPoints((short) 18);
		bottomCellStyle.setFont(font);

		// 创建标题行
		HSSFCellStyle titleCellStyle = workbook.createCellStyle();
		titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
		titleCellStyle.setBorderBottom(BorderStyle.THIN);
		titleCellStyle.setBorderLeft(BorderStyle.THIN);
		titleCellStyle.setBorderRight(BorderStyle.THIN);
		titleCellStyle.setBorderTop(BorderStyle.THIN);
		titleCellStyle.setWrapText(Boolean.TRUE);

		// 创建一行excel;
		HSSFRow row = sheet.createRow(1);
		row.setHeight((short) 600);

		// 设置每一列的属性和标题；
		HSSFCell cell = null;
		i = 0;
		for (String column : columnsArray) {
			cell = row.createCell(i);
			cell.setCellStyle(style);
			cell.setCellValue(column);
			i++;
		}

		/**
		 * 将数据写入单元格;
		 */
		i = 1;
		int j = 0;
		row = null;
		Object value = null;
		String[] fieldArray = null;// 字段数组；
		List<Map<String, Object>> mapList = excel.getMapList();
		fieldArray = excel.getFields().split(",");
		if (fieldArray.length == 1)
			fieldArray = excel.getFields().split("，");
		if (mapList != null && !mapList.isEmpty())
			for (Map<String, Object> map : mapList) {
				row = sheet.createRow(i + 1);
				row.setHeight((short) 350);
				row.createCell(0).setCellStyle(style);
				row.getCell(0).setCellValue(i);
				j = 1;
				// 写入一行数据；
				for (String field : fieldArray) {
					row.createCell(j).setCellStyle(style);
					value = map.get(field);
					if (!StringUtils.isEmpty(value))
						row.getCell(j).setCellValue(value.toString());
					j++;
				}
				i++;
			}

		// 把excel对象写入文件，并关闭；
		try {
			if (workbook != null) {
				workbook.write(outputStream);
				workbook.close();
			}
			if (outputStream != null)
				outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return i - 1;
	}
}