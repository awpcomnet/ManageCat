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
	 * @param id
	 */
	public void deleteShippedHead(Integer id){
		shippedHeadDao.deleteShippedHead(id);
	}
	
	/**
	 * 查询邮寄清单主单
	 * @param shippedHead
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<ShippedHead> queryShippedHead(ShippedHead shippedHead, Integer pageNum, Integer pageSize){
		PageHelper.startPage(pageNum, pageSize);
		List<ShippedHead> list = shippedHeadDao.queryShippedHead(shippedHead);
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
		List<ShippedHead> list = shippedHeadDao.queryShippedHead(shippedHead);
		if(list.size() > 1)
			throw new BusinessException("1", "快递单号【"+trackingNumber+"】存在"+list.size()+"个主邮寄清单！");
		if(list.size() == 1){
			return list.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * 添加邮寄清单（主单）
	 * 查询下单清单，添加下单清单内容到邮寄清单子单
	 * 修改下单清单状态
	 * @param shippedHead
	 * @param checkIds
	 */
	public void addShippedHeadAndShipped(ShippedHead shippedHead, Integer[] checkIds){
		//添加邮寄清单主单
		shippedHeadDao.addShippedHead(shippedHead);
		
		//设置邮寄清单子单
		Shipped shipped = new Shipped();
		shipped.setShippedStatus("1");//1邮寄中 
		
		//查询下单清单记录
		List<Check> checks = checkService.queryCheckByIds(checkIds);
		
		//轮询添加
		for(Check c : checks){
			shippedService.addShipped(shippedHead, shipped, c);
		}
		
		//修改下单清单状态
		checkService.updateCheckForStatus(checkIds, "1");//1邮寄中
	}
}
