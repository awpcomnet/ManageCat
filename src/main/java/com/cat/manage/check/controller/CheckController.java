package com.cat.manage.check.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cat.manage.base.domain.Brand;
import com.cat.manage.base.domain.Series;
import com.cat.manage.base.domain.Singleproduct;
import com.cat.manage.base.service.BrandService;
import com.cat.manage.base.service.SeriesService;
import com.cat.manage.base.service.SingleproductService;
import com.cat.manage.batch.service.BatchService;
import com.cat.manage.check.domain.Check;
import com.cat.manage.check.service.CheckService;
import com.cat.manage.common.exception.BusinessException;
import com.cat.manage.common.model.Srm;
import com.cat.manage.common.param.HttpParams;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/check")
public class CheckController {
	@Autowired
	private CheckService checkService;
	
	@Autowired
	private BrandService brandService;
	
	@Autowired
	private SeriesService seriesService;
	
	@Autowired
	private BatchService batchService;
	
	@Autowired
	private SingleproductService singleproductService;
	
	@RequestMapping("/add")
	public Srm addCheck(Check check){
		Integer brandId = check.getBrandId();
		Integer seriesId = check.getSeriesId();
		Integer singleId = check.getSingleId();
		String batchNo = check.getBatchNo();
		/**参数合法性校验*/
		//存在性验证
		Brand brand = brandService.queryBrandById(brandId);
		if(brand == null)
			throw new BusinessException("1", "品牌参数["+brandId+"]非法");
		Series series = seriesService.querySeriesById(seriesId);
		if(series == null)
			throw new BusinessException("1", "系列参数["+seriesId+"]非法");
		Singleproduct singleproduct = singleproductService.querySingleproductBySingleId(singleId);
		if(singleproduct == null)
			throw new BusinessException("1", "单品参数["+singleId+"]非法");
		//关联性验证
		if(!series.getOfOrigin().equals(brandId+""))
			throw new BusinessException("1", "品牌["+brand.getBrandName()+"]与系列["+series.getSeriesName()+"]从属关系非法");
		if(!singleproduct.getOfOrigin().equals(seriesId+""))
			throw new BusinessException("1", "系列["+series.getSeriesName()+"]与单品["+singleproduct.getSingleName()+"]从属关系非法");
		if(!batchService.checkBatchNo(batchNo))
			throw new BusinessException("1", "批次号非法,批次号["+batchNo+"]");
		
		//添加下单清单是设置状态
		check.setOrderStatus("0");//已下单
		checkService.addCheck(check);
		return new Srm().setResultCode("0").setResultMessage("添加下单记录成功");
	}
	
	@RequestMapping("/modify")
	public Srm modifyCheck(Check check){
		//检查订单状态是否为 已下单
		if(!"0".equals(check.getOrderStatus())){
			return new Srm().setResultCode("1").setResultMessage("清单状态不为【已下单】");
		}
		checkService.updateCheck(check);
		return new Srm().setResultCode("0").setResultMessage("修改下单清单成功");
	}
	
	@RequestMapping("/delete")
	public Srm deleteCheck(Integer id){
		checkService.deleteCheck(id);
		return new Srm().setResultCode("0").setResultMessage("删除订单成功");
	}
	
	@RequestMapping("/query")
	public Srm queryCheck(Check check, String startTime, String endTime, HttpServletRequest request){
		HttpParams params = HttpParams.buildFrom(request);
		Integer pageNum = params.getInt("page");
		Integer pageSize = params.getInt("limit");
		
		PageInfo<Check> page = checkService.queryCheck(check, startTime, endTime, pageNum, pageSize);
		return new Srm().setResultCode("0").setResultMessage("查询订单成功").buildPageInfo(page);
	}
}
