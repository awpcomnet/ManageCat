package com.cat.manage.store.detail.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.store.detail.dao.StoreDetailDao;
import com.cat.manage.store.detail.domain.StoreDetail;
import com.cat.manage.store.domain.Store;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * 仓库详情服务类
 * @author wanghang
 *
 */
@Service
public class StoreDetailService {
	
	@Autowired
	private StoreDetailDao storeDetailDao;
	
	public List<StoreDetail> queryStoreDetail(Store store){
		List<StoreDetail> resultList = Lists.newArrayList();
		
		List<StoreDetail> list = storeDetailDao.queryStoreByPayBy(store);
		if(list == null || list.size() <= 0)
			return new ArrayList<StoreDetail>();
		
		Double sumUnitPostage = 0.0;
		Double sumStoreCost = 0.0;
		Integer sumNum = 0;
		Double sumUnitRmb = 0.0;
		int id = 1;
		for(StoreDetail storeDetail : list){
			double unitRmb = Double.parseDouble(Strings.isNullOrEmpty(storeDetail.getSumUnitRmb()) ? "0.0" : storeDetail.getSumUnitRmb());
			double unitPostage = Double.parseDouble(Strings.isNullOrEmpty(storeDetail.getSumUnitPostage()) ? "0.0" : storeDetail.getSumUnitPostage());
			double storeCost = Double.parseDouble(Strings.isNullOrEmpty(storeDetail.getSumStoreCost()) ? "0.0" : storeDetail.getSumStoreCost());
			int num = Integer.parseInt(Strings.isNullOrEmpty(storeDetail.getResidueNum()) ? "0" : storeDetail.getResidueNum());
			
			sumUnitPostage += unitPostage;
			sumStoreCost += storeCost;
			sumNum += num;
			sumUnitRmb += unitRmb;
			
			storeDetail.setId(id);
			storeDetail.setSumUnitPostage(String.format("%.02f", unitPostage));
			storeDetail.setSumUnitRmb(String.format("%.02f", unitRmb));
			resultList.add(storeDetail);
			id++;
		}
		
		StoreDetail lastStoreDetail = new StoreDetail();
		lastStoreDetail.setPayBy("金额汇总=>");
		lastStoreDetail.setSumUnitPostage(String.format("%.02f", sumUnitPostage)); 
		lastStoreDetail.setSumStoreCost(String.format("%.02f", sumStoreCost));
		lastStoreDetail.setResidueNum(String.format("%d", sumNum));
		lastStoreDetail.setSumUnitRmb(String.format("%.02f", sumUnitRmb));
		lastStoreDetail.setId(id);
		resultList.add(lastStoreDetail);
		return resultList;
	}
	
}
