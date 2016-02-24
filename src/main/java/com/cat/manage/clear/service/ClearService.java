package com.cat.manage.clear.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.check.domain.Check;
import com.cat.manage.check.service.CheckService;
import com.cat.manage.clear.domain.MonthClear;
import com.cat.manage.common.util.ExcelUtil;
import com.cat.manage.selled.domain.Selled;
import com.cat.manage.selled.service.SelledService;
import com.cat.manage.shipped.domain.ShippedHead;
import com.cat.manage.shipped.service.ShippedHeadService;
import com.cat.manage.store.domain.Store;
import com.cat.manage.store.service.StoreService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @Description: 结算服务类
 * @author 王航
 * @date 2016年2月24日 下午2:32:38
 */
@Service
public class ClearService {
	@Autowired
	private CheckService checkService;
	
	@Autowired
	private ShippedHeadService shippedHeadService;
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private SelledService selledService;
	
	public void outPutExcelForProfit(String startTime, String endTime, OutputStream os){
		List<MonthClear> listContent = this.calculateMonthClear(startTime, endTime);
		
		LinkedHashMap title = Maps.newLinkedHashMap();
		title.put("sumUnitPrice", "下单总金额($)");
		title.put("sumUnitPostage", "邮费总金额(￥)");
		title.put("sumUnitCost", "成本总金额(￥)");
		title.put("sumSellPrice", "售出总金额(￥)");
		title.put("sumRefund", "补损总金额(￥)");
		title.put("sumProfit", "总利润(￥)");
		
		ExcelUtil.exportExcelForSingleSheet(os, startTime+"-"+endTime+"收益结算", title, listContent);
	}
	
	public List<MonthClear> calculateMonthClear(String startTime, String endTime){
		Double sumUnitPrice = 0.0;//下单总金额($)
		Double sumUnitPostage = 0.0;//邮费总金额(￥)
		Double sumUnitCost = 0.0;//成本总金额(￥)
		Double sumSellPrice = 0.0;//售出总金额(￥)
		Double sumRefund = 0.0;//补损总金额(￥)
		Double sumProfit = 0.0;//总利润(￥)
		
		//下单清单唯一编号集合
		List<Integer> checkIds = Lists.newArrayList();
		
		//下单清单查询
		List<Check> checkList = checkService.queryCheckForList(new Check(), startTime, endTime);
		if(checkList != null){
			for(Check check : checkList){
				sumUnitPrice += check.getUnitPrice();
				checkIds.add(check.getId());
			}
		}
		
		//查询邮寄清单主单
		List<ShippedHead> shippedHeadList = shippedHeadService.queryShippedHeadIncludeCheckIds((Integer[])checkIds.toArray(new Integer[]{}));
		if(shippedHeadList != null){
			for(ShippedHead shippedHead : shippedHeadList){
				sumUnitPostage += shippedHead.getPostage();
			}
		}
		
		//查询入库后信息
		List<Store> storeList = storeService.queryStoreByCheckIds((Integer[])checkIds.toArray(new Integer[]{}));
		if(storeList != null){
			for(Store store : storeList){
				sumUnitCost += Double.parseDouble(store.getUnitCost());
			}
		}
		
		//查询售出清单
		List<Selled> selledList = selledService.queryStoreByCheckIds((Integer[])checkIds.toArray(new Integer[]{}));
		if(selledList != null){
			for(Selled selled : selledList){
				sumSellPrice += selled.getSellingPrice();
				sumRefund += selled.getRefund();
			}
		}
		
		//计算总利润
		BigDecimal sumUnitPriceB = new BigDecimal(sumUnitPrice).setScale(2,BigDecimal.ROUND_HALF_UP);
		BigDecimal sumUnitPostageB = new BigDecimal(sumUnitPostage).setScale(2,BigDecimal.ROUND_HALF_UP);
		BigDecimal sumUnitCostB = new BigDecimal(sumUnitCost).setScale(2,BigDecimal.ROUND_HALF_UP);
		BigDecimal sumSellPriceB = new BigDecimal(sumSellPrice).setScale(2,BigDecimal.ROUND_HALF_UP);
		BigDecimal sumRefundB = new BigDecimal(sumRefund).setScale(2,BigDecimal.ROUND_HALF_UP);
		BigDecimal sumProfitB = new BigDecimal(sumProfit).setScale(2,BigDecimal.ROUND_HALF_UP);
		
		sumProfitB = sumSellPriceB.subtract(sumRefundB).subtract(sumUnitCostB).setScale(2,BigDecimal.ROUND_HALF_UP);
		
		MonthClear monthClear = new MonthClear();
		monthClear.setSumProfit(sumProfitB.doubleValue());
		monthClear.setSumSellPrice(sumSellPriceB.doubleValue());
		monthClear.setSumUnitCost(sumUnitCostB.doubleValue());
		monthClear.setSumUnitPostage(sumUnitPostageB.doubleValue());
		monthClear.setSumUnitPrice(sumUnitPriceB.doubleValue());
		monthClear.setSumRefund(sumRefundB.doubleValue());
		monthClear.setStartTime(startTime);
		monthClear.setEndTime(endTime);
		
		List<MonthClear> clearList = Lists.newArrayList();
		clearList.add(monthClear);
		return clearList;
	}
	
}
