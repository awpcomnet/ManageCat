package com.cat.manage.order.serviceTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cat.manage.clear.domain.MonthClear;
import com.cat.manage.clear.service.ClearService;
import com.cat.manage.common.util.ExcelUtil;
import com.google.common.collect.Maps;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
public class ClearTest {

	@Autowired
	private ClearService clearService;
	
	@Test
	public void testAddOrder() throws FileNotFoundException{
		
		LOG.info("测试清算开始");
		String startTime = "20160216";
		String endTime = "20160220";
		List<MonthClear> listContent = clearService.calculateMonthClear(startTime, endTime);
		
		LinkedHashMap title = Maps.newLinkedHashMap();
		title.put("sumUnitPrice", "下单总金额($)");
		title.put("sumUnitPostage", "邮费总金额(￥)");
		title.put("sumUnitCost", "成本总金额(￥)");
		title.put("sumSellPrice", "售出总金额(￥)");
		title.put("sumRefund", "补损总金额(￥)");
		title.put("sumProfit", "总利润(￥)");
		
		File f = new File("F:\\结算文件.xls");
		FileOutputStream os = new FileOutputStream(f);
		ExcelUtil.exportExcelForSingleSheet(os, startTime+"-"+endTime+"收益结算", title, listContent);
		LOG.info("测试清算结束");
	}
	
	private static final Logger LOG = LoggerFactory.getLogger(ClearTest.class);
}
