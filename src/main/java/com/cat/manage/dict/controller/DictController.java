package com.cat.manage.dict.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cat.manage.common.exception.ParameterException;
import com.cat.manage.common.model.Srm;
import com.cat.manage.dict.domain.Dict;
import com.cat.manage.dict.service.DictService;
import com.google.common.base.Strings;

/**
 * @Description: 字典服务http接口
 * @author 
 * @date 2015年11月17日 下午3:17:36
 */
@RestController
@RequestMapping("/dict")
public class DictController {
	
	@Autowired
	private DictService dictService;
	
	@RequestMapping("/type")
	public Srm getDictsByType(@RequestParam("type") String type) {
		if (Strings.isNullOrEmpty(type))
			throw new ParameterException("1", "参数 [type] 不能为空!");
		
		return new Srm().setResultCode("0").setResultMessage("查询字典成功").addAll(dictService.getDictsByType(type));	
	}
	
	@RequestMapping("/add")
	public Srm onAddDict(Dict dict) {
		if (Strings.isNullOrEmpty(dict.getName()))
			throw new ParameterException("1", "字典名称 [name] 不能为空");
		
		if (Strings.isNullOrEmpty(dict.getCode()))
			throw new ParameterException("1", "字典代码 [code] 不能为空");
		
		dictService.addDict(dict);
		return new Srm().setResultCode("0").setResultMessage("增加字典成功");
	}
	
	@RequestMapping("/modify")
	public Srm onModifyDict(Dict dict) {
		if (dict == null)
			throw new ParameterException("1", "参数 字典 不能为空");
		if (Strings.isNullOrEmpty(dict.getName()))
			throw new ParameterException("1", "字典名称[name]不能为空");

		if (Strings.isNullOrEmpty(dict.getCode()))
			throw new ParameterException("1", "字典名称[code]不能为空");

		if (Strings.isNullOrEmpty(dict.getName()))
			throw new ParameterException("1", "字典名称[typeId]不能为空");
		
		dictService.updateDict(dict);
		return new Srm().setResultCode("0").setResultMessage("修改字典成功");
	}
	
	@RequestMapping("/delete")
	public Srm onDeleteDict(Integer[] id) {
		if (id == null)
			throw new ParameterException("1", "参数 字典项[id] 不能为空");
		if (id.length <= 0)
			throw new ParameterException("1", "参数 字典项[id] 不能为空");

		dictService.deleteDict(id);
		return new Srm().setResultCode("0").setResultMessage("删除字典成功");
	}
	
	/**
	 * 根据DictCode去查询某个字典的详细信息
	 */
	@RequestMapping("/info")
	public Srm onQuerySingleDict(String dictcode) {
	    if (Strings.isNullOrEmpty(dictcode)) {
	        throw new ParameterException("1", "字典代码不能为空");
	    }
	    
	    return new Srm().setResultCode("0")
	                    .setResultMessage("查询字典信息成功")
	                    .addResult(dictService.getDictByCodeWithItems(dictcode));
	}
}
