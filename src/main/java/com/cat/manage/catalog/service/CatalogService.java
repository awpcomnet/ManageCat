package com.cat.manage.catalog.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.catalog.dao.CatalogDao;
import com.cat.manage.catalog.domain.Catalog;

/**
 * 描述: 系统栏目业务对象
 * 作者: 
 */
@Service
public class CatalogService {
	
	@Autowired
	private CatalogDao catalogDao;
	
	/** 根栏目ID */
	private static final int ROOT_CATALOG_ID = 0;
	
	/**
	 * 获得某栏目的信息
	 */
	public Catalog queryCatalogbyId(int id) {
		return catalogDao.queryCatalog(id);
	}
	
	/**
	 * 获得完整系统栏目树
	 */
	public Catalog queryCatalogTree() {
		return queryCatalog(ROOT_CATALOG_ID, true);
	}
	
	/**
	 * 获得某栏目及其子栏目信息
	 */
	public Catalog queryCatalog(int id, boolean isFillSubcatalog) {
		Catalog catalog = catalogDao.queryCatalog(id);
		if (catalog != null)
			fillSubCatalog(catalog, isFillSubcatalog);
		
		return catalog;
	}
	
	/**
	 * 递归填充子栏目信息
	 */
	private void fillSubCatalog(Catalog catalog, boolean isRecursion) {
		if (catalog == null)
			return;
		
		List<Catalog> subCatalogs = catalogDao.querySubCatalogs(catalog.getId());
		if (subCatalogs == null || subCatalogs.isEmpty())
			return;
		
		if (isRecursion) {
			for (Iterator<Catalog> it = subCatalogs.iterator(); it.hasNext();) {
				Catalog sc = it.next();
				fillSubCatalog(sc, isRecursion);
			}
		}
		
		catalog.setSubCatalogs(subCatalogs);
	}
	
	/**
	 * 查找某个栏目的所有子栏目
	 * TODO: 需要优化
	 */
	public List<Catalog> findSubCatalogs(int parentId) {
		List<Catalog> catalogs = catalogDao.querySubCatalogs(parentId);
		
		for (int i = 0, len = catalogs.size(); i < len; i++) {
			Catalog catalog = catalogs.get(i);
			int id = catalog.getId();
			
			List<Catalog> catas = catalogDao.querySubCatalogs(id);
			if (catas == null || catas.size() == 0) {
				catalog.setLeaf(true);
			} else {
				catalog.setLeaf(false);
			}
		}
		
		return catalogs;
	}
	
	/**
	 * 查找某个栏目的所有子栏目（模糊查询）
	 */
	public List<Catalog> findSubCatalogsForFuzzy(Catalog catalog) {
		List<Catalog> catalogs = catalogDao.querySubCatalogsForFuzzy(catalog);
		
		for (int i = 0, len = catalogs.size(); i < len; i++) {
			Catalog cl = catalogs.get(i);
			int id = cl.getId();
			
			List<Catalog> catas = catalogDao.querySubCatalogs(id);
			if (catas == null || catas.size() == 0) {
				cl.setLeaf(true);
			} else {
				cl.setLeaf(false);
			}
		}
		return catalogs;
	}
	
	/**
	 * 添加一个栏目
	 */
	public void addCatalog(Catalog catalog) {
		catalogDao.addCatalog(catalog);
	}
	
	/**
	 * 删除一个栏目
	 */
	public void deleteCatalog(int id) {
		catalogDao.delCatalogById(id);
	}
	
	/**
	 * 删除多个栏目
	 */
	public void deleteCatalog(Integer[] id){
		catalogDao.delCatalogs(id);
		catalogDao.delCatalogsByParentId(id);
	}
	
	/**
	 * 修改一个栏目
	 */
	public void updateCatalog(Catalog catalog){
		catalogDao.updateCatalog(catalog);
	}
}
