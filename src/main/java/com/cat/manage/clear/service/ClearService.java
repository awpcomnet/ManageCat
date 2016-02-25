package com.cat.manage.clear.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.check.domain.Check;
import com.cat.manage.check.service.CheckService;
import com.cat.manage.clear.domain.MonthClear;
import com.cat.manage.selled.domain.Selled;
import com.cat.manage.selled.service.SelledService;
import com.cat.manage.shipped.domain.ShippedHead;
import com.cat.manage.shipped.service.ShippedHeadService;
import com.cat.manage.store.domain.Store;
import com.cat.manage.store.service.StoreService;
import com.google.common.collect.Lists;

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
		List<ShippedHead> shippedHeadList = shippedHeadService.queryShippedHeadForList(null, startTime, endTime);
		if(shippedHeadList != null){
			for(ShippedHead shippedHead : shippedHeadList){
				sumUnitPostage += shippedHead.getPostage();
			}
		}
		
		//查询入库后信息
		List<Store> storeList = storeService.queryStoreForList(null, startTime, endTime);
		if(storeList != null){
			for(Store store : storeList){
				sumUnitCost += Double.parseDouble(store.getUnitCost());
			}
		}
		
		//查询售出清单
		List<Selled> selledList = selledService.querySelledForTimeQuantum(null, startTime, endTime);
		if(selledList != null){
			for(Selled selled : selledList){
				sumSellPrice += selled.getSellingPrice();
				sumRefund += selled.getRefund();
			}
		}
		
		//计算总利润
		sumProfit = sumSellPrice - sumRefund - sumUnitCost;
		
		MonthClear monthClear = new MonthClear();
		monthClear.setSumProfit(sumProfit);
		monthClear.setSumSellPrice(sumSellPrice);
		monthClear.setSumUnitCost(sumUnitCost);
		monthClear.setSumUnitPostage(sumUnitPostage);
		monthClear.setSumUnitPrice(sumUnitPrice);
		monthClear.setSumRefund(sumRefund);
		monthClear.setStartTime(startTime);
		monthClear.setEndTime(endTime);
		
		List<MonthClear> clearList = Lists.newArrayList();
		clearList.add(monthClear);
		return clearList;
	}
	
}
