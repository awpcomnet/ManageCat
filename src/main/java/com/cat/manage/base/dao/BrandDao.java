package com.cat.manage.base.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cat.manage.base.domain.Brand;
/**
 * 品牌Dao
 * @author wanghang
 *
 */
@Repository
public interface BrandDao {
	/**
	 * 添加品牌
	 */
	public void addBrand(Brand brand);
	
	/**
	 * 修改品牌信息
	 * @param brand
	 */
	public void updateBrand(Brand brand);
	
	/**
	 * 根据品牌ID删除品牌
	 * @param brandId
	 */
	public void deleteBrand(Integer brandId);
	
	/**
	 * 查询品牌
	 * @return
	 */
	public List<Brand> queryBrands(Brand brand, String orderBy);
	
	/**
	 * 根据品牌编号查询品牌信息
	 * @param brandId
	 * @return
	 */
	public Brand queryBrandById(Integer brandId);
	
	/**
	 * 查询品牌（精确查询，不含模糊查询，不区分大小写）
	 * @param brand
	 * @return
	 */
	public List<Brand> queryBrandsAccurate(Brand brand);
}
