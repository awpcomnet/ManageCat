package com.cat.manage.clear.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.manage.clear.service.ClearService;
import com.cat.manage.common.exception.BusinessException;
import com.cat.manage.common.model.Srm;
import com.google.common.base.Strings;

/**
 * @Description: 清算控制器
 * @author 王航
 * @date 2016年2月24日 下午6:03:11
 */
@RestController
@RequestMapping("/clear")
public class ClearController {
	@Autowired
	private ClearService clearService;
	
	@RequestMapping("/profit")
	public Srm getProfitByMonth(String startTime, String endTime, HttpServletResponse response){
		if(Strings.isNullOrEmpty(startTime))
			throw new BusinessException("1", "起始时间不能为空");
		if(Strings.isNullOrEmpty(endTime))
			throw new BusinessException("1", "结束时间不能为空");
		
		OutputStream os = null;
		try {
			String excelName = "("+startTime+"-"+endTime+")结算文件.xls";
			response.setHeader("Content-disposition", "attachment;filename=" + new String(excelName.getBytes( "gb2312"), "ISO8859-1"));
			os = response.getOutputStream();
			clearService.outPutExcelForProfit(startTime, endTime, os);
		} catch (IOException e) {
			throw new BusinessException("1", "文件写出失败");
		} finally {
			try {
				os.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new Srm().setResultCode("0").setResultMessage("导出利润成功");
	}
	
	@RequestMapping("/selled")
	public Srm getSelledByMonth(String startTime, String endTime, HttpServletResponse response){
		if(Strings.isNullOrEmpty(startTime))
			throw new BusinessException("1", "起始时间不能为空");
		if(Strings.isNullOrEmpty(endTime))
			throw new BusinessException("1", "结束时间不能为空");
		
		OutputStream os = null;
		try {
			String excelName = "("+startTime+"-"+endTime+")售出清单文件.xls";
			response.setHeader("Content-disposition", "attachment;filename=" + new String(excelName.getBytes( "gb2312"), "ISO8859-1"));
			os = response.getOutputStream();
			clearService.outPutExcelForSelled(startTime, endTime, os);
		} catch (IOException e) {
			throw new BusinessException("1", "文件写出失败");
		} finally {
			try {
				os.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new Srm().setResultCode("0").setResultMessage("导出售出清单成功");
	}
	
	@RequestMapping("/store")
	public Srm getStoreByMonth(HttpServletResponse response){
		OutputStream os = null;
		try {
			String excelName = "仓库清单文件.xls";
			response.setHeader("Content-disposition", "attachment;filename=" + new String(excelName.getBytes( "gb2312"), "ISO8859-1"));
			os = response.getOutputStream();
			clearService.outPutExcelForStore(os);
		} catch (IOException e) {
			throw new BusinessException("1", "文件写出失败");
		} finally {
			try {
				os.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new Srm().setResultCode("0").setResultMessage("导出仓库清单成功");
	}
	
	@RequestMapping("/check")
	public Srm getCheckByMonth(String startTime, String endTime, HttpServletResponse response){
		if(Strings.isNullOrEmpty(startTime))
			throw new BusinessException("1", "起始时间不能为空");
		if(Strings.isNullOrEmpty(endTime))
			throw new BusinessException("1", "结束时间不能为空");
		
		OutputStream os = null;
		try {
			String excelName = "("+startTime+"-"+endTime+")下单清单文件.xls";
			response.setHeader("Content-disposition", "attachment;filename=" + new String(excelName.getBytes( "gb2312"), "ISO8859-1"));
			os = response.getOutputStream();
			clearService.outPutExcelForCheck(startTime, endTime, os);
		} catch (IOException e) {
			throw new BusinessException("1", "文件写出失败");
		} finally {
			try {
				os.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new Srm().setResultCode("0").setResultMessage("导出下单清单成功");
	}
}
