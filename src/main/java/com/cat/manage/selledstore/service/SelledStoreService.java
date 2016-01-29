package com.cat.manage.selledstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.order.domain.SubOrder;
import com.cat.manage.selledstore.dao.SelledStoreDao;

/**
 * 售出信息服务
 * @author wanghang
 *
 */
@Service
public class SelledStoreService {

	@Autowired
	private SelledStoreDao selledStoreDao;
	
	/**
	 * 添加售出信息
	 */
	public void addSelledStore(SubOrder subOrder){
		selledStoreDao.addSelledStore(subOrder);
	}
}
