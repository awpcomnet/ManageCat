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
	 * 根据下单清单唯一编号修改邮寄清单内容
	 * @param shipped
	 */
	public void updateShippedByCheckId(Shipped shipped);
	
	/**
	 * 根据邮寄清单子单编号修改清单状态
	 * @param ids
	 * @param shippedStatus
	 */
	public void updateShippedForStatus(Integer[] ids, String shippedStatus);
	
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
	 * 根据邮寄清单主单唯一编号删除子单信息
	 * @param headId
	 */
	public void deleteShippedByHeadId(Integer headId);
	
	/**
	 * 根据邮寄清单子单唯一编号，批量删除记录
	 * @param ids
	 */
	public void deleteShippedByIds(Integer[] ids);
	
	/**
	 * 查询邮寄清单子单信息
	 * @param shipped
	 * @return
	 */
	public List<Shipped> queryShipped(Shipped shipped);
	
	/**
	 * 根据邮寄清单主单唯一编号查询子订单
	 * @param headId
	 * @return
	 */
	public List<Shipped> queryShippedByHeadId(Integer headId);
	
	/**
	 * 根据邮寄清单子单唯一编号查询子单信息
	 * @param ids
	 * @return
	 */
	public List<Shipped> queryShippedByIds(Integer[] ids);
	
	/**
	 * 根据邮寄清单子单唯一编号查询单条信息
	 * @param id
	 * @return
	 */
	public Shipped queryShippedById(Integer id);
	
	/**
	 * 根据邮寄单号查询与该物品相同品牌，系列的历史最近一条拥有重量的历史数据
	 * @param id
	 * @return
	 */
	public Shipped queryShippedWeightForPlan(Integer id);
	
	/**
	 * 用于同步的邮寄清单查询，仅查询 品牌 ，系列 ， 单品，唯一编号，下单唯一编号
	 * @param shipped
	 * @return
	 */
	public List<Shipped> queryShippedForSync(Shipped shipped);
	
	/**
	 * 根据下单清单唯一编号查询邮寄清单
	 * @param checkId
	 * @return
	 */
	public Shipped queryShippedByCheckId(Integer checkId);
	
}
