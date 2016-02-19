package com.cat.manage.check.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cat.manage.check.domain.Check;

/**
 * 下单清单dao
 * @author wanghang
 *
 */
@Repository
public interface CheckDao {
	/**
	 * 添加一条下单订单
	 * @param check
	 */
	public void addCheck(Check check);
	
	/**
	 * 修改下单订单
	 * @param check
	 */
	public void updateCheck(Check check);
	
	/**
	 * 根据下单清单唯一编号修改订单记录状态
	 * @param ids
	 */
	public void updateCheckForStatus(Integer[] ids, String orderStatus);
	
	/**
	 * 删除一条下单订单
	 * @param id
	 */
	public void deleteCheck(Integer id);
	
	/**
	 * 查询下单清单
	 * @param check
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Check> queryCheck(Check check, String startTime, String endTime);
	
	/**
	 * 查询下单清单（除去状态）
	 * @param check
	 * @param startTime
	 * @param endTime
	 * @param status
	 * @return
	 */
	//public List<Check> queryCheckExclusiveStatus(Check check, String startTime, String endTime, String status);
	
	/**
	 * 根据下单清单唯一编号查询记录
	 * @param ids
	 * @return
	 */
	public List<Check> queryCheckById(Integer[] ids);
}