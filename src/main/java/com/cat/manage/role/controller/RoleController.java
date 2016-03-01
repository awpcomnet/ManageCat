package com.cat.manage.role.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.manage.common.exception.ParameterException;
import com.cat.manage.common.model.Srm;
import com.cat.manage.common.param.HttpParams;
import com.cat.manage.role.domain.Role;
import com.cat.manage.role.service.RoleService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;

/**
 * 
 * @Description: 角色控制器
 * @author 王航
 * @date 2016年3月1日 上午11:16:00
 */
@RestController
@RequestMapping("/role")
public class RoleController {
	 
    private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/findAll")
	public Srm findAllUser() {
		return new Srm().setResultCode("0").setResultMessage("查询成功!").addAll(roleService.findAll());
	}
	
	@RequestMapping("/page")
	public Srm onGetPage(HttpServletRequest request) {
	    int pageNumber = getPageNumber(request);
	    int pageSize = getPageSize(request);
	    
	    PageInfo<Role> rolePage = roleService.getRolePage(pageNumber, pageSize);
 	    return new Srm().setResultCode("0").setResultMessage("查询分页信息成功").buildPageInfo(rolePage);
	}
	
	@RequestMapping("/add")
	public Srm onAddRole(HttpServletRequest request) {
	    HttpParams params = HttpParams.buildFrom(request);
	    
	    String name = params.getStr("name");
	    String code = params.getStr("code");
	    
	    Role role = new Role();
	    role.setName(name);
	    role.setCode(code);
	    
	    roleService.addRole(role);
	    return new Srm().setResultCode("0").setResultMessage("新增角色信息成功");
	}
	
	@RequestMapping("/update")
	public Srm onUpdateRole(HttpServletRequest request) {
	    HttpParams params = HttpParams.buildFrom(request);
	    
	    Integer id = params.getInt("id");
	    String name = params.getStr("name");
	    String code = params.getStr("code");
	    
	    Role role = new Role();
	    role.setId(id);
	    role.setName(name);
	    role.setCode(code);
	    
	    roleService.updateRole(role);
	    return new Srm().setResultCode("0").setResultMessage("修改用户数据成功");
	}
	
	@RequestMapping("/delete")
	public Srm onDeleteRole(HttpServletRequest request) {
	    HttpParams params = HttpParams.buildFrom(request);
	    String idsLiteral = params.getStr("ids");
	    
	    String[] idsStrArray = idsLiteral.split(",");
	    Integer[] idsIntArray = new Integer[idsStrArray.length];
	    
	    for (int i = 0; i < idsStrArray.length; i++) {
	        String id = idsStrArray[i];
	        idsIntArray[i] = Integer.parseInt(id);
	    }
	    
	    roleService.deleteRole(idsIntArray);
	    return new Srm().setResultCode("0").setResultMessage("删除角色成功");
	}
	
	private static final String PAGE_NUMBER_KEY = "page";
	private static final int PAGE_NUMBER_DEFAULT = 1;
	private int getPageNumber(HttpServletRequest request) {
	    String pageNumberLiteral = request.getParameter(PAGE_NUMBER_KEY);
	    int pageNumber = PAGE_NUMBER_DEFAULT;
	    try {
	        pageNumber = Integer.parseInt(pageNumberLiteral);
	    } catch (Exception e) {
	        pageNumber = PAGE_NUMBER_DEFAULT;
	    }
	    return pageNumber;
	}

	private static final String PAGE_SIZE_KEY = "limit";
	private static final int PAGE_SIZE_DEFAULT = 25;
	private int getPageSize(HttpServletRequest request) {
	    String pageSizeLiteral = request.getParameter(PAGE_SIZE_KEY);
	    int pageSize = PAGE_SIZE_DEFAULT;
	    try {
	        pageSize = Integer.parseInt(pageSizeLiteral);
	    } catch (Exception e) {
	        pageSize = PAGE_SIZE_DEFAULT;
	    }
	    return pageSize;
	}

	@RequestMapping("/updatepermission")
	public Srm onUpdateRolePermission(Integer roleId, String permissionString) {
	    if (roleId == null)
	        throw new ParameterException("1", "角色Id不能为空");
	    
	    if (Strings.isNullOrEmpty(permissionString))
	        throw new ParameterException("1", "权限分配字符串不能为空");
	    
	    List<Map<String, Integer>> rolePermission = null;
	    try {
	        rolePermission = mapper.readValue(permissionString, new TypeReference<List<Map<String, Integer>>>() {});
        } catch (Exception e) {
            throw new ParameterException("1", "权限分配字符串不为JSON字符串");
        }
	    
	    if (rolePermission == null)
	        throw new ParameterException("1", "权限分配字符串内容为空");
	    
	    roleService.updateRolePermission(roleId, rolePermission);
	    return new Srm().setResultCode("0").setResultMessage("更新角色权限数据成功");
	}
}
