package com.cat.manage.base.service;

import java.util.List;

import javax.print.attribute.standard.MediaSize.Other;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.base.dao.BrandDao;
import com.cat.manage.base.domain.Brand;
import com.cat.manage.base.domain.Series;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
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
	public boolean addBrand(Brand brand){
		//校验是否存在(英文名)
		Brand btemp = new Brand();
		btemp.setBrandEname(brand.getBrandEname());
		List<Brand> list = brandDao.queryBrandsAccurate(btemp);
		if(list != null && list.size() >= 1)
			return false;
		
		brandDao.addBrand(brand);
		return true;
	}
	
	/**
	 * 修改品牌
	 * @param brand
	 */
	public boolean updateBrand(Brand brand){
		//校验是否存在(英文名)
		Brand btemp = new Brand();
		btemp.setBrandEname(brand.getBrandEname());
		List<Brand> list = brandDao.queryBrandsAccurate(btemp);
		if(list != null && list.size() >= 1)
			return false;
		
		brandDao.updateBrand(brand);
		return true;
	}
	
	/**
	 * 删除品牌
	 * @param brandId
	 */
	public void deleteBrand(Integer brandId){
		//删除品牌
		brandDao.deleteBrand(brandId);
		
		//查询系列
//		List<Series> seriesList = seriesService.querySeriesByBrandId(brandId);
//		if(seriesList == null || seriesList.size() <= 0){
//			return;
//		}
		
		//删除系列 系列中将会操作单品信息的删除
		seriesService.deleteSeriesByBrandId(brandId);
		
		//记录系列编号
//		List<Integer> seriesIds = Lists.newArrayList();
//		for(Series series : seriesList){
//			seriesIds.add(series.getSeriesId());
//		}
		
		//删除单品
//		singleService.deleteSingleproductBySeriesId((Integer[])seriesIds.toArray(new Integer[]{}));
		
	}
	
	/**
	 * 查询品牌
	 * @param brand
	 * @return
	 */
	public PageInfo<Brand> queryBrand(Brand brand, Integer pageNum, Integer pageSize, String orderBy){
		PageHelper.startPage(pageNum, pageSize);
		List<Brand> list = brandDao.queryBrands(brand, Strings.isNullOrEmpty(orderBy) ? null : orderBy);
		PageInfo<Brand> page = new PageInfo<Brand>(list);
		return page;
	}
	
	/**
	 * 查询所有品牌
	 * @param brand
	 * @return
	 */
	public List<Brand> queryBrandAll(Brand brand, String orderBy){
		return brandDao.queryBrands(brand, orderBy);
	}
	
	/**
	 * 根据品牌编号查询品牌
	 * @param brandId
	 * @return
	 */
	public Brand queryBrandById(Integer brandId){
		return brandDao.queryBrandById(brandId);
	}
	
	/**
	 * 转换排序列
	 * @param orderBy
	 * @return
	 */
	public static String decodeForOrderBy(String orderBy){
		if(Strings.isNullOrEmpty(orderBy))
			return null;
		
		String result = null;
		switch(orderBy){
			case "1":
				result = "brand_ename";
				break;
		}
		return result;
	}
}
