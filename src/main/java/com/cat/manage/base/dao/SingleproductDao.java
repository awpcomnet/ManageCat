package com.cat.manage.base.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cat.manage.base.domain.Singleproduct;

/**
 * 单品Dao
 * @author wanghang
 *
 */
@Repository
public interface SingleproductDao {
	
	/**
	 * 添加单品
	 * @param singleproduct
	 */
	public void addSingleproduct(Singleproduct singleproduct);
	
	/**
	 * 修改单品信息
	 * @param singleproduct
	 */
	public void updateSingleproduct(Singleproduct singleproduct);
	
	/**
	 * 删除单品信息
	 * @param singleproductId
	 */
	public void deleteSingleproduct(Integer singleproductId);
	
	/**
	 * 查询单品信息
	 * @param singleproduct
	 * @return
	 */
	public List<Singleproduct> querySingleproducts(Singleproduct singleId);
}
