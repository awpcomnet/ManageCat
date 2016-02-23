package com.cat.manage.selled.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.manage.check.domain.Check;
import com.cat.manage.common.exception.BusinessException;
import com.cat.manage.common.model.Srm;
import com.cat.manage.common.param.HttpParams;
import com.cat.manage.selled.domain.Selled;
import com.cat.manage.selled.service.SelledService;
import com.github.pagehelper.PageInfo;

/**
 * @Description: 售出控制器
 * @author 王航
 * @date 2016年2月23日 下午3:41:29
 */
@RestController
@RequestMapping("/selled")
public class SelledController {

	@Autowired
	private SelledService selledService;
	
	@RequestMapping("/add")
	public Srm addSelled(Selled selled){
		if(selled.getStoreId() == null)
			throw new BusinessException("1", "仓库记录编号不能为空");
		if(selled.getSellingPrice() == null)
			throw new BusinessException("1", "售价不能为空");
		
		selledService.addSelled(selled);
		return new Srm().setResultCode("0").setResultMessage("添加售出清单成功");
	}
	
	@RequestMapping("/query")
	public Srm querySelledForPage(Selled selled, String startTime, String endTime, HttpServletRequest request){
		HttpParams params = HttpParams.buildFrom(request);
		Integer pageNum = params.getInt("page");
		Integer pageSize = params.getInt("limit");
		
		PageInfo<Selled> page = selledService.querySelledForPage(selled, startTime, endTime, pageNum, pageSize);
		return new Srm().setResultCode("0").setResultMessage("查询售出清单成功").buildPageInfo(page);
	}
	
	@RequestMapping("/modify")
	public Srm modifySelled(Selled selled){
		selledService.updateSelled(selled);
		return new Srm().setResultCode("0").setResultMessage("修改售出记录成功");
	}
	
	@RequestMapping("/delete")
	public Srm deleteSelled(Integer id){
		selledService.deleteSelled(id);
		return new Srm().setResultCode("0").setResultMessage("删除售出记录成功");
	}
}
