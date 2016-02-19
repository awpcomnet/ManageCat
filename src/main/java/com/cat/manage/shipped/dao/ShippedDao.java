package com.cat.manage.shipped.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cat.manage.check.domain.Check;
import com.cat.manage.shipped.domain.Shipped;
import com.cat.manage.shipped.domain.ShippedHead;

/**
 * 邮寄清单（子单）Dao
 * @author wanghang
 *
 */
@Repository
public interface ShippedDao {
	/**
	 * 添加邮寄清单（子单）
	 * @param shipped
	 */
	public void addShipped(ShippedHead shippedHead, Shipped shipped, Check check);
	
	/**
	 * 修改邮寄清单（子单）
	 * @param shipped
	 */
	public void updateShipped(Shipped shipped);
	
	/**
	 * 根据邮寄清单子单唯一编号删除记录
	 * @param id
	 */
	public void deleteShipped(Integer id);
	
	/**
	 * 根据下单清单唯一编号删除记录
	 * @param CheckId
	 */
	public void deleteShippedByCheckId(Integer checkId);
	
	/**
	 * 查询邮寄清单子单信息
	 * @param shipped
	 * @return
	 */
	public List<Shipped> queryShipped(Shipped shipped);
}
