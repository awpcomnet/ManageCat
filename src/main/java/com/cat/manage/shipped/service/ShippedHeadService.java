package com.cat.manage.shipped.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.check.domain.Check;
import com.cat.manage.check.service.CheckService;
import com.cat.manage.common.exception.BusinessException;
import com.cat.manage.shipped.dao.ShippedHeadDao;
import com.cat.manage.shipped.domain.Shipped;
import com.cat.manage.shipped.domain.ShippedHead;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

/**
 * 邮寄清单（主单）服务
 * @author wanghang
 *
 */
@Service
public class ShippedHeadService {
	@Autowired
	private ShippedHeadDao shippedHeadDao;
	
	@Autowired
	private ShippedService shippedService;
	
	@Autowired
	private CheckService checkService;
	
	/**
	 * 添加邮寄清单（主单）
	 * @param shippedHead
	 */
	public void addShippedHead(ShippedHead	shippedHead){
		shippedHeadDao.addShippedHead(shippedHead);
	}
	
	/**
	 * 修改邮寄清单（主单）
	 * @param shippedHead
	 */
	public void updateShippedHead(ShippedHead shippedHead){
		shippedHeadDao.updateShippedHead(shippedHead);
	}
	
	/**
	 * 根据邮寄清单（主单）唯一编号删除记录
	 * 同时删除子订单
	 * @param id
	 */
	public void deleteShippedHead(Integer id){
		//查询是否存在子订单
		List<Shipped> shippeds = shippedService.queryShippedByHeadId(id);
		if(shippeds != null && shippeds.size() >= 1){
			//保存子单下单清单唯一编号，用于修改下单清单状态
			List<Integer> checkIds = Lists.newArrayList();
			
			//验证子单状态是否为【已邮寄】，否则不能删除
			for(Shipped shipped : shippeds){
				if(!"1".equals(shipped.getShippedStatus()))
					throw new BusinessException("1", "邮寄清单子单状态不为【已邮寄】");
				
				checkIds.add(shipped.getCheckId());
			}
			
			//删除子单信息
			shippedService.deleteShippedByHeadId(id);
			
			//修改下单清单状态 0已下单
			checkService.updateCheckForStatus((Integer[])checkIds.toArray(new Integer[]{}), "0");
		}
		
		//删除邮寄清单主单
		shippedHeadDao.deleteShippedHead(id);
	}
	
	/**
	 * 根据邮寄清单主单唯一编号删除主单记录
	 * 仅删除主单记录
	 * @param id
	 */
	public void deleteShippedHeadById(Integer id){
		shippedHeadDao.deleteShippedHead(id);
	}
	
	/**
	 * 查询邮寄清单主单
	 * @param shippedHead
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<ShippedHead> queryShippedHead(ShippedHead shippedHead, String flag, Integer pageNum, Integer pageSize){
		PageHelper.startPage(pageNum, pageSize);
		List<ShippedHead> list = shippedHeadDao.queryShippedHead(shippedHead, flag);
		PageInfo<ShippedHead> page = new PageInfo<ShippedHead>(list);
		return page;
	}
	
	/**
	 * 根据快递单号查询邮寄清单（主单）
	 * @param trackingNumber
	 * @return
	 */
	public ShippedHead queryShippedHeadBytrackingNumber(String trackingNumber){
		ShippedHead shippedHead = new ShippedHead();
		shippedHead.setTrackingNumber(trackingNumber);
		List<ShippedHead> list = shippedHeadDao.queryShippedHead(shippedHead, "");
		if(list.size() > 1)
			throw new BusinessException("1", "快递单号【"+trackingNumber+"】存在"+list.size()+"个主邮寄清单！");
		if(list.size() == 1){
			return list.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * 根据邮寄清单主单唯一编号查询主单信息
	 * @param id
	 * @return
	 */
	public ShippedHead queryShippedHeadById(Integer id){
		return shippedHeadDao.queryShippedHeadById(id);
	}
	
	/**
	 * 根据查询所有邮寄清单主单信息
	 * @param trackingNumber
	 * @return
	 */
	public List<ShippedHead> queryShippedHeadAll(ShippedHead shippedHead){
		return shippedHeadDao.queryShippedHead(shippedHead, "");
	}
	
	/**
	 * 添加邮寄清单（主单）
	 * 查询下单清单，添加下单清单内容到邮寄清单子单
	 * 修改下单清单状态
	 * @param shippedHead
	 * @param checkIds
	 */
	public void addShippedHeadAndShipped(ShippedHead shippedHead, Integer[] checkIds){
		//查询快递编号是否已经存在邮寄清单主单
		ShippedHead querySH = shippedHeadDao.queryShippedHeadByTrackingNumber(shippedHead.getTrackingNumber());
		if(querySH == null){//不存在
			//添加邮寄清单主单
			shippedHeadDao.addShippedHead(shippedHead);
			
			//查询邮寄清单主订单
			ShippedHead sh = shippedHeadDao.queryShippedHeadByTrackingNumber(shippedHead.getTrackingNumber());
			
			//设置邮寄清单子单
			Shipped shipped = new Shipped();
			shipped.setShippedStatus("1");//1邮寄中 
			
			//查询下单清单记录
			List<Check> checks = checkService.queryCheckByIds(checkIds);
			
			//轮询添加
			for(Check c : checks){
				//检测下单清单状态是否为[已下单]
				if(!"0".equals(c.getOrderStatus()))
					throw new BusinessException("1", "提交的下单清单中存在状态不为[已下单]的记录");
				
				shippedService.addShipped(sh, shipped, c);
			}
			
			//修改下单清单状态
			checkService.updateCheckForStatus(checkIds, "1");//1邮寄中
		} else {//存在
			//设置邮寄清单子单
			Shipped shipped = new Shipped();
			shipped.setShippedStatus("1");//1邮寄中 
			
			//查询下单清单记录
			List<Check> checks = checkService.queryCheckByIds(checkIds);
			
			//轮询添加
			for(Check c : checks){
				//检测下单清单状态是否为[已下单]
				if(!"0".equals(c.getOrderStatus()))
					throw new BusinessException("1", "提交的下单清单中存在状态不为[已下单]的记录");
				
				shippedService.addShipped(querySH, shipped, c);
			}
			
			//修改下单清单状态
			checkService.updateCheckForStatus(checkIds, "1");//1邮寄中
		}
		
		
	}
	
	/**
	 * 根据下单清单唯一编号（多个），查询邮寄清单主单
	 * @param checkIds
	 * @return
	 */
	public List<ShippedHead> queryShippedHeadIncludeCheckIds(Integer[] checkIds){
		return shippedHeadDao.queryShippedHeadByCheckIds(checkIds);
	}
}
