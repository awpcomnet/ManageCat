package com.cat.manage.shipped.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.check.domain.Check;
import com.cat.manage.shipped.dao.ShippedDao;
import com.cat.manage.shipped.domain.Shipped;
import com.cat.manage.shipped.domain.ShippedHead;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 邮寄清单（子单）服务
 * @author 王航
 * @date 2016年2月19日 下午4:45:38
 */
@Service
public class ShippedService {
	@Autowired
	private ShippedDao shippedDao;
	
	/**
	 * 添加邮寄清单（子单）
	 * @param shipped
	 */
	public void addShipped(ShippedHead shippedHead, Shipped shipped, Check check){
		shippedDao.addShipped(shippedHead, shipped, check);
	}
	
	/**
	 * 修改邮寄清单（子单）
	 * @param shipped
	 */
	public void updateShipped(Shipped shipped){
		shippedDao.updateShipped(shipped);
	}
	
	/**
	 * 根据邮寄清单唯一编号删除记录
	 * @param id
	 */
	public void deleteShipped(Integer id){
		shippedDao.deleteShipped(id);
	}
	
	/**
	 * 根据下单清单唯一编号删除记录
	 * @param checkId
	 */
	public void deleteShippedByCheckId(Integer checkId){
		shippedDao.deleteShippedByCheckId(checkId);
	}
	
	/**
	 * 查询邮寄清单（子单）记录
	 * @param shipped
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Shipped> queryShipped(Shipped shipped, Integer pageNum, Integer pageSize){
		PageHelper.startPage(pageNum, pageSize);
		List<Shipped> list = shippedDao.queryShipped(shipped);
		PageInfo<Shipped> page = new PageInfo<Shipped>(list);
		return page;
	}
}
