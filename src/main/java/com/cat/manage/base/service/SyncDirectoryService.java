package com.cat.manage.base.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.base.dao.SyncDirectoryDao;
import com.cat.manage.base.domain.Series;
import com.cat.manage.base.domain.Singleproduct;
import com.cat.manage.base.domain.SyncDetail;
import com.cat.manage.base.domain.SyncDirectory;
import com.cat.manage.check.domain.Check;
import com.cat.manage.check.service.CheckService;
import com.cat.manage.common.exception.BusinessException;
import com.cat.manage.selled.domain.Selled;
import com.cat.manage.selled.service.SelledService;
import com.cat.manage.shipped.domain.Shipped;
import com.cat.manage.shipped.service.ShippedService;
import com.cat.manage.store.domain.Store;
import com.cat.manage.store.service.StoreService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 同步目录服务
 * @author wanghang
 *
 */
@Service
public class SyncDirectoryService {

	@Autowired
	private SyncDirectoryDao syncDirDao;
	
	@Autowired
	private SyncDetailService syncDetailService;
	
	@Autowired
	private CheckService checkService;
	
	@Autowired
	private ShippedService shippedService;
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private SelledService selledService;
	
	@Autowired
	private SeriesService seriesService;
	
	/**
	 * 查询目录
	 * @param syncDirectory
	 * @return
	 */
	public List<SyncDirectory> querySyncDirectory(SyncDirectory syncDirectory){
		return syncDirDao.querySyncDirectory(syncDirectory);
	}
	
	/**
	 * 同步--系列
	 * @param dbseries 系列信息来自于数据库，系列合法性校验在seriesService中校验
	 */
	public Map addSync(Series dbSeries){
		Integer seriesId = dbSeries.getSeriesId();
		//查询使用该系列的所有订单信息，下单，邮寄，入库，售出
		List<Check> checkList = checkService.queryCheckForSync(seriesId, null);
		List<Shipped> shippedList = shippedService.queryShippedForSync(seriesId, null);
		List<Store> storeList = storeService.queryStoreForSync(seriesId, null);
		List<Selled> selledList = selledService.querySelledForSync(seriesId, null);
		
		//获取同步标记
		String syncFlag = this.getSyncFlag();
		
		//保存同步详情
		syncDetailService.addSyncDetailList(checkList, syncFlag);
		syncDetailService.addSyncDetailList(shippedList, syncFlag);
		syncDetailService.addSyncDetailList(storeList, syncFlag);
		syncDetailService.addSyncDetailList(selledList, syncFlag);
		
		//保存同步目录
		this.addSyncDir(dbSeries, syncFlag);
		
		//开始同步
		for(Check check : checkList)
			checkService.updateCheckForSync(check.getId(), Integer.valueOf(dbSeries.getOfOrigin()), seriesId, null);
		for(Shipped shipped : shippedList)
			shippedService.updateShippedForSync(shipped.getId(), Integer.valueOf(dbSeries.getOfOrigin()), seriesId, null);
		for(Store store : storeList)
			storeService.updateStoreForSync(store.getId(), Integer.valueOf(dbSeries.getOfOrigin()), seriesId, null);
		for(Selled selled : selledList)
			selledService.updateSelledForSync(selled.getId(), Integer.valueOf(dbSeries.getOfOrigin()), seriesId, null);
		
		Map<String, Integer> map = Maps.newHashMap();
		map.put("check", checkList.size());
		map.put("shipped", shippedList.size());
		map.put("store", storeList.size());
		map.put("selled", selledList.size());
		return map;
	}
	
	/**
	 * 同步--单品
	 * @param dbSingleproduct 单品信息来自于数据库，系列合法性校验在singleService中校验
	 */
	public Map addSync(Singleproduct dbSingleproduct){
		Integer singleId = dbSingleproduct.getSingleId();
		//查询使用该系列的所有订单信息，下单，邮寄，入库，售出
		List<Check> checkList = checkService.queryCheckForSync(null, singleId);
		List<Shipped> shippedList = shippedService.queryShippedForSync(null, singleId);
		List<Store> storeList = storeService.queryStoreForSync(null, singleId);
		List<Selled> selledList = selledService.querySelledForSync(null, singleId);
		
		//获取同步标记
		String syncFlag = this.getSyncFlag();
		
		//保存同步详情
		syncDetailService.addSyncDetailList(checkList, syncFlag);
		syncDetailService.addSyncDetailList(shippedList, syncFlag);
		syncDetailService.addSyncDetailList(storeList, syncFlag);
		syncDetailService.addSyncDetailList(selledList, syncFlag);
		
		//保存同步目录
		this.addSyncDir(dbSingleproduct, syncFlag);
		
		//开始同步
		for(Check check : checkList)
			checkService.updateCheckForSync(check.getId(), Integer.valueOf(dbSingleproduct.getBrandId()), Integer.valueOf(dbSingleproduct.getOfOrigin()), singleId);
		for(Shipped shipped : shippedList)
			shippedService.updateShippedForSync(shipped.getId(), Integer.valueOf(dbSingleproduct.getBrandId()), Integer.valueOf(dbSingleproduct.getOfOrigin()), singleId);
		for(Store store : storeList)
			storeService.updateStoreForSync(store.getId(), Integer.valueOf(dbSingleproduct.getBrandId()), Integer.valueOf(dbSingleproduct.getOfOrigin()), singleId);
		for(Selled selled : selledList)
			selledService.updateSelledForSync(selled.getId(), Integer.valueOf(dbSingleproduct.getBrandId()), Integer.valueOf(dbSingleproduct.getOfOrigin()), singleId);
		
		Map<String, Integer> map = Maps.newHashMap();
		map.put("check", checkList.size());
		map.put("shipped", shippedList.size());
		map.put("store", storeList.size());
		map.put("selled", selledList.size());
		return map;
	}
	
	/**
	 * 恢复同步 -- 系列
	 * @param series
	 * @return
	 */
	public Map recoverSync(Series series){
		//根据系列ID查询最近一次同步记录
		SyncDirectory syncDirectory = syncDirDao.querySyncDirectoryForNew(series.getSeriesId(), null);
		if(syncDirectory == null)
			throw new BusinessException("1", "系列["+series.getSeriesName()+"]没有可恢复的记录");
		
		return this.recoverSync(syncDirectory.getSyncFlag());
	}
	
	/**
	 * 恢复同步 -- 单品
	 * @param single
	 * @return
	 */
	public Map recoverSync(Singleproduct single){
		//根据单品ID查询最近一次同步记录
		SyncDirectory syncDirectory = syncDirDao.querySyncDirectoryForNew(null, single.getSingleId());
		if(syncDirectory == null)
			throw new BusinessException("1", "单品["+single.getSingleName()+"]没有可恢复的记录");
		return this.recoverSync(syncDirectory.getSyncFlag());
	}
	
	/**
	 * 恢复同步
	 * @return
	 */
	public Map recoverSync(String syncFlag){
		//查询同步标记
		List<SyncDirectory> syncDirectoryList = this.querySyncDirBySyncFlag(syncFlag);
		if(syncDirectoryList == null || syncDirectoryList.size() <= 0)
			throw new BusinessException("1", "要恢复同步的同步标记["+syncFlag+"]不存在");
		SyncDirectory syncDirectory = syncDirectoryList.get(0);
		
		//查询同步详情
		List<SyncDetail> syncDetailList = syncDetailService.querySyncDetailBySyncFlag(syncFlag);
		
		//获取要恢复的各个订单数据
		Map<String, List<?>> map = this.changeTo(syncDetailList);
		List<Check> checkList = (List<Check>) map.get("check");
		List<Shipped> shippedList = (List<Shipped>) map.get("shipped");
		List<Store> storeList = (List<Store>) map.get("store");
		List<Selled> selledList = (List<Selled>) map.get("selled");
		
		//开始恢复数据
		for(Check check : checkList)
			checkService.updateCheckForSync(check.getId(), check.getBrandId(), check.getSeriesId(), check.getSingleId());
		for(Shipped shipped : shippedList)
			shippedService.updateShippedForSync(shipped.getId(), shipped.getBrandId(), shipped.getSeriesId(), shipped.getSingleId());
		for(Store store : storeList)
			storeService.updateStoreForSync(store.getId(), store.getBrandId(), store.getSeriesId(), store.getSingleId());
		for(Selled selled : selledList)
			selledService.updateSelledForSync(selled.getId(), selled.getBrandId(), selled.getSeriesId(), selled.getSingleId());
		
		//删除同步目录
		syncDirDao.deleteSyncDirectoryBySyncFlag(syncFlag);
		
		//删除同步详情
		syncDetailService.deleteSyncDetailBySyncFlag(syncFlag);
		
		Map<String, Integer> resultMap = Maps.newHashMap();
		resultMap.put("check", checkList.size());
		resultMap.put("shipped", shippedList.size());
		resultMap.put("store", storeList.size());
		resultMap.put("selled", selledList.size());
		
		return resultMap;
	}
	
	/**
	 * 根据同步标记查询同步目录
	 * @param syncFlag
	 * @return
	 */
	public List<SyncDirectory> querySyncDirBySyncFlag(String syncFlag){
		SyncDirectory syncDir = new SyncDirectory();
		syncDir.setSyncFlag(syncFlag);
		return syncDirDao.querySyncDirectory(syncDir);
	}
	
	/**
	 * 删除同步目录
	 * @param syncFlag
	 */
	public void deleteDirectory(String syncFlag){
		syncDirDao.deleteSyncDirectoryBySyncFlag(syncFlag);
	}
	
	/**
	 * 获取目录标识
	 * @return
	 */
	private String getSyncFlag(){
		return System.currentTimeMillis()+"";
	}
	
	/**
	 * 保存同步目录 -- 系列专用
	 * @param series
	 * @param syncFlag
	 */
	private void addSyncDir(Series series, String syncFlag){
		SyncDirectory syncDirectory = new SyncDirectory();
		syncDirectory.setSeriesId(series.getSeriesId());
		syncDirectory.setSyncFlag(syncFlag);
		syncDirectory.setIsUsable("1");//该字段暂时没用
		syncDirDao.addSyncDirectory(syncDirectory);
	}
	
	/**
	 * 保存同步目录 -- 单品专用
	 * @param series
	 * @param syncFlag
	 */
	private void addSyncDir(Singleproduct single, String syncFlag){
		SyncDirectory syncDirectory = new SyncDirectory();
		syncDirectory.setSingleId(single.getSingleId());
		syncDirectory.setSyncFlag(syncFlag);
		syncDirectory.setIsUsable("1");//该字段暂时没用
		syncDirDao.addSyncDirectory(syncDirectory);
	}
	
	/**
	 * 将同步详情转换为不同订单结果
	 * @param syncDetailList
	 * @return
	 */
	public Map<String, List<?>> changeTo(List<SyncDetail> syncDetailList){
		Map<String, List<?>> map = Maps.newHashMap();
		if(syncDetailList == null || syncDetailList.size() <= 0)
			return map;
		
		List<Check> checkList = Lists.newArrayList();
		List<Shipped> shippedList = Lists.newArrayList();
		List<Store> storeList = Lists.newArrayList();
		List<Selled> selledList = Lists.newArrayList();
		
		for(SyncDetail detail : syncDetailList){
			String orderType = detail.getOrderType();
			switch(orderType){
				case SyncDetailService.CHECKTYPE:
					checkList.add(SyncDirectoryService.changeToCheck(detail));
					break;
					
				case SyncDetailService.SHIPPEDTYPE:
					shippedList.add(SyncDirectoryService.changeToShipped(detail));
					break;
					
				case SyncDetailService.STORETYPE:
					storeList.add(SyncDirectoryService.changeToStore(detail));
					break;
	
				case SyncDetailService.SELLTYPE:
					selledList.add(SyncDirectoryService.changeToSelled(detail));
					break;
			}
		}
		
		map.put("check", checkList);
		map.put("shipped", shippedList);
		map.put("store", storeList);
		map.put("selled", selledList);
		return map;
	}
	
	/**
	 * syncDetail转换check
	 * @param syncDetail
	 * @return
	 */
	public static Check changeToCheck(SyncDetail syncDetail){
		Check check = new Check();
		check.setId(syncDetail.getOrderId());
		check.setBrandId(syncDetail.getBrandId());
		check.setSeriesId(syncDetail.getSeriesId());
		check.setSingleId(syncDetail.getSingleId());
		return check;
	}
	
	/**
	 * syncDetail转换Shipped
	 * @param syncDetail
	 * @return
	 */
	public static Shipped changeToShipped(SyncDetail syncDetail){
		Shipped shipped = new Shipped();
		shipped.setId(syncDetail.getOrderId());
		shipped.setBrandId(syncDetail.getBrandId());
		shipped.setSeriesId(syncDetail.getSeriesId());
		shipped.setSingleId(syncDetail.getSingleId());
		return shipped;
	}
	
	/**
	 * syncDetail转换Store
	 * @param syncDetail
	 * @return
	 */
	public static Store changeToStore(SyncDetail syncDetail){
		Store store = new Store();
		store.setId(syncDetail.getOrderId());
		store.setBrandId(syncDetail.getBrandId());
		store.setSeriesId(syncDetail.getSeriesId());
		store.setSingleId(syncDetail.getSingleId());
		return store;
	}
	
	/**
	 * syncDetail转换Selled
	 * @param syncDetail
	 * @return
	 */
	public static Selled changeToSelled(SyncDetail syncDetail){
		Selled selled = new Selled();
		selled.setId(syncDetail.getOrderId());
		selled.setBrandId(syncDetail.getBrandId());
		selled.setSeriesId(syncDetail.getSeriesId());
		selled.setSingleId(syncDetail.getSingleId());
		return selled;
	}
	
}
