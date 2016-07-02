package com.cat.manage.base.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.base.dao.SingleproductDao;
import com.cat.manage.base.domain.Series;
import com.cat.manage.base.domain.Singleproduct;
import com.cat.manage.common.exception.BusinessException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;

/**
 * 单品服务
 * @author wanghang
 *
 */
@Service
public class SingleproductService {
	@Autowired
	private SingleproductDao singleproductDao;
	
	@Autowired
	private SeriesService seriesService;
	
	/**
	 * 添加单品信息
	 * @param singleproduct
	 */
	public boolean addSingleproduct(Singleproduct singleproduct){
		Series Series = seriesService.querySeriesById(Integer.valueOf(singleproduct.getOfOrigin()));
		if(Series == null)
			throw new BusinessException("1", "系列信息不存在");
		if(!"1".equals(Series.getIsUse()))
			throw new BusinessException("1", "系列已失效");
		
		//校验唯一性
		List<Singleproduct> list = singleproductDao.querySingleproductsAccurateForName(singleproduct);
		if(list != null && list.size() >= 1)
			return false;
		
		singleproductDao.addSingleproduct(singleproduct);
		return true;
	}
	
	/**
	 * 修改单品信息
	 * @param singleproduct
	 */
	public boolean updateSingleproduct(Singleproduct singleproduct){
		Series Series = seriesService.querySeriesById(Integer.valueOf(singleproduct.getOfOrigin()));
		if(Series == null)
			throw new BusinessException("1", "系列信息不存在");
		if(!"1".equals(Series.getIsUse()))
			throw new BusinessException("1", "系列已失效");
		
		//校验唯一性
		List<Singleproduct> list = singleproductDao.querySingleproductsAccurateForName(singleproduct);
		if(list != null && list.size() >= 1)
			return false;
		
		singleproductDao.updateSingleproduct(singleproduct);
		return true;
	}
	
	/**
	 * 删除单品信息
	 * @param singleproductId
	 */
	public void deleteSingleproduct(Integer singleId){
		singleproductDao.deleteSingleproduct(singleId);
	}
	
	/**
	 * 根据系列编号删除单品
	 * @param seriesIds
	 */
	public void deleteSingleproductBySeriesId(Integer[] seriesIds){
		singleproductDao.deleteSingleproductBySeriesIds(seriesIds);
	}
	
	/**
	 * 查询单品信息
	 * @param singleproduct
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Singleproduct> querySingleproduct(Singleproduct singleproduct, Integer pageNum, Integer pageSize, String brandId){
		PageHelper.startPage(pageNum, pageSize);
		List<Singleproduct> list = singleproductDao.querySingleproducts(singleproduct, brandId);
		PageInfo<Singleproduct> page = new PageInfo<Singleproduct>(list);
		return page;
	}
	
	/**
	 * 查询所有单品信息
	 * @param singleproduct
	 * @param brandId
	 * @return
	 */
	public List<Singleproduct> querySingleproductAll(Singleproduct singleproduct, String brandId){
		return singleproductDao.querySingleproducts(singleproduct, brandId);
	}
	
	/**
	 * 通过单品编号查询单品信息
	 * @param singleId
	 * @return
	 */
	public Singleproduct querySingleproductBySingleId(Integer singleId){
		return singleproductDao.querySingleproductBySingleId(singleId);
	}
}
