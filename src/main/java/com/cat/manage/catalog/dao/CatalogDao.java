package com.cat.manage.catalog.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cat.manage.catalog.domain.Catalog;


/**
 * 描述: 栏目数据访问对象 
 * 作者: 
 */
@Repository
public interface CatalogDao {

	/**
	 * 查询一个栏目信息
	 */
	Catalog queryCatalog(int id);
	
	/**
	 * 增加一个栏目
	 */
	void addCatalog(Catalog catalog);
	
	/**
	 * 删除一个栏目
	 */
	void delCatalogById(int id);
	
	/**
	 * 删除多个栏目
	 */
	void delCatalogs(Integer[] id);
	
	/**
	 * 删除多个栏目(根据parentId)
	 */
	void delCatalogsByParentId(Integer[] id);
	
	
	/**
	 * 更新一个栏目
	 */
	void updateCatalog(Catalog catalog);
	
	/**
	 * 查询某个栏目下子栏目列表
	 */
	List<Catalog> querySubCatalogs(int parentId);
	
	/**
	 * 查询某个栏目下子栏目列表（模糊多条件查询）
	 */
	List<Catalog> querySubCatalogsForFuzzy(Catalog catalog);
}
