package com.cat.manage.base.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.base.dao.BrandDao;
import com.cat.manage.base.domain.Brand;
import com.cat.manage.base.domain.Series;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

/**
 * 品牌服务
 * @author wanghang
 *
 */
@Service
public class BrandService {
	@Autowired
	private BrandDao brandDao;
	
	@Autowired
	private SeriesService seriesService;
	
	@Autowired
	private SingleproductService singleService;
	
	/**
	 * 添加品牌
	 * @param brand
	 */
	public void addBrand(Brand brand){
		brandDao.addBrand(brand);
	}
	
	/**
	 * 修改品牌
	 * @param brand
	 */
	public void updateBrand(Brand brand){
		brandDao.updateBrand(brand);
	}
	
	/**
	 * 删除品牌
	 * @param brandId
	 */
	public void deleteBrand(Integer brandId){
		//删除品牌
		brandDao.deleteBrand(brandId);
		
		//查询系列
		List<Series> seriesList = seriesService.querySeriesByBrandId(brandId);
		if(seriesList == null || seriesList.size() <= 0){
			return;
		}
		
		//删除系列
		seriesService.deleteSeriesByBrandId(brandId);
		
		//记录系列编号
		List<Integer> seriesIds = Lists.newArrayList();
		for(Series series : seriesList){
			seriesIds.add(series.getSeriesId());
		}
		
		//删除单品
		singleService.deleteSingleproductBySeriesId((Integer[])seriesIds.toArray(new Integer[]{}));
		
	}
	
	/**
	 * 查询品牌
	 * @param brand
	 * @return
	 */
	public PageInfo<Brand> queryBrand(Brand brand, Integer pageNum, Integer pageSize){
		PageHelper.startPage(pageNum, pageSize);
		List<Brand> list = brandDao.queryBrands(brand);
		PageInfo<Brand> page = new PageInfo<Brand>(list);
		return page;
	}
	
	/**
	 * 根据品牌编号查询品牌
	 * @param brandId
	 * @return
	 */
	public Brand queryBrandById(Integer brandId){
		return brandDao.queryBrandById(brandId);
	}
}
