package com.cat.manage.catalog.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.manage.catalog.domain.Catalog;
import com.cat.manage.catalog.service.CatalogService;
import com.cat.manage.common.exception.ParameterException;
import com.cat.manage.common.model.Srm;
import com.cat.manage.shiro.user.CurrentUser;
import com.cat.manage.user.domain.User;
import com.cat.manage.user.service.UserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 描述: 栏目管理资源请求路径处理
 * 作者: 
 */
@RestController
@RequestMapping("/catalog")
public class CatalogController {
	
	@Autowired
	private CatalogService catalogService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 查询所有栏目, 以栏目树的形式返回
	 */
	@RequestMapping("/queryAllActive")
	public Catalog getCatalogTree() {
		Catalog catalogTree = catalogService.queryCatalogTree();
		return catalogTree;
	}
	
	/**
	 * 查询单个栏目信息
	 */
	@RequestMapping("/queryById")
	public Srm findCatalogById(int id) {
		return new Srm()
				.setResultCode("0")
				.setResultMessage("查询栏目成功")
				.addResult(catalogService.queryCatalogbyId(id));
	}

	/**
	 * 增加一个栏目
	 */
	@RequestMapping("/add")
	public Srm addCatalog(Catalog catalog) {
		catalogService.addCatalog(catalog);
		return new Srm()
			.setResultCode("0")
			.setResultMessage("添加栏目成功");	
	}
	
	/**
	 * 删除一个栏目
	 */
	@RequestMapping("/delete")
	public Srm deleteCatalog(int id) {
		catalogService.deleteCatalog(id);
		return new Srm()
			.setResultCode("0")
			.setResultMessage("删除栏目成功!");
	}
	
	/**
	 * 删除多个栏目
	 */
	@RequestMapping("/deletes")
	public Srm deleteCatalogs(Integer[] id){
		catalogService.deleteCatalog(id);
		return new Srm().setResultCode("0").setResultMessage("删除栏目成功!");
	}
	
	/**
	 * 更新一个栏目的数据
	 */
	@RequestMapping("/modify")
	public Srm updateCatalog(Catalog catalog) {
		catalogService.updateCatalog(catalog);
		return new Srm().setResultCode("0").setResultMessage("编辑栏目成功");
	}
	
	/**
	 * 查找所有的一级栏目
	 */
	@RequestMapping("/classOne")
	public Catalog findClassOneCatalog(int id) {
		return catalogService.queryCatalog(id, false);
	}
	
	/**
	 * 查找某个栏目的子栏目
	 */
	@RequestMapping("/subCatalogs")
	public Srm findSubCatalogs(Catalog cl) {
		List<Catalog> catalogs = catalogService.findSubCatalogsForFuzzy(cl);
		List<Map<String, Object>> catalogList = Lists.newArrayList();
		
		if (catalogs != null && catalogs.size() > 0) {
			for (int i = 0, len = catalogs.size(); i < len; i++) {
				Catalog catalog = catalogs.get(i);
				Map<String, Object> cata = Maps.newHashMap();
				
				cata.put("id", catalog.getId());
				cata.put("parentId", catalog.getParentId());
				cata.put("text", catalog.getName());
				cata.put("iconCls", catalog.getIcon());
				cata.put("urlType", catalog.getUrlType());
				cata.put("url", catalog.getUrl());
				cata.put("abbr", catalog.getAbbr());
				cata.put("description", catalog.getDescription());
				cata.put("state", catalog.getState());
				cata.put("orderNum", catalog.getOrder());
				
				if (catalog.isLeaf()) {
					cata.put("leaf", true);
				} else {
					cata.put("expanded", false);
				}
				
				catalogList.add(cata);
			}
		}
		
		return new Srm().setResultCode("0").setResultMessage("查询成功!").addAll(catalogList);
	}
	
	@RequestMapping("/queryUserCatalog")
    public Srm getCatalogTree(Catalog cl, @CurrentUser User user) {
        List<Catalog> catalogs = catalogService.findSubCatalogsForFuzzy(cl);
        List<Map<String, Object>> catalogList = Lists.newArrayList();

        Integer userId = user.getUserId();
        String username = user.getUsername();
        if (userId == null) {
            throw new ParameterException("3", "请先登录");
        }
        Set<Integer> catalogIds = userService.getUserCatalog(userId);
        
        if (catalogs != null && catalogs.size() > 0) {
            for (int i = 0, len = catalogs.size(); i < len; i++) {
                Catalog catalog = catalogs.get(i);
                Map<String, Object> cata = Maps.newHashMap();

                cata.put("id", catalog.getId());
                cata.put("parentId", catalog.getParentId());
                cata.put("text", catalog.getName());
                cata.put("iconCls", catalog.getIcon());
                cata.put("urlType", catalog.getUrlType());
                cata.put("url", catalog.getUrl());
                cata.put("abbr", catalog.getAbbr());
                cata.put("description", catalog.getDescription());
                cata.put("state", catalog.getState());
                cata.put("orderNum", catalog.getOrder());

                if (catalog.isLeaf()) {
                    cata.put("leaf", true);
                } else {
                    cata.put("expanded", false);
                }

                if(catalog.getState().equals("false"))
                	continue;
                
                if ((catalogIds != null && catalogIds.contains(catalog.getId())) || "admin".equals(username)) {
                    catalogList.add(cata);
                }
            }
        }
        
        return new Srm().setResultCode("0").setResultMessage("查询成功!").addAll(catalogList);
    }
	
	private static final Logger LOG = LoggerFactory.getLogger(CatalogController.class);
}
