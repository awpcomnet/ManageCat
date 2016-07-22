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
	 * @param shippedHead
	 * @param flag 筛选标记， 1-筛选邮寄清单中子单未入库主单 2-筛选邮寄清单中子单全已入库主单    其他为全部查询
	 * @return
	 */
	public List<ShippedHead> queryShippedHead(ShippedHead shippedHead, String flag, String orderBy);
	
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
