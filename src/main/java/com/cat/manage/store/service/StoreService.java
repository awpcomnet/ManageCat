package com.cat.manage.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.store.dao.StoreDao;
import com.cat.manage.store.domain.Store;

/**
 * 仓库服务类
 * @author wanghang
 *
 */
@Service
public class StoreService {

	@Autowired
	private StoreDao storeDao;
	
	/**
	 * 添加仓库信息
	 * store中必须存在shippedId邮寄清单子单唯一编号
	 * @param store 
	 */
	public void addStore(Store store){
		//验证邮寄清单子单是否存在
		
	}
}
