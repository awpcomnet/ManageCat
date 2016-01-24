package com.cat.manage.base.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cat.manage.base.domain.Country;

/**
 * 国家信息Dao
 * @author wanghang
 *
 */
@Repository
public interface CountryDao {
	
	/**
	 * 添加国家信息
	 * @param country
	 */
	public void addCountry(Country country);
	
	/**
	 * 修改国家信息
	 * @param country
	 */
	public void updateCountry(Country country);
	
	/**
	 * 根据国家编号删除国家信息
	 * @param countryId
	 */
	public void deleteCountry(Integer countryId);
	
	/**
	 * 查询国家信息
	 * @param country
	 * @return
	 */
	public List<Country> queryCountrys(Country country);
}
