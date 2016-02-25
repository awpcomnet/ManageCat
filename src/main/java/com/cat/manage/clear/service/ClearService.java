package com.cat.manage.clear.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.BufferOverflowException;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.check.domain.Check;
import com.cat.manage.check.service.CheckService;
import com.cat.manage.clear.domain.MonthClear;
import com.cat.manage.common.exception.BusinessException;
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
		if(listContent == null || listContent.size() <= 0 ){
			throw new BusinessException("1", "导出数据错误");
		}
		
		LinkedHashMap title = Maps.newLinkedHashMap();
		title.put("sumUnitPrice", "下单总金额($)");
		title.put("sumUnitRmb", "实际下单总金额(￥)");
		title.put("sumUnitPostage", "实际邮费总金额(￥)");
		title.put("sumUnitCost", "实际成本总金额(￥)");
		title.put("sumUnitCostPlan", "定义成本总金额(￥)");
		title.put("sumSellPrice", "售出总金额(￥)");
		title.put("sumRefund", "补损总金额(￥)");
		title.put("sumProfit", "实际总利润(￥)");
		title.put("sumProfitPlan", "定义总利润(￥)");
		title.put("remark", "备注");
		
		ExcelUtil.exportExcelForSingleSheet(os, startTime+"-"+endTime+"收益结算", title, listContent);
	}
	
	public List<MonthClear> calculateMonthClear(String startTime, String endTime){
		BigDecimal sumSellPrice = new BigDecimal(0.0).setScale(2,BigDecimal.ROUND_HALF_UP);//售出总金额(￥)
		
		BigDecimal sumUnitCost = new BigDecimal(0.0).setScale(2,BigDecimal.ROUND_HALF_UP);//实际成本总金额(￥)
		BigDecimal sumRefund = new BigDecimal(0.0).setScale(2,BigDecimal.ROUND_HALF_UP);//补损总金额(￥)
		BigDecimal sumUnitCostPlan = new BigDecimal(0.0).setScale(2,BigDecimal.ROUND_HALF_UP);//定义成本总金额(￥)
		
		BigDecimal sumUnitPrice = new BigDecimal(0.0).setScale(2,BigDecimal.ROUND_HALF_UP);//下单总金额($)
		BigDecimal sumUnitPostage = new BigDecimal(0.0).setScale(2,BigDecimal.ROUND_HALF_UP);//邮费总金额(￥)
		
		BigDecimal sumProfit = new BigDecimal(0.0).setScale(2,BigDecimal.ROUND_HALF_UP);//实际总利润(￥)
		BigDecimal sumProfitPlan = new BigDecimal(0.0).setScale(2,BigDecimal.ROUND_HALF_UP);//定义总利润(￥)
		
		String remark = "注:实际总成本是指真实下单金额加真实邮费金额，其中真实邮费为转运公司每批货物的总邮费。定义总成本是指在入库时加重新分配的每个商品邮费后，得出的总成本。真实总成本与定义总成本将略有偏差。"
				+ "偏差原因举例：1.重新分配的邮费总值不等于真实转运公司每批货物的总邮费（偏高或偏低）"
				+ "2.转运公司同一批货物部分商品入库，重新分配的邮费之和与实际此批货物邮费不等";//备注
		
		//时间段内售出清单查询
		List<Selled> selledList = selledService.querySelledForTimeQuantum(new Selled(), startTime, endTime);
		if(selledList != null){
			for(Selled selled : selledList){
				Double sellingPrice = selled.getSellingPrice();
				Integer sellNum = selled.getSellNum();
				sumSellPrice.add(new BigDecimal(sellingPrice * sellNum));
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
//		//下单清单查询
//		List<Check> checkList = checkService.queryCheckForList(new Check(), startTime, endTime);
//		if(checkList != null){
//			for(Check check : checkList){
//				sumUnitPrice += check.getUnitPrice() * check.getNum();
//			}
//		}
//		
//		//查询邮寄清单主单
//		List<ShippedHead> shippedHeadList = shippedHeadService.queryShippedHeadForList(new ShippedHead(), startTime, endTime);
//		if(shippedHeadList != null){
//			for(ShippedHead shippedHead : shippedHeadList){
//				sumUnitPostage += shippedHead.getPostage();
//			}
//		}
//		
//		//查询入库后信息
//		List<Store> storeList = storeService.queryStoreForList(new Store(), startTime, endTime);
//		if(storeList != null){
//			for(Store store : storeList){
//				sumUnitCostPlan += (Double.parseDouble(store.getUnitCost()) * store.getNum());//定义总成本
//				sumUnitRmb += (Double.parseDouble(store.getUnitRmb()) * store.getNum());
//			}
//		}
//		
//		//实际总成本
//		sumUnitCost = sumUnitRmb + sumUnitPostage;
//		
//		//查询售出清单
//		List<Selled> selledList = selledService.querySelledForTimeQuantum(new Selled(), startTime, endTime);
//		if(selledList != null){
//			for(Selled selled : selledList){
//				sumSellPrice += (selled.getSellingPrice() * selled.getSellNum());
//				sumRefund += (selled.getRefund() * selled.getSellNum());
//			}
//		}
//		
//		//计算总利润
//		BigDecimal sumUnitPriceB = new BigDecimal(sumUnitPrice).setScale(2,BigDecimal.ROUND_HALF_UP);
//		BigDecimal sumUnitRmbB = new BigDecimal(sumUnitRmb).setScale(2,BigDecimal.ROUND_HALF_UP);
//		BigDecimal sumUnitPostageB = new BigDecimal(sumUnitPostage).setScale(2,BigDecimal.ROUND_HALF_UP);
//		BigDecimal sumUnitCostPlanB = new BigDecimal(sumUnitCostPlan).setScale(2,BigDecimal.ROUND_HALF_UP);
//		BigDecimal sumUnitCostB = new BigDecimal(sumUnitCost).setScale(2,BigDecimal.ROUND_HALF_UP);
//		BigDecimal sumSellPriceB = new BigDecimal(sumSellPrice).setScale(2,BigDecimal.ROUND_HALF_UP);
//		BigDecimal sumRefundB = new BigDecimal(sumRefund).setScale(2,BigDecimal.ROUND_HALF_UP);
//		BigDecimal sumProfitB = new BigDecimal(sumProfit).setScale(2,BigDecimal.ROUND_HALF_UP);
//		BigDecimal sumProfitPlanB = new BigDecimal(sumProfitPlan).setScale(2,BigDecimal.ROUND_HALF_UP);
//		
//		sumProfitB = sumSellPriceB.subtract(sumRefundB).subtract(sumUnitCostB).setScale(2,BigDecimal.ROUND_HALF_UP);
//		sumProfitPlanB = sumSellPriceB.subtract(sumRefundB).subtract(sumUnitCostPlanB).setScale(2,BigDecimal.ROUND_HALF_UP);
//		
//		MonthClear monthClear = new MonthClear();
//		monthClear.setSumProfit(sumProfitB.doubleValue());
//		monthClear.setSumProfitPlan(sumProfitPlanB.doubleValue());
//		monthClear.setSumSellPrice(sumSellPriceB.doubleValue());
//		monthClear.setSumUnitCost(sumUnitCostB.doubleValue());
//		monthClear.setSumUnitCostPlan(sumUnitCostPlanB.doubleValue());
//		monthClear.setSumUnitPostage(sumUnitPostageB.doubleValue());
//		monthClear.setSumUnitPrice(sumUnitPriceB.doubleValue());
//		monthClear.setSumRefund(sumRefundB.doubleValue());
//		monthClear.setSumUnitRmb(sumUnitRmbB.doubleValue());
//		monthClear.setStartTime(startTime);
//		monthClear.setEndTime(endTime);
//		monthClear.setRemark(remark);
		
		List<MonthClear> clearList = Lists.newArrayList();
//		clearList.add(monthClear);
		return clearList;
	}
	
}
