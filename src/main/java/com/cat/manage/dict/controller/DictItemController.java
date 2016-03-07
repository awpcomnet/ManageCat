package com.cat.manage.dict.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.manage.common.exception.ParameterException;
import com.cat.manage.common.model.Srm;
import com.cat.manage.common.param.HttpParams;
import com.cat.manage.dict.domain.Dict;
import com.cat.manage.dict.domain.DictItem;
import com.cat.manage.dict.service.DictService;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;

/**
 * @Description: 字典项Http接口
 * @author
 * @date 2015年11月18日 上午10:09:34
 */
@RestController
@RequestMapping("/dictitem")
public class DictItemController {

	@Autowired
	private DictService dictService;

	@RequestMapping("/items")
	public Srm onQueryDictItem(DictItem item, HttpServletRequest request) {
		HttpParams params = HttpParams.buildFrom(request);
		int pageNumber = params.getInt("page");
		int pageSize = params.getInt("limit");
		
		PageInfo<DictItem> page = dictService.queryFuzzyItem(item, pageNumber, pageSize);
		return new Srm().setResultCode("0").setResultMessage("查询成功").buildPageInfo(page);	
	}
	
	@RequestMapping("/find")
	public Srm onFindDictItemWithCode(String code) {
		if (Strings.isNullOrEmpty(code))
			throw new ParameterException("1", "参数 字典项 [code] 不能为空");
		
		Dict dict = dictService.getDictByCodeWithItems(code);
		if(dict == null)
			return new Srm().setResultCode("1").setResultMessage("查询失败");
		
		return new Srm().setResultCode("0").setResultMessage("查询成功")
				.addAll(dict.getItems());
	}

	@RequestMapping("/add")
	public Srm onAddDictItem(DictItem item) {
		if (item == null)
			throw new ParameterException("1", "参数 字典子项 不能为空");

		if (Strings.isNullOrEmpty(item.getName()))
			throw new ParameterException("1", "字典子项名称[name]不能为空");

		if (Strings.isNullOrEmpty(item.getCode()))
			throw new ParameterException("1", "字典子项名称[code]不能为空");

		if (Strings.isNullOrEmpty(item.getName()))
			throw new ParameterException("1", "字典子项名称[typeId]不能为空");

		dictService.addDictItem(item);
		return new Srm().setResultCode("0").setResultMessage("添加字典子项成功");
	}

	@RequestMapping("/delete")
	public Srm onDeleteDictItem(Integer[] id) {
		if (id == null)
			throw new ParameterException("1", "参数 字典项[id] 不能为空");
		if (id.length <= 0)
			throw new ParameterException("1", "参数 字典项[id] 不能为空");

		dictService.deleteDictItem(id);
		return new Srm().setResultCode("0").setResultMessage("删除字典子项成功");
	}

	@RequestMapping("/modify")
	public Srm onModifyDictItem(DictItem item) {
		if (item == null)
			throw new ParameterException("1", "参数 字典子项 不能为空");
		if (Strings.isNullOrEmpty(item.getName()))
			throw new ParameterException("1", "字典子项名称[name]不能为空");

		if (Strings.isNullOrEmpty(item.getCode()))
			throw new ParameterException("1", "字典子项名称[code]不能为空");

		if (Strings.isNullOrEmpty(item.getName()))
			throw new ParameterException("1", "字典子项名称[typeId]不能为空");
		
		dictService.updateDictItem(item);
		return new Srm().setResultCode("0").setResultMessage("修改字典子项成功");
	}
	
}
