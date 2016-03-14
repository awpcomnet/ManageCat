package com.cat.manage.clear.service;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.check.domain.Check;
import com.cat.manage.check.service.CheckService;
import com.cat.manage.clear.domain.MonthClear;
import com.cat.manage.common.exception.BusinessException;
import com.cat.manage.common.util.ExcelUtil;
import com.cat.manage.selled.domain.Selled;
import com.cat.manage.selled.service.SelledService;
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
			throw new BusinessException("1", "导出数据为空");
		}
		
		LinkedHashMap title = Maps.newLinkedHashMap();
		title.put("startTime", "起始时间");
		title.put("endTime", "结束时间");
		title.put("sumUnitPrice", "下单总金额($)");
		title.put("sumUnitCost", "成本总金额(￥)");
		title.put("sumRefund", "补损总金额(￥)");
		title.put("sumSellPrice", "销售总金额(￥)");
		title.put("sumProfit", "总利润(￥)");
		title.put("profitRate", "利润率");
		title.put("remark", "备注");
		title.put("payby", "付款人");
		title.put("paybyUnitPrice", "付款总金额");
		title.put("payRatio", "比例(%)");
		
		ExcelUtil.exportExcelForSingleSheet(os, startTime+"-"+endTime+"收益结算", title, listContent);
	}
	
	public List<MonthClear> calculateMonthClear(String startTime, String endTime){
		BigDecimal sumSellPrice = new BigDecimal(0.0);//售出总金额(￥)
		BigDecimal sumUnitCost = new BigDecimal(0.0);//成本总金额(￥)
		BigDecimal sumRefund = new BigDecimal(0.0);//补损总金额(￥)
		BigDecimal sumUnitPrice = new BigDecimal(0.0);//下单总金额($)
		BigDecimal sumProfit = new BigDecimal(0.0);//实际总利润(￥)
		String remark = "实际利润与入库时邮费定义和成本定义有关，与实际所得利润可能存在差异。付款人的付款总金额仅包含下单金额,下单金额为换算后的人民币,不包含邮费。";//备注
		String profitRate = "0%";
		Map<String, Double> payInfo = Maps.newHashMap();
		Double sumUnitRmb = 0.0;
		
		List<Integer> checkIds = Lists.newArrayList();
		//时间段内售出清单查询
		List<Selled> selledList = selledService.querySelledForTimeQuantum(new Selled(), startTime, endTime);
		if(selledList != null){
			for(Selled selled : selledList){
				Double sellingPrice = selled.getSellingPrice();
				Double unitCost = selled.getUnitCost();
				Double refund = selled.getRefund();
				Integer sellNum = selled.getSellNum();
				sumSellPrice = sumSellPrice.add(new BigDecimal(sellingPrice * sellNum));
				sumUnitCost = sumUnitCost.add(new BigDecimal(unitCost * sellNum));
				sumRefund = sumRefund.add(new BigDecimal(refund));
				checkIds.add(selled.getCheckId());
				
				//统计付款人，以及付款总金额（仅下单金额,下单金额为换算后的人民币，不包含邮费）
				//已经保存过付款人,则累计付款金额
				//未保存付款人的，直接保存付款金额
				if(payInfo.containsKey(selled.getPayby().trim())){
					Double tempPrice = payInfo.get(selled.getPayby().trim());
					tempPrice += (selled.getUnitRmb() * selled.getSellNum());
					payInfo.put(selled.getPayby().trim(), tempPrice);
				} else {
					payInfo.put(selled.getPayby().trim(), selled.getUnitRmb() * selled.getSellNum());
				}
				
				sumUnitRmb += selled.getUnitRmb() * selled.getSellNum();
			}
		}
		
		//查询下单清单记录
		List<Check> checkList = checkService.queryCheckByIds((Integer[])checkIds.toArray(new Integer[]{}));
		if(checkList != null){
			for(Check check : checkList){
				Double unitPrice = check.getUnitPrice();
				Integer num = check.getNum();
				sumUnitPrice = sumUnitPrice.add(new BigDecimal(unitPrice * num));
			}
		}
		
		//计算利润
		sumProfit = sumSellPrice.subtract(sumUnitCost).subtract(sumRefund);
		if(sumUnitCost.doubleValue() != 0){
			profitRate = sumProfit.divide(sumUnitCost, 2).multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue() + "%";
		} else {
			profitRate = "成本金额为["+sumUnitCost.doubleValue()+"],利润率无法计算";
		}
		
		MonthClear monthClear = new MonthClear();
		monthClear.setStartTime(startTime);
		monthClear.setEndTime(endTime);
		monthClear.setSumSellPrice(sumSellPrice.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		monthClear.setSumUnitCost(sumUnitCost.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		monthClear.setSumRefund(sumRefund.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		monthClear.setSumUnitPrice(sumUnitPrice.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		monthClear.setSumProfit(sumProfit.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		monthClear.setRemark(remark);
		monthClear.setProfitRate(profitRate);
		
		List<MonthClear> clearList = Lists.newArrayList();
		clearList.add(monthClear);
		
		//换行写入付款人和付款金额
		Iterator iter = payInfo.entrySet().iterator();
		while(iter.hasNext()){
			Map.Entry entry = (Entry) iter.next();
			String payBy = entry.getKey()+"";
			Double paybyUnitPrice = Double.valueOf(entry.getValue()+"");
			BigDecimal bigPaybyUnitPrice = new BigDecimal(paybyUnitPrice);
			
			MonthClear tempMonthClear = new MonthClear();
			tempMonthClear.setPayby(payBy);
			tempMonthClear.setPaybyUnitPrice(bigPaybyUnitPrice.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
			if(sumUnitRmb != 0){
				BigDecimal temp = new BigDecimal(sumUnitRmb);
				String payRatioStr = String.format("%.02f", bigPaybyUnitPrice.divide(temp,4,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)).doubleValue());
				tempMonthClear.setPayRatio(payRatioStr);
			} 
			clearList.add(tempMonthClear);
		}
		
		return clearList;
	}
	
	/**
	 * 导出售出清单
	 * @param startTime
	 * @param endTime
	 * @param os
	 */
	public void outPutExcelForSelled(String startTime, String endTime, OutputStream os){
		List<Selled> listContent = selledService.querySelledForTimeQuantum(new Selled(), startTime, endTime);
		if(listContent == null || listContent.size() <= 0 ){
			throw new BusinessException("1", "导出数据为空");
		}
		
		LinkedHashMap title = Maps.newLinkedHashMap();
		title.put("brandName", "品牌名称");
		title.put("seriesName", "系列名称");
		title.put("singleName", "单品名称");
		title.put("unitPrice", "下单单价（$）");
		title.put("sellNum", "售出数量");
		title.put("unitRmb", "实际单价(￥)");
		title.put("unitPostage", "实际单个邮费(￥)");
		title.put("unitCost", "实际成本(￥)");
		title.put("sellingPrice", "实际售价(￥)");
		title.put("refund", "补损金额(￥)");
		title.put("sellTime", "售出时间");
		title.put("sumPrice", "总售出金额(￥)");
		title.put("payby", "下单付款人（非购买人）");
		title.put("remark", "备注");
		
		ExcelUtil.exportExcelForSingleSheet(os, startTime+"-"+endTime+"售出清单", title, listContent);
	}
	
	/**
	 * 导出仓库清单
	 * @param startTime
	 * @param endTime
	 * @param os
	 */
	public void outPutExcelForStore(OutputStream os){
		List<Store> listContent = storeService.queryStoreForValidity();
		if(listContent == null || listContent.size() <= 0 ){
			throw new BusinessException("1", "导出数据为空");
		}
		
		LinkedHashMap title = Maps.newLinkedHashMap();
		title.put("trackingNumber", "网站至转运公司快递单号");
		title.put("brandName", "品牌名称");
		title.put("seriesName", "系列名称");
		title.put("singleName", "单品名称");
		title.put("residueNum", "剩余数量");
		title.put("storeTime", "入库时间");
		title.put("payby", "付款人");
		title.put("unitPrice", "下单单价（美元单位）");
		title.put("unitRmb", "实际单价(人民币)");
		title.put("unitPostage", "实际单个邮费(人民币)");
		title.put("unitCost", "实际成本(人民币)");
		title.put("remark", "备注");

		ExcelUtil.exportExcelForSingleSheet(os, "仓库记录详情", title, listContent);
	}
	
	/**
	 * 导出下单清单
	 * @param startTime
	 * @param endTime
	 * @param os
	 */
	public void outPutExcelForCheck(String startTime, String endTime, OutputStream os){
		List<Check> listContent = checkService.queryCheckForList(new Check(), startTime, endTime);
		if(listContent == null || listContent.size() <= 0 ){
			throw new BusinessException("1", "导出数据为空");
		}
		
		LinkedHashMap title = Maps.newLinkedHashMap();
		title.put("batchNo", "批次号");
		title.put("trackingNumber", "网站至转运公司快递单号");
		title.put("orderAddrName", "下单网站");
		title.put("transferCompanyName", "转运公司");
		title.put("brandName", "品牌名称");
		title.put("seriesName", "系列名称");
		title.put("singleName", "单品名称");
		title.put("num", "下单数量");
		title.put("orderTime", "下单时间");
		title.put("payby", "付款人");
		title.put("unitPrice", "下单单价（美元单位）");
		title.put("sumPrice", "总价(美元单位)");
		title.put("remark", "备注");
		
		ExcelUtil.exportExcelForSingleSheet(os, "下单记录详情", title, listContent);
	}
	
	private static final transient Logger LOG = LoggerFactory.getLogger(ClearService.class);
}
