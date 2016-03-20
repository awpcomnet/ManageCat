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
	public void addSingleproduct(Singleproduct singleproduct){
		Series Series = seriesService.querySeriesById(Integer.valueOf(singleproduct.getOfOrigin()));
		if(Series == null)
			throw new BusinessException("1", "系列信息不存在");
		if(!"1".equals(Series.getIsUse()))
			throw new BusinessException("1", "系列已失效");
		
		singleproductDao.addSingleproduct(singleproduct);
	}
	
	/**
	 * 修改单品信息
	 * @param singleproduct
	 */
	public void updateSingleproduct(Singleproduct singleproduct){
		singleproductDao.updateSingleproduct(singleproduct);
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
	public PageInfo<Singleproduct> querySingleproduct(Singleproduct singleproduct, Integer pageNum, Integer pageSize){
		PageHelper.startPage(pageNum, pageSize);
		List<Singleproduct> list = singleproductDao.querySingleproducts(singleproduct);
		PageInfo<Singleproduct> page = new PageInfo<Singleproduct>(list);
		return page;
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
