package com.cat.manage.store.detail.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.manage.common.model.Srm;
import com.cat.manage.store.detail.domain.StoreDetail;
import com.cat.manage.store.detail.service.StoreDetailService;
import com.cat.manage.store.domain.Store;

/**
 * 仓库详情控制器
 * @author wanghang
 *
 */
@RestController
@RequestMapping("/storedetail")
public class StoreDetailController {
	@Autowired
	private StoreDetailService storeDetailService;
	
	@RequestMapping("/all")
	public Srm queryStoreDetailForAll(Store store){
		List<StoreDetail> list = storeDetailService.queryStoreDetail(store);
		return new Srm().setResultCode("0").setResultMessage("查询仓库详情成功").addAll(list);
	}
}
