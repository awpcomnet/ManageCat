package com.cat.manage.order.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cat.manage.order.domain.SubOrder;

@Repository
public interface SubOrderDao {
	/**
	 * 添加订单
	 */
	public void addSubOrder(SubOrder subOrder);
	
	/**
	 * 根据ID查询子订单
	 * @param subOrderId
	 * @return
	 */
	public SubOrder querySubOrderById(Integer subOrderId);
	
	/**
	 * 查询所有子订单
	 * @return
	 */
	public List<SubOrder> querySubOrderAll(@Param("startTime") String startTime, @Param("endTime") String endTime);
	
	/**
	 * 修改子订单
	 * @param subOrder
	 */
	public Integer updateSubOrder(SubOrder subOrder);
	
	/**
	 * 复制子订单
	 * @num 产品数量
	 */
	public Integer addSubOrderForCopy(@Param("subOrderId") Integer subOrderId, @Param("num")  Integer num);
}
