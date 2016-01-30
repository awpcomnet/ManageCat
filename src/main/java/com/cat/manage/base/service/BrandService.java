package com.cat.manage.base.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.base.dao.BrandDao;
import com.cat.manage.base.domain.Brand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 品牌服务
 * @author wanghang
 *
 */
@Service
public class BrandService {
	@Autowired
	private BrandDao brandDao;
	
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
		brandDao.deleteBrand(brandId);
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
