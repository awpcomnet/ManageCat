package com.cat.manage.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @Description: Excel工具类
 * @author 王航
 * @date 2015年11月23日 上午11:03:22
 */
public class ExcelUtil {

	private ExcelUtil() {}

	/**
	 * 描述：导出单工作表Excel
	 * 
	 * @param os
	 * @param sheetName
	 *            工作表名称,缺省值为sheet1
	 * @param title
	 *            文件第一列标题集合
	 * @param listContent
	 *            文件内容正文
	 * @param maxSheet
	 * 			  Excel工作表的最大数据量
	 * @return
	 */
	public final static boolean exportExcelForSingleSheet(OutputStream os, String sheetName, LinkedHashMap title,	List<?> listContent) {
		boolean isSuccess = true;
		//如果设定的工作表的最大数据不合法，则默认最大数值
		//maxSheet = (maxSheet > 65536 || maxSheet <= 0) ? 65536 : maxSheet;
		//计算总页数
		//int sheetTotalNum = listContent.size()%maxSheet == 0 ? listContent.size()/maxSheet : listContent.size()/maxSheet+1;
		
		WritableWorkbook workBook = null;
		try {
			// 创建工作簿
			workBook = Workbook.createWorkbook(os);

			// 创建工作表
			WritableSheet sheet = workBook.createSheet(	(sheetName == null) ? "sheet1" : ("".equals(sheetName.trim()) ? "sheet1" : sheetName), 0);

			// 设置纵横打印，默认纵打印
			jxl.SheetSettings sheetSet = sheet.getSettings();
			sheetSet.setProtected(false);
			
			// 获取单元格模板
			WritableCellFormat wcf_title = ExcelUtil.getCellModelForTitle(10);
			WritableCellFormat wcf_content = ExcelUtil.getCellModelForContent(10);
			
			// 写入标题内容
			ExcelUtil.writeTitle(sheet, wcf_title, title, 0, 0);
			ExcelUtil.writeContent(sheet, wcf_content, title, listContent, 1, 0);
			
			//将缓存总内容写入Excel
			workBook.write();
			
			//清理输出流
			os.flush();
			
		} catch (Exception e) {
			// 打印错误日志
			isSuccess = false;
			LOG.error("导出Excel失败" , e);
			e.printStackTrace();
		} finally {
			if (workBook != null) {
				try {
					workBook.close();
				} catch (Exception e2) {
					//打印错误日志
					LOG.error("关闭工作簿失败" , e2);
					e2.printStackTrace();
				}
			}
		}
		return isSuccess;
	}

	/**
	 * 描述：获取单元格模型,标题
	 * 
	 * @param fontSize 字体大小,缺省值 12
	 * @return
	 */
	private final static WritableCellFormat getCellModelForTitle(int fontSize) throws Exception{
		WritableCellFormat wcf = null;
		try {
			// 获取画笔
			WritableFont font = new WritableFont(WritableFont.ARIAL, fontSize <= 0 ? 12 : fontSize, WritableFont.BOLD);

			// 设置单元格
			wcf = new WritableCellFormat(font);
			wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE);//文字垂直对齐
			wcf.setAlignment(Alignment.CENTRE);//文字居中对齐
			wcf.setBackground(Colour.GRAY_25);
			wcf.setWrap(false);
		} catch (Exception e) {
			throw e;
		}
		return wcf;
	}
	
	/**
	 * 描述：获取单元格模型,内容
	 * 
	 * @param fontSize 字体大小,缺省值 10
	 * @return
	 */
	private final static WritableCellFormat getCellModelForContent(int fontSize) throws Exception {
		WritableCellFormat wcf = null;
		// 获取画笔
		WritableFont font = new WritableFont(WritableFont.ARIAL,
				fontSize <= 0 ? 10 : fontSize);

		// 设置单元格
		wcf = new WritableCellFormat(font);
		wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
		wcf.setVerticalAlignment(VerticalAlignment.CENTRE);// 文字垂直对齐
		wcf.setAlignment(Alignment.LEFT);// 文字居中对齐
		wcf.setWrap(false);
		return wcf;
	}
	
	/**
	 * 写入标题内容
	 * @param sheet 工作簿
	 * @param wcf 单元格格式
	 * @param map 标题内容
	 * @param rowNum 行数，0为第一行
	 * @param colNum 列数，缺省值0
	 * @return
	 */
	private final static WritableSheet writeTitle(WritableSheet sheet, WritableCellFormat wcf, LinkedHashMap map, int rowNum, int colNum) throws Exception{
		rowNum = rowNum < 0 ? 0: rowNum;
		colNum = colNum < 0 ? 0: colNum;
		for(Iterator iter = map.entrySet().iterator();iter.hasNext();){ 
            Map.Entry element = (Map.Entry)iter.next(); 
            Object strObj = element.getValue(); 
            sheet.addCell(new Label(colNum, rowNum, strObj.toString(), wcf));
            colNum++;
		} 
		
		return sheet;
	}
	
	/**
	 * 写入正文内容
	 * @param sheet 工作簿
	 * @param wcf 单元格格式
	 * @param map 标题对应
	 * @param listContent 正文内容
	 * @param rowNum 起始行，缺省值为1
	 * @param colNum 起始列，缺省值为0
	 * @param os 用于定时清理缓存
	 * @return
	 * @throws Exception
	 */
	private final static WritableSheet writeContent(WritableSheet sheet, WritableCellFormat wcf, LinkedHashMap map,	List<?> listContent, int rowNum, int colNum) throws Exception {
		rowNum = rowNum < 0 ? 1 : rowNum;
		colNum = colNum < 0 ? 0 : colNum;
		
		List<Map> list = ExcelUtil.getListContent(listContent);
		for (Map contentMap : list) {
			// 根据标题顺序写入正文内容
			int col = colNum;
			for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
				Map.Entry element = (Map.Entry) iter.next();
				Object key = element.getKey();
				String content = (String) contentMap.get(key);

				sheet.addCell(new Label(col, rowNum, content, wcf));
				col++;
			}
			rowNum++;
		}

		return sheet;
	}
	
	/**
	 * 将领域模型转换为map
	 * @param listContent
	 * @return
	 */
	private final static List<Map> getListContent(List<?> listContent) throws Exception{
		List<Map> list = new ArrayList<Map>();
		
		if(listContent == null){
			return list;
		}
		
		Field[] fields = null;
		 for(Object obj:listContent){
			 fields = obj.getClass().getDeclaredFields();
			 Map contentMap = new HashMap();
			 for(Field v : fields){
				 v.setAccessible(true);
                 Object va = v.get(obj);
                 String name = v.getName();
                 if(va == null){
                	 va = "";
                 }
                 contentMap.put(name, va.toString());
			 }
			 
			 list.add(contentMap);
		 }
		 return list;
	}
	
	/**********************************************读取Excel文档*************************************************************/
	/**
	 * 默认从第一个工作簿，第一行读取数据
	 * @param fromFile 导入分档位置
	 * @param title 导入Excel对应列名称（该名称为英文代码名称，arg不为NULL的时候需要根据英文名称设置读取到的值。传入时要有序）
	 * @param arg 需要返回的对象类型，null则返回map对象集合
	 * @return
	 * @throws Exception
	 */
	public static List<Object> importExcel(File fromFile, List<String> title, Class<?> arg) throws Exception{
		return importExcel(fromFile, title, arg, 0, 0);
	}
	
	/**
	 * 默认第一行读取数据
	 * @param fromFile 导入分档位置
	 * @param title 导入Excel对应列名称（该名称为英文代码名称，arg不为NULL的时候需要根据英文名称设置读取到的值。传入时要有序）
	 * @param arg 需要返回的对象类型，null则返回map对象集合
	 * @return
	 * @throws Exception
	 */
	public static List<Object> importExcel(File fromFile, List<String> title, Class<?> arg, int sheetNum) throws Exception{
		return importExcel(fromFile, title, arg, sheetNum, 0);
	}
	
	/**
	 * 
	 * @param fromFile 导入分档位置
	 * @param title 导入Excel对应列名称（该名称为英文代码名称，arg不为NULL的时候需要根据英文名称设置读取到的值。传入时要有序）
	 * @param arg 需要返回的对象类型，null则返回map对象集合
	 * @param rowNum 从excel第几行读取数据，第一行为0 
	 * @return
	 * @throws Exception
	 */
	public static List<Object> importExcel(File fromFile, List<String> title, Class<?> arg, int sheetNum, int rowNum) throws Exception{
		Workbook fromWorkbook = null;
		
		//读取源模板
		fromWorkbook = getTemplate(fromFile);
		
		//获取模板内容
		return getResultList(fromWorkbook, title, arg, sheetNum, rowNum);
		
	}
	
	
	/**
	 * 获取导出模板
	 * @throws Exception 
	 */
	private static Workbook getTemplate(File fromFile) throws Exception{
		//读取源模板
		if(!fromFile.exists())
			throw new Exception("源模板不存在");
		if(!fromFile.isFile())
			throw new Exception("源模板为非法文件");
		if(!fromFile.canRead())
			throw new Exception("源模板不可读取");
        
		Workbook workBook = Workbook.getWorkbook(fromFile);
		return workBook;
	}
	
	/**
	 * 读取模板内容
	 * @param workBook
	 * @param sheetNum 读取第几个工作簿，第一个为 0 
	 * @return
	 * @throws Exception 
	 */
	private static Sheet readExcel(Workbook workBook, int sheetNum) throws Exception{
		//获取工作簿
		Sheet[] fromSheets = workBook.getSheets();
				
		if(fromSheets == null){
			throw new Exception("工作簿不存在");
		}
		
		if(fromSheets.length < sheetNum+1){
			throw new Exception("不存在第"+sheetNum+1+"工作簿");
		}
		//获取目标工作簿
		return fromSheets[sheetNum];
	}
	
	
	/**
	 * 获取返回结果
	 * @param workBook
	 * @param title
	 * @param arg
	 * @param sheetNum
	 * @param rowNum
	 * @return
	 * @throws Exception 
	 */
	private static List<Object> getResultList(Workbook workBook, List<String> title, Class<?> arg, int sheetNum, int rowNum) throws Exception{
		List<Object> resultList = Lists.newArrayList();
		
		//获取目标工作簿
		Sheet fromSheet = readExcel(workBook, sheetNum);
		
		//返回处理后的数据
		return getResultHandle(arg, fromSheet, title, rowNum);
		
	}
	
	/**
	 * 返回结果处理，根据arg是否传入返回不同结果集
	 * @param arg
	 * @param fromSheet
	 * @param title
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private static List<Object> getResultHandle(Class<?> arg, Sheet fromSheet, List<String> title, int rowNum) throws InstantiationException, IllegalAccessException{
		if(arg == null)
			return getMapList(fromSheet, title, rowNum);
		
		return getArgList(arg, fromSheet, title, rowNum);
	}
	
	/**
	 * 返回arg实体类对象集合
	 * @param arg
	 * @param fromSheet
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private static List<Object> getArgList(Class<?> arg, Sheet fromSheet, List<String> title, int rowNum) throws InstantiationException, IllegalAccessException{
		List<Object> resultList = Lists.newArrayList();
		int rows = fromSheet.getRows();
		
		for(int i=rowNum; i<rows; i++){
			Cell[] cells = fromSheet.getRow(i);//行
			Object obj = arg.newInstance();
			for(int j=0,len=title.size(); j<len; j++){
				Cell cell = cells[j];
				String paramValue = cell.getContents();
				String paramName = title.get(j);//
				
				Field field;
				try {
					field = obj.getClass().getDeclaredField(paramName);
					field.setAccessible(true);
					field.set(obj, paramValue);
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				}
				
			}
			resultList.add(obj);
		}
		
		return resultList;
	}
	
	/**
	 * 返回Map集合
	 * @param fromSheet
	 * @param title
	 * @return
	 */
	private static List<Object> getMapList(Sheet fromSheet, List<String> title, int rowNum){
		List<Object> resultList = Lists.newArrayList();
		int rows = fromSheet.getRows();
		
		for(int i=rowNum; i<rows; i++){
			Cell[] cells = fromSheet.getRow(i);//行
			for(int j=0,len=title.size(); j<len; j++){
				Cell cell = cells[j];
				String paramValue = cell.getContents();
				String paramName = title.get(j);//
				Map<String, String> map = Maps.newHashMap();
				map.put(paramName, paramValue);
				resultList.add(map);
			}
		}
		
		return resultList;
	}
	
	private static final Logger LOG = LoggerFactory.getLogger(ExcelUtil.class);
}
