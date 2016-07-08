package com.cat.manage.base.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.base.dao.SyncDetailDao;
import com.cat.manage.base.domain.SyncDetail;
import com.cat.manage.check.domain.Check;
import com.cat.manage.selled.domain.Selled;
import com.cat.manage.shipped.domain.Shipped;
import com.cat.manage.store.domain.Store;
import com.google.common.base.Strings;

/**
 * 同步详情服务
 * @author wanghang
 *
 */
@Service
public class SyncDetailService {
	public static final String CHECKTYPE = "01";
	public static final String SHIPPEDTYPE = "02";
	public static final String STORETYPE = "03";
	public static final String SELLTYPE = "04";

	@Autowired
	private SyncDetailDao syncDetailDao;
	
	/**
	 * 添加同步详情
	 */
	public void addSyncDetail(SyncDetail syncDetail){
		syncDetailDao.addDetail(syncDetail);
	}
	
	/**
	 * 添加同步详情
	 * @param list
	 * @param syncFlag
	 */
	public void addSyncDetailList(List<?> list, String syncFlag){
		if(list == null || list.size() <= 0)
			return;
		if(Strings.isNullOrEmpty(syncFlag))
			return;
		
		for(Object obj : list){
			SyncDetail detail = this.changeToSyncDetail(obj);
			if(detail == null)
				continue;
			detail.setSyncFlag(syncFlag);
			this.addSyncDetail(detail);
		}
		
	}
	
	/**
	 * 根据同步标记查询同步详情
	 * @param syncFlag
	 * @return
	 */
	public List<SyncDetail> querySyncDetailBySyncFlag(String syncFlag){
		SyncDetail syncDetail = new SyncDetail();
		syncDetail.setSyncFlag(syncFlag);
		return syncDetailDao.queryDetail(syncDetail);
	}
	
	/**
	 * 根据同步标记删除记录
	 * @param syncFlag
	 */
	public void deleteSyncDetailBySyncFlag(String syncFlag){
		syncDetailDao.deleteDetailBySyncFlag(syncFlag);
	}
	
	/**
	 * 领域模型转换
	 * @param object
	 * @return
	 */
	private SyncDetail changeToSyncDetail(Object object){
		if(object.getClass().equals(Check.class))
			return this.checkChange((Check)object);
		if(object.getClass().equals(Shipped.class))
			return this.shippedChange((Shipped)object);
		if(object.getClass().equals(Store.class))
			return this.storeChange((Store)object);
		if(object.getClass().equals(Selled.class))
			return this.sellChange((Selled)object);
		return null;
	}
	
	/**
	 * 下单模型转换
	 * @param check
	 * @return
	 */
	private SyncDetail checkChange(Check check){
		if(check == null)
			return null;
		SyncDetail detail = new SyncDetail();
		detail.setBrandId(check.getBrandId());
		detail.setSeriesId(check.getSeriesId());
		detail.setSingleId(check.getSingleId());
		detail.setOrderType(CHECKTYPE);
		detail.setOrderId(check.getId());
		return detail;
	}
	
	/**
	 * 邮寄模型转换
	 * @param shipped
	 * @return
	 */
	private SyncDetail shippedChange(Shipped shipped){
		if(shipped == null)
			return null;
		SyncDetail detail = new SyncDetail();
		detail.setBrandId(shipped.getBrandId());
		detail.setSeriesId(shipped.getSeriesId());
		detail.setSingleId(shipped.getSingleId());
		detail.setOrderType(SHIPPEDTYPE);
		detail.setOrderId(shipped.getId());
		return detail;
	}
	
	/**
	 * 入库模型转换
	 * @param store
	 * @return
	 */
	private SyncDetail storeChange(Store store){
		if(store == null)
			return null;
		SyncDetail detail = new SyncDetail();
		detail.setBrandId(store.getBrandId());
		detail.setSeriesId(store.getSeriesId());
		detail.setSingleId(store.getSingleId());
		detail.setOrderType(STORETYPE);
		detail.setOrderId(store.getId());
		return detail;
	}
	
	/**
	 * 售出模型转换
	 * @param sell
	 * @return
	 */
	private SyncDetail sellChange(Selled sell){
		if(sell == null)
			return null;
		SyncDetail detail = new SyncDetail();
		detail.setBrandId(sell.getBrandId());
		detail.setSeriesId(sell.getSeriesId());
		detail.setSingleId(sell.getSingleId());
		detail.setOrderType(SELLTYPE);
		detail.setOrderId(sell.getId());
		return detail;
	}
	
}
