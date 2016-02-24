package com.cat.manage.shipped.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cat.manage.shipped.domain.ShippedHead;

/**
 * 邮寄清单Dao（主单）
 * @author wanghang
 *
 */
@Repository
public interface ShippedHeadDao {
	/**
	 * 添加邮寄清单（主单）
	 * @param shippedHead
	 */
	public void addShippedHead(ShippedHead shippedHead);
	
	/**
	 * 修改邮寄清单（主单）
	 * @param shippedHead
	 */
	public void updateShippedHead(ShippedHead shippedHead);
	
	/**
	 * 根据邮寄清单（主单）唯一编号删除记录
	 * @param id
	 */
	public void deleteShippedHead(Integer id);
	
	/**
	 * 查询邮寄清单（主单）记录
	 * @return
	 */
	public List<ShippedHead> queryShippedHead(ShippedHead shippedHead, String flag);
	
	/**
	 * 根据快递单号查询邮寄清单主单
	 * @param trackingNumber
	 * @return
	 */
	public ShippedHead queryShippedHeadByTrackingNumber(String trackingNumber);
	
	/**
	 * 根据邮寄清单主单唯一编号查询记录
	 * @param id
	 * @return
	 */
	public ShippedHead queryShippedHeadById(Integer id);
	
	/**
	 * 查询邮寄清单主单时间段信息
	 * @param shippedHead
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<ShippedHead> queryShippedHeadForList(ShippedHead shippedHead, String startTime, String endTime);
	
	/**
	 * 根据下单清单唯一编号（多个），查询邮寄清单主单
	 * @param checkIds
	 * @return
	 */
	public List<ShippedHead> queryShippedHeadByCheckIds(Integer[] checkIds);
}
