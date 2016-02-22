package com.cat.manage.store.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.manage.common.model.Srm;
import com.cat.manage.common.param.HttpParams;
import com.cat.manage.store.domain.Store;
import com.cat.manage.store.service.StoreService;
import com.github.pagehelper.PageInfo;

/**
 * @Description: 入库清单控制器
 * @author 王航
 * @date 2016年2月22日 下午4:15:25
 */
@RestController
@RequestMapping("/store")
public class StoreController {
	@Autowired
	private StoreService storeService;
	
	@RequestMapping("/query")
	public Srm queryStoreForPage(Store store, HttpServletRequest request){
		HttpParams params = HttpParams.buildFrom(request);
		Integer pageNum = params.getInt("page");
		Integer pageSize = params.getInt("limit");
		
		PageInfo<Store> page = storeService.queryStoreForPage(store, pageNum, pageSize);
		return new Srm().setResultCode("0").setResultMessage("查询入库清单成功").buildPageInfo(page);
	}
	
	@RequestMapping("/add")
	public Srm addStore(Store store){
		storeService.addStore(store);
		return new Srm().setResultCode("0").setResultMessage("添加入库清单成功");
	}
	
	@RequestMapping("/calculate")
	public Srm calculatePost(Integer shippedId){
		
		return new Srm().setResultCode("0").setResultMessage("计算邮费成功");
	}
}
