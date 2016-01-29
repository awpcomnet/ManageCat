package com.cat.manage.order.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.common.exception.BusinessException;
import com.cat.manage.order.dao.SubOrderDao;
import com.cat.manage.order.domain.SubOrder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;

@Service
public class SubOrderService {
	
	@Autowired
	private SubOrderDao subOrderDao;
	
	/**
	 * 添加子订单
	 * @param subOrder
	 */
	public void addSubOrder(SubOrder subOrder){
		subOrderDao.addSubOrder(subOrder);
	}
	
	/**
	 * 查询单个子订单
	 */
	public SubOrder querySubOrder(Integer subOrderId){
		return subOrderDao.querySubOrderById(subOrderId);
	}
	
	/**
	 * 查询所有子订单
	 * @return
	 */
	public List<SubOrder> querySubOrderAll(String startTime, String endTime){
		return subOrderDao.querySubOrderAll(startTime, endTime);
	}
	
	/**
	 * 查询所有子订单
	 * @return
	 */
	public PageInfo<SubOrder> querySubOrderAllForName(SubOrder subOrder, String startTime, String endTime, Integer pageNum, Integer pageSize){
		PageHelper.startPage(pageNum, pageSize);
		List<SubOrder> list = subOrderDao.querySubOrderAllForName(subOrder, startTime, endTime);
		PageInfo<SubOrder> page = new PageInfo<SubOrder>(list);
		return page;
	}
	
	/**
	 * 修改子订单信息
	 * @param subOrder
	 */
	public void updateSubOrder(SubOrder subOrder){
		subOrderDao.updateSubOrder(subOrder);
	}
	
	/**
	 * 子订单拆分
	 * @param subOrderId 原子订单Id
	 * @param num 原子订单拆分后剩余数量
	 */
	public void updateSubOrderForGroup(Integer subOrderId, int num){
		//查询原订单信息
		SubOrder subOrder = subOrderDao.querySubOrderById(subOrderId);
		if(subOrder == null)
			return;
		//TODO 需要将"销售中"和"已售罄"替换
		if(subOrder.getCurState().equals("销售中"))
			throw new BusinessException("1", "子订单正在销售中，不支持拆分");
		if(subOrder.getCurState().equals("已售罄"))
			throw new BusinessException("1", "子订单已售罄，不支持拆分");
		
		Integer sum = subOrder.getNum();
		//复制订单
		if(sum <= num)
			throw new BusinessException("1", "拆分子订单失败，拆分订单的产品数量大于或等于原有数量");
		Integer addFlag = subOrderDao.addSubOrderForCopy(subOrderId, sum-num);
		if(addFlag == 0)
			throw new BusinessException("1", "拆分子订单失败，未查询到要拆分的订单");
		//修改原订单
		SubOrder modifyOrder = new SubOrder();
		modifyOrder.setSuborderId(subOrderId);
		modifyOrder.setNum(num);
		Integer modifyFlag = subOrderDao.updateSubOrder(modifyOrder);
		if(modifyFlag == 0)
			throw new BusinessException("1", "拆分子订单失败，未查询到要拆分的订单");
	}
	
	/**
	 * 根据主订单单号删除子订单
	 * @param parentId
	 */
	public void deleteSubOrderByParentId(Integer parentId){
		subOrderDao.deleteSubOrderByParentId(parentId);
	}
	
	/**
	 * 根据子订单单号删除子订单
	 * @param subOrderIds
	 */
	public void deleteSubOrderByIds(Integer[] subOrderIds){
		subOrderDao.deleteSubOrderForMoreId(subOrderIds);
	}
	
	/**
	 * 修改子订单状态
	 * @param suborderId
	 * @param curState
	 */
	public void updateSubOrderForStatus(Integer orderId, String curState){
		subOrderDao.updateSubOrderForStatus(orderId, curState);
	}
	
	/**
	 * 合并子订单
	 * @param subOrderIds 需要合并的所有子订单ID
	 * @param orderPrice 合并后下单单价
	 * @param transferPrice 合并后运费（单个）
	 * @param costPrice 合并后成本单价
	 * @param sellingPrice 合并后销售单价
	 * @param curState 合并后子订单状态
	 */
	public void mergeSubOrder(Integer[] subOrderIds, Double orderPrice, Double transferPrice, Double costPrice, Double sellingPrice, String curState){
		Integer subOrderId = subOrderIds[0];
		int orderNum = subOrderIds.length;
		
		//查询子订单是否存在, 排除已砍单订单
		List<SubOrder> list = subOrderDao.querySubOrderByIds(subOrderIds, new String[]{"8"});
		if(list.size() != orderNum)
			throw new BusinessException("1", "记录子订单数和要合并的子订单数不一致");
		//TODO 需要将"销售中"和"已售罄"替换
		for(SubOrder so : list){
			if(so.getCurState().equals("销售中"))
				throw new BusinessException("1", "子订单存在状态为销售中，不支持合并");
			if(so.getCurState().equals("已售罄"))
				throw new BusinessException("1", "子订单存在状态为已售罄，不支持合并");
		}
		
		//校验子订单数量是否一致，校验子订单品牌、系列、单品是否一致, 校验子订单当前状态是否为已砍单
		String brandId = "";
		String seriesId = "";
		String singleId = "";
		
		for(SubOrder subOrder: list){
			if(subOrder.getCurState().equals("8")){//已砍单
				throw new BusinessException("1", "合并的订单中存在[已砍单]的订单，主订单编号["+subOrder.getSuperOrderId()+"]，子订单编号["+subOrder.getSuborderId()+"]");
			}
			if(Strings.isNullOrEmpty(brandId))
				brandId = String.valueOf(subOrder.getBrandId());
			if(Strings.isNullOrEmpty(seriesId))
				seriesId = String.valueOf(subOrder.getSeriesId());
			if(Strings.isNullOrEmpty(singleId))
				singleId = String.valueOf(subOrder.getSingleId());
			
			if(!brandId.equals(String.valueOf(subOrder.getBrandId())))
				throw new BusinessException("1", "合并的订单中品牌存在差异");
			if(!seriesId.equals(String.valueOf(subOrder.getSeriesId())))
				throw new BusinessException("1", "合并的订单中系列存在差异");
			if(!singleId.equals(String.valueOf(subOrder.getSingleId())))
				throw new BusinessException("1", "合并的订单中单品存在差异");
		}
		
		//合并至一个子订单中
		subOrderDao.updateSubOrderForMerge(subOrderId, subOrderIds, orderPrice, transferPrice, costPrice, sellingPrice, curState);
		
		//剔除合并后的子订单，剩余订单删除
		subOrderIds = Arrays.copyOfRange(subOrderIds, 1, subOrderIds.length);
		
		//删除剩余子订单
		int deleteNum = subOrderDao.deleteSubOrderForMoreId(subOrderIds);
		if(deleteNum != subOrderIds.length){
			throw new BusinessException("1", "删除剩余子订单失败，需要删除["+subOrderIds.length+"]条子订单，实际删除["+deleteNum+"]条");
		}
	}
}
