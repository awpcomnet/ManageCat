package com.cat.manage.selled.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cat.manage.selled.domain.Selled;
import com.cat.manage.store.domain.Store;

/**
 * @Description: 售出清单Dao
 * @author 王航
 * @date 2016年2月23日 上午10:41:30
 */
@Repository
public interface SelledDao {
	/**
	 * 添加售出记录
	 * @param selled
	 * @param shipped
	 */
	public void addSelled(Selled selled, Store store);
	
	/**
	 * 修改售出记录
	 * @param selled
	 */
	public void updateSelled(Selled selled);
	
	/**
	 * 根据售出清单唯一编号删除记录
	 * @param id
	 */
	public void deleteSelledById(Integer id);
	
	/**
	 * 根据售出清单的下单清单唯一编号删除记录
	 * @param checkId
	 */
	public void deleteSelledByCheckId(Integer checkId);
	
	/**
	 * 查询售出清单记录
	 * @param selled
	 * @return
	 */
	public List<Selled> querySelled(Selled selled);
	
	/**
	 * 根据售出清单唯一编号（多个）查询记录
	 * @param ids
	 * @return
	 */
	public List<Selled> querySelledByIds(Integer[] ids);
	
	/**
	 * 根据售出清单唯一编号查询记录
	 * @param id
	 * @return
	 */
	public Selled querySelledById(Integer id);
	
	/**
	 * 根据下单清单唯一编号查询记录
	 * @param checkId
	 * @return
	 */
	public Selled querySelledByCheckId(Integer checkId);
	
	/**
	 * 根据邮寄清单唯一编号查询记录
	 * @param shippedId
	 * @return
	 */
	public Selled querySelledByShippedId(Integer shippedId);
}
