package com.cat.manage.permission.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.manage.common.exception.ParameterException;
import com.cat.manage.common.model.Srm;
import com.cat.manage.common.param.HttpParams;
import com.cat.manage.permission.domain.Permission;
import com.cat.manage.permission.service.PermissionService;
import com.cat.manage.role.service.RoleService;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;

/**
 * 
 * @Description: 权限控制器
 * @author 王航
 * @date 2016年3月1日 下午2:37:52
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {
	private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int DEFAULT_PAGE_SIZE   = 25;
    
    @Autowired
    private PermissionService permissionService;
    
    @Autowired
    private RoleService roleService;
    
    /**
     * 条件查询分页数据
     */
    @RequestMapping("/page")
    public Srm onGetPage(HttpServletRequest request) {
        HttpParams params = HttpParams.buildFrom(request);
        Map<String, String> reqMap = params.getMap();
        
        String catalogIdLiteral = reqMap.get("catalogId");
        Integer catalogId = null;
        try {
            catalogId = Integer.parseInt(catalogIdLiteral);
        } catch (Exception e) {
            catalogId = null;
        }
        
        String name = reqMap.get("name");
        String code = reqMap.get("code");
        String url  = reqMap.get("url");
        
        String pageNumberLiteral = reqMap.get("page");
        int pageNumber = DEFAULT_PAGE_NUMBER;
        try {
            pageNumber = Integer.parseInt(pageNumberLiteral);
        } catch (Exception e) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
        
        String pageSizeLiteral = reqMap.get("limit");
        int pageSize = DEFAULT_PAGE_SIZE;
        try {
            pageSize = Integer.parseInt(pageSizeLiteral);
        } catch (Exception e) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        
        Permission permission = new Permission();
        permission.setName(name);
        permission.setCode(code);
        permission.setUrl(url);
        permission.setCatalogId(catalogId);
        
        PageInfo<Permission> page = permissionService.getPermissionPageWithCondition(pageNumber, pageSize, permission);
        
        return new Srm().setResultCode("0").setResultMessage("查询分页成功").buildPageInfo(page);
    }
    
    @RequestMapping("/add")
    public Srm onAddPermission(HttpServletRequest request) {
        HttpParams params = HttpParams.buildFrom(request);
        
        String name = params.getStr("name");
        String code = params.getStr("code");
        String url  = params.getStr("url");
        Integer catalogId = params.getInt("catalogId");
        
        Permission permission = new Permission();
        permission.setCatalogId(catalogId);
        permission.setName(name);
        permission.setCode(code);
        permission.setUrl(url);
        
        permissionService.addPermission(permission);
        return new Srm().setResultCode("0").setResultMessage("新增权限成功");
    }
    
    
    @RequestMapping("/update")
    public Srm onUpdatePermission(HttpServletRequest request) {
        HttpParams params = HttpParams.buildFrom(request);
        
        Integer id  = params.getInt("id");
        String name = params.getStr("name");
        String code = params.getStr("code");
        String url  = params.getStr("url");
        
        Permission permission = new Permission();
        permission.setId(id);
        permission.setName(name);
        permission.setCode(code);
        permission.setUrl(url);
        
        permissionService.updatePermission(permission);

        return new Srm().setResultCode("0").setResultMessage("修改权限数据成功");
    }
    
    @RequestMapping("/delete")
    public Srm onDeletePermission(String ids) {
        if (Strings.isNullOrEmpty(ids)) {
            throw new ParameterException("1", "删除记录Id不能为空");
        }
        
        String[] idArray = ids.split(",");
        Integer[] idIntArray = new Integer[idArray.length];
        for (int i = 0; i < idArray.length; i++) {
            idIntArray[i] = Integer.valueOf(idArray[i]);
        }
        permissionService.deletePermission(idIntArray);
        
        return new Srm().setResultCode("0").setResultMessage("删除记录成功");
    }
    
    
    @RequestMapping("/tree")
    public Srm onGetPermissionTree(Integer roleId) {
        if (roleId == null) {
            throw new ParameterException("1", "角色ID不能为空");
        }
        
        List<Map<String, Object>> rolePermission = roleService.getRolePermission(roleId);
        
        Permission root = permissionService.readPermissionTree();
        Map<String, Object> rootMap = new HashMap<String, Object>();
        trasnformPermissionTree(root, rootMap, rolePermission);
        
        return new Srm().setResultCode("0").setResultMessage("查询权限树成功").addAll(((Collection<?>)rootMap.get("results")));
    }
    
    private void trasnformPermissionTree(Permission permission, Map<String, Object> map, List<Map<String, Object>> rolePermission) {
        if (permission == null || map == null) {
            return;
        }
        
        Integer id = permission.getId();
        String name = permission.getName();
        String code = permission.getCode();
        String url  = permission.getUrl();
        Integer catalogId = permission.getCatalogId();
        List<Permission> pl = permission.getSubPermissions();
        
        map.put("id", id);
        map.put("text", name);
        map.put("code", code);
        map.put("url", url);
        map.put("catalogId", catalogId);
        
        if (hasPermission(rolePermission, id, "0")) {
            map.put("checked", true);
        } else {
            map.put("checked", false);
        }
        
        if (pl == null || pl.size() == 0) {
            map.put("leaf", true);
            
        } else {
            map.put("expanded", true);
            List<Map<String, Object>> subP = new ArrayList<Map<String, Object>>();
            for (Permission p : pl) {
                Map<String, Object> newMap = new HashMap<String, Object>();
                trasnformPermissionTree(p, newMap, rolePermission);
                
                subP.add(newMap);
            }
            map.put("results", subP);
        }
    }
    
    private boolean hasPermission(List<Map<String, Object>> rolePermission, Integer permissionId, String permissionType) {
        if (rolePermission == null || rolePermission.size() == 0) {
            return false;
        }
        
        for (Iterator<Map<String, Object>> it = rolePermission.iterator(); it.hasNext();) {
            Map<String, Object> per = it.next();
            Integer perId  = ((BigDecimal)per.get("PERMISSIONID")).intValue();
            String perType = (String)per.get("PERMISSIONTYPE");
            
            if (perId.equals(permissionId) && perType.equals(permissionType)) {
                return true;
            }
        }
        
        return false;
    }
}
