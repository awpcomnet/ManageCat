package com.cat.manage.shipped.service;

import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.check.dao.CheckDao;
import com.cat.manage.check.domain.Check;
import com.cat.manage.check.service.CheckService;
import com.cat.manage.common.exception.BusinessException;
import com.cat.manage.shipped.dao.ShippedDao;
import com.cat.manage.shipped.domain.Shipped;
import com.cat.manage.shipped.domain.ShippedHead;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * @Description: 邮寄清单（子单）服务
 * @author 王航
 * @date 2016年2月19日 下午4:45:38
 */
@Service
public class ShippedService {
	@Autowired
	private ShippedDao shippedDao;
	
	@Autowired
	private ShippedHeadService shippedHeadService;
	
	@Autowired
	private CheckService checkService;
	
	/**
	 * 添加邮寄清单（子单）
	 * @param shipped
	 */
	public void addShipped(ShippedHead shippedHead, Shipped shipped, Check check){
		shippedDao.addShipped(shippedHead, shipped, check);
	}
	
	/**
	 * 修改邮寄清单（子单）
	 * 同时还修改了下单清单的汇率
	 * @param shipped
	 */
	public void updateShipped(Shipped shipped) {
		String dateOfManufacture = shipped.getDateOfManufacture();// 生产日期
		String qualityGuaranteePeriod = shipped.getQualityGuaranteePeriod();// 有效时长（单位：年）
		String periodOfValidity = shipped.getPeriodOfValidity();// 到期时间
		if (Strings.isNullOrEmpty(periodOfValidity)
				&& !Strings.isNullOrEmpty(dateOfManufacture)
				&& dateOfManufacture.length() == 8
				&& !Strings.isNullOrEmpty(qualityGuaranteePeriod)
				&& Double.parseDouble(qualityGuaranteePeriod) > 0) {
			DateTimeFormatter df = DateTimeFormat.forPattern("yyyyMMdd");
			DateTime beginTime = DateTime.parse(dateOfManufacture, df);
			int numberForMonth = (int) Math.floor(Double.valueOf(qualityGuaranteePeriod)*12);
			shipped.setPeriodOfValidity(beginTime.plusMonths(numberForMonth).toString(df));
		} else if(Strings.isNullOrEmpty(periodOfValidity)) {
			//如果生产日期和有效时长不能同时满足规则，并且到期日期也为空，则清空这两项数据
			shipped.setDateOfManufacture("");
			shipped.setQualityGuaranteePeriod("-1");
		}

		shippedDao.updateShipped(shipped);
		
		//同时修改下单清单的汇率
		Integer checkId = shipped.getCheckId();
		Double rate = shipped.getRate();
		if(rate != null){
			Check check = new Check();
			check.setId(checkId);
			check.setRate(rate);
			checkService.updateCheck(check);
		}
	}
	
	/**
	 * 根据下单清单唯一编号修改邮寄清单
	 * @param shipped
	 */
	public void updateShippedByCheckId(Shipped shipped){
		shippedDao.updateShippedByCheckId(shipped);
	}
	
	/**
	 * 单独用于修改邮寄清单重量
	 * @param shippedList
	 */
	public void updateShippedForWeight(List<Shipped> shippedList){
		if(shippedList == null || shippedList.size() <= 0)
			return;
		
		for(Shipped sh : shippedList){
			updateShipped(sh);
		}
	}
	
	/**
	 * 根据邮寄单号查询历史相同物品重量，无为0
	 * @param ids
	 * @return
	 */
	public List<Shipped> queryWeightPlan(String[] ids){
		List<Shipped> list = Lists.newArrayList();
		for(String id : ids){
			Shipped shipped = shippedDao.queryShippedWeightForPlan(Integer.valueOf(id));
			if(shipped == null){
				shipped = new Shipped();
				shipped.setId(Integer.valueOf(id));
				shipped.setWeight("0");
				continue;
			}
			if(shipped.getWeight() == null){
				shipped.setWeight("0");
			}
			list.add(shipped);
		}
		return list;
	}
	
	/**
	 * 根据邮寄清单子单编号修改清单状态
	 * @param ids
	 * @param shippedStatus
	 */
	public void updateShippedForStatus(Integer[] ids, String shippedStatus){
		shippedDao.updateShippedForStatus(ids, shippedStatus);
	}
	
	/**
	 * 根据邮寄清单唯一编号删除记录
	 * @param id
	 */
	public void deleteShipped(Integer id){
		shippedDao.deleteShipped(id);
	}
	
	/**
	 * 根据下单清单唯一编号删除记录
	 * @param checkId
	 */
	public void deleteShippedByCheckId(Integer checkId){
		//查询子单信息
		Shipped shipped = new Shipped();
		shipped.setCheckId(checkId);
		List<Shipped> list = shippedDao.queryShipped(shipped);
		if(list == null || list.size() <= 0)
			return;
		if(list.size() > 1)
			throw new BusinessException("1", "业务异常，下单清单编号["+checkId+"]存在"+list.size()+"个邮寄清单");
		
		//获取存储信息
		shipped = list.get(0);
		
		//获取主单唯一编号
		Integer headId = shipped.getHeadId();
		
		//删除子单
		shippedDao.deleteShippedByCheckId(checkId);
		
		//查询主单编号下是否还存在子单
		List<Shipped> shippedList = shippedDao.queryShippedByHeadId(headId);
		
		//不存在直接删除主单信息
		if(shippedList == null || shippedList.size() <= 0)
			shippedHeadService.deleteShippedHead(headId);
		
	}
	
	/**
	 * 根据邮寄清单主单唯一编号删除记录
	 * @param headId
	 */
	public void deleteShippedByHeadId(Integer headId){
		shippedDao.deleteShippedByHeadId(headId);
	}
	
	/**
	 * 根据邮寄清单子单删除记录
	 * 如果主单下已没有记录，同时删除主单
	 * @param ids
	 */
	public void deleteShippedByIds(Integer[] ids){
		//查询需要删除的订单记录
		List<Shipped> list = shippedDao.queryShippedByIds(ids);
		if(list == null || list.size() <= 0)
			throw new BusinessException("1", "未找到需要删除的订单记录。");
		
		//校验合法性,重新确定需要删除记录的ID
		List<Integer> newIds = Lists.newArrayList();
		List<Integer> checkIds = Lists.newArrayList();
		Integer headId = null;
		for(Shipped shipped : list){
			if(headId == null) {
				headId = shipped.getHeadId();
			} else {
				if(headId != shipped.getHeadId())
					throw new BusinessException("1", "要删除的邮寄清单记录非同一主邮寄清单内记录");
			}
			
			if(!"1".equals(shipped.getShippedStatus()))
				throw new BusinessException("1", "存在邮寄清单不为[已邮寄]状态");
			
			newIds.add(shipped.getId());
			checkIds.add(shipped.getCheckId());
		}
		
		ShippedHead shippedHead = shippedHeadService.queryShippedHeadById(headId);
		if(shippedHead == null)
			throw new BusinessException("1", "邮寄清单主单不存在");
		
		System.out.println("#######>");
		Integer[] aa = (Integer[])newIds.toArray(new Integer[]{});
		for(Integer a : aa){
			System.out.println(a);
		}
		
		//删除记录
		shippedDao.deleteShippedByIds((Integer[])newIds.toArray(new Integer[]{}));
		
		//恢复下单清单记录状态
		checkService.updateCheckForStatus((Integer[])checkIds.toArray(new Integer[]{}), "0");//恢复为[已下单状态]
		
		//查询剩余记录数
		List<Shipped> lastShippeds = shippedDao.queryShippedByHeadId(headId);
		if(lastShippeds == null || lastShippeds.size() <= 0)
			shippedHeadService.deleteShippedHeadById(headId);
			
	}
	
	/**
	 * 查询邮寄清单（子单）记录
	 * @param shipped
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Shipped> queryShipped(Shipped shipped, Integer pageNum, Integer pageSize){
		PageHelper.startPage(pageNum, pageSize);
		List<Shipped> list = shippedDao.queryShipped(shipped);
		PageInfo<Shipped> page = new PageInfo<Shipped>(list);
		return page;
	}
	
	/**
	 * 根据邮寄清单主单唯一编号查询子单信息
	 * @param headId
	 * @return
	 */
	public List<Shipped> queryShippedByHeadId(Integer headId){
		return shippedDao.queryShippedByHeadId(headId);
	}
	
	/**
	 * 根据邮寄清单子单唯一编号查询记录
	 * @param id
	 * @return
	 */
	public Shipped queryShippedById(Integer id){
		return shippedDao.queryShippedById(id);
	}
	
	/**
	 * 查询邮寄清单，仅系列/单品 同步时使用
	 * @param seriesId
	 * @param singleId
	 * @return
	 */
	public List<Shipped> queryShippedForSync(Integer seriesId, Integer singleId){
		if(seriesId == null && singleId == null)
			return null;
		if(seriesId != null && singleId != null)
			return null;
		
		Shipped shipped = new Shipped();
		shipped.setSeriesId(seriesId);
		shipped.setSingleId(singleId);
		return shippedDao.queryShippedForSync(shipped);
	}
	
	/**
	 * 修改邮寄清单的 品牌，系列，单品， 仅系列/单品 同步时使用
	 * @param id
	 * @param brandId
	 * @param seriesId
	 * @param singleId
	 */
	public void updateShippedForSync(Integer id, Integer brandId, Integer seriesId, Integer singleId){
		Shipped shipped = new Shipped();
		shipped.setId(id);
		shipped.setBrandId(brandId);
		shipped.setSeriesId(seriesId);
		shipped.setSingleId(singleId);
		shippedDao.updateShipped(shipped);
	}
	
	/**
	 * 根据下单清单唯一编号查询邮寄清单
	 * @param checkId
	 * @return
	 */
	public Shipped queryShippedByCheckId(Integer checkId){
		return shippedDao.queryShippedByCheckId(checkId);
	}
	
	/**
	 * 邮寄子单产品数量拆分
	 * 1.【已邮寄状态】 拆分后，旧的邮寄清单和旧的下单清单状态不变，仅修改下单数量。新的邮寄清单和下单清单状态为[已邮寄]
	 * 2.【部分入库状态】拆分后，如果拆分数量=原下单数-已入库数，则旧邮寄清单和旧下单清单状态修改为[已入库]，同时修改数量。新的邮寄清单和下单清单状态为[已邮寄]
	 * 	                                   如果拆分数量<原下单数-已入库数，则旧邮寄清单和旧下单清单状态不变，同时修改数量。新的邮寄清单和下单清单状态为[已邮寄]
	 * @param shippedId
	 * @param divisionNum
	 */
	public void updateDivisionOrderByShippedId(Integer shippedId, int divisionNum){
		//查询邮寄清单信息
		Shipped oldShipped = shippedDao.queryShippedById(shippedId);
		if(oldShipped == null){
			throw new BusinessException("1", "邮寄清单为空，邮寄清单唯一编号["+shippedId+"]");
		}
		
		Integer checkId = oldShipped.getCheckId();
		Check oldCheck = checkService.queryCheckById(checkId);
		if(oldCheck == null){
			throw new BusinessException("1", "下单清单为空，下单清单唯一编号["+checkId+"]");
		}
		
		String oldShippedStatus = oldShipped.getShippedStatus();
		Integer checkNum = oldCheck.getNum();
		Integer storeNum = oldShipped.getStoreNum();
		if(divisionNum == 0){
			return;
		}
		
		String shippedStatus = "";
		String checkStatus = "";
		if("1".equals(oldShippedStatus)){
			//已邮寄
			if(divisionNum > (checkNum - storeNum - 1)){
				throw new BusinessException("1", "分割的商品数量["+divisionNum+"]已超过数量上限。上限["+(checkNum - storeNum - 1)+"]=下单数量["+checkNum+"]-已入库数量["+storeNum+"]-1");
			}
		}else if("7".equals(oldShippedStatus)){
			//部分入库
			if(divisionNum > (checkNum - storeNum)){
				throw new BusinessException("1", "分割的商品数量["+divisionNum+"]已超过数量上限。上限["+(checkNum - storeNum)+"]=下单数量["+checkNum+"]-已入库数量["+storeNum+"]");
			}
		}else{
			throw new BusinessException("1", "邮寄清单状态不为[已邮寄]或[部分入库状态]");
		}
		
		String changeShippedStatus = "";
		String changeCheckStatus = "";
		if(divisionNum == (checkNum - storeNum)){
			//状态设置为已入库
			changeShippedStatus = "2";
			changeCheckStatus = "2";
		}
		
		shippedStatus = "1";
		checkStatus = "1";
		//1.修改邮寄清单的数量
		Shipped oldChangeShipped = new Shipped();
		oldChangeShipped.setId(shippedId);
		oldChangeShipped.setNum(checkNum - divisionNum);
		oldChangeShipped.setShippedStatus(changeShippedStatus);
		shippedDao.updateShipped(oldChangeShipped);
		
		//2.修改下单清单的数量
		Check oldChangeCheck = new Check();
		oldChangeCheck.setId(checkId);
		oldChangeCheck.setNum(checkNum - divisionNum);
		oldChangeCheck.setOrderStatus(changeCheckStatus);
		checkService.updateCheck(oldChangeCheck);
		
		//3.复制下单清单  只需要重置状态和数量，其他不需要重置
		Check newCheck = new Check();
		newCheck = oldCheck;
		newCheck.setOrderStatus(checkStatus);//设置状态
		newCheck.setNum(divisionNum);//设置数量
		checkService.addCheck(newCheck);

		//4.复制邮寄清单
		Shipped newShipped = new Shipped();
		newShipped = oldShipped;
		newShipped.setShippedStatus(shippedStatus);
		newShipped.setNum(divisionNum);
		shippedDao.addShippedForCopyShipped(newShipped, newCheck.getId());
	}
	
}
