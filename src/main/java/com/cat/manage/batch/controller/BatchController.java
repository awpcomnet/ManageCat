package com.cat.manage.batch.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.manage.batch.domain.Batch;
import com.cat.manage.batch.service.BatchService;
import com.cat.manage.common.model.Srm;
import com.cat.manage.common.param.HttpParams;
import com.github.pagehelper.PageInfo;

/**
 * 批次号控制器
 * @author wanghang
 *
 */
@RestController
@RequestMapping("/batch")
public class BatchController {
	
	@Autowired
	private BatchService batchService;
	
	/**
	 * 生成批次号
	 * @return
	 */
	@RequestMapping("/create")
	public Srm createBatchNo(){
		String batchNo = batchService.addBatch();
		return new Srm().setResultCode("0").setResultMessage("批次号生成成功").addResult(batchNo);
	}
	
	/**
	 * 查询批次号
	 * @param batch
	 * @param request
	 * @return
	 */
	@RequestMapping("/query")
	public Srm queryBatchNoForPage(Batch batch, HttpServletRequest request){
		HttpParams params = HttpParams.buildFrom(request);
		Integer pageNum = params.getInt("page");
		Integer pageSize = params.getInt("limit");
		
		PageInfo<Batch> page = batchService.queryBatchForPage(batch, pageNum, pageSize);
		return new Srm().setResultCode("0").setResultMessage("查询批次号成功").buildPageInfo(page);
	}
	
}
