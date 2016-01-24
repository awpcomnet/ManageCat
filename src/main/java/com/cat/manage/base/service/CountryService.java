package com.cat.manage.base.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.base.dao.CountryDao;
import com.cat.manage.base.domain.Country;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 国家信息服务
 * @author wanghang
 *
 */
@Service
public class CountryService {
	@Autowired
	private CountryDao countryDao;
	
	/**
	 * 添加国家信息
	 * @param country
	 */
	public void addCountry(Country country){
		countryDao.addCountry(country);
	}
	
	/**
	 * 修改国家信息
	 * @param country
	 */
	public void updateCountry(Country country){
		countryDao.updateCountry(country);
	}
	
	/**
	 * 删除国家信息
	 * @param countryId
	 */
	public void deleteCountry(Integer countryId){
		countryDao.deleteCountry(countryId);
	}
	
	/**
	 * 查询国家信息
	 * @param country
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Country> queryCountry(Country country, Integer pageNum, Integer pageSize){
		PageHelper.startPage(pageNum, pageSize);
		List<Country> list = countryDao.queryCountrys(country);
		PageInfo<Country> page = new PageInfo<Country>(list);
		return page;
	}
}
