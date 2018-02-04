package com.cat.manage.check.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.check.dao.CheckDao;
import com.cat.manage.check.domain.Check;
import com.cat.manage.common.exception.BusinessException;
import com.cat.manage.selled.domain.Selled;
import com.cat.manage.selled.service.SelledService;
import com.cat.manage.shipped.domain.Shipped;
import com.cat.manage.shipped.service.ShippedService;
import com.cat.manage.store.domain.Store;
import com.cat.manage.store.service.StoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;

/**
 * 下单清单服务
 * @author wanghang
 *
 */
@Service
public class CheckService {
	@Autowired
	private CheckDao checkDao;
	
	@Autowired
	private ShippedService shippedService;
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private SelledService selledService;
	
	/**
	 * 添加一条下单清单
	 */
	public void addCheck(Check check){
		checkDao.addCheck(check);
	}
	
	/**
	 * 修改一条下单清单
	 * @param check
	 */
	public void updateCheck(Check check){
		checkDao.updateCheck(check);
	}
	
	/**
	 * 根据下单清单唯一编号修改订单状态
	 * @param ids
	 * @param orderStatus
	 */
	public void updateCheckForStatus(Integer[] ids, String orderStatus){
		checkDao.updateCheckForStatus(ids, orderStatus);
	}
	
	/**
	 * 删除一条下单清单
	 * @param id
	 */
	public void  deleteCheck(Integer id){
		//删除下单清单记录
		checkDao.deleteCheck(id);
		
		//删除邮寄清单记录
		shippedService.deleteShippedByCheckId(id);
		
		//删除入库清单记录
		storeService.deleteStoreByCheckId(id);

		//删除售出清单记录
		selledService.deleteSelledByCheckId(id);
	}
	
	/**
	 * 分页查询下单清单记录
	 * @param check
	 * @param startTime
	 * @param endTime
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Check> queryCheck(Check check, String startTime, String endTime, Integer pageNum, Integer pageSize){
		PageHelper.startPage(pageNum, pageSize);
		List<Check> list = checkDao.queryCheck(check, startTime, endTime);
		PageInfo<Check> page = new PageInfo<Check>(list);
		return page;
	}
	
	/**
	 * 
	 * @param check
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Check> queryCheckForList(Check check, String startTime, String endTime){
		return checkDao.queryCheck(check, startTime, endTime);
	}
	
	/**
	 * 根据下单清单唯一编号查询记录(多条)
	 * @param ids
	 * @return
	 */
	public List<Check> queryCheckByIds(Integer[] ids){
		return checkDao.queryCheckByIds(ids);
	}
	
	
	/**
	 * 根据下单清单唯一编号查询记录
	 * @param id
	 * @return
	 */
	public Check queryCheckById(Integer id){
		return checkDao.queryCheckById(id);
	}
	
	/**
	 * 查询下单清单，仅系列/单品 同步时使用
	 * @param seriesId
	 * @param singleId
	 * @return
	 */
	public List<Check> queryCheckForSync(Integer seriesId, Integer singleId){
		if(seriesId == null && singleId == null)
			return null;
		if(seriesId != null && singleId != null)
			return null;
		return checkDao.queryCheckForSync(seriesId, singleId);
	}
	
	/**
	 * 修改下单清单的 品牌，系列，单品， 仅系列/单品 同步时使用
	 * @param id
	 * @param brandId
	 * @param seriesId
	 * @param singleId
	 */
	public void updateCheckForSync(Integer id, Integer brandId, Integer seriesId, Integer singleId){
		Check check = new Check();
		check.setId(id);
		check.setBrandId(brandId);
		check.setSeriesId(seriesId);
		check.setSingleId(singleId);
		checkDao.updateCheck(check);
	}
	
	/**
	 * 强制修改下单清单的信息，不校验当前状态
	 * @param check
	 */
	public String updateCheckForce(Check check){
		if(check.getNum().intValue() <= 0)
			throw new BusinessException("1", "下单数量非法["+check.getNum()+"]");
		
		//获取数据库中数据
		Check dbCheck = checkDao.queryCheckById(check.getId());
		if(dbCheck == null)
			throw new BusinessException("1", "强制修改的下单清单内容不存在");
		
		StringBuffer tip = new StringBuffer("影响的清单有[下单清单]");
		//判断是否需要修改其他表
		boolean isChangeShipped = this.isChangeShipped(dbCheck, check);
		boolean isChangeStore = this.isChangeStore(dbCheck, check);
		boolean isChangeSelled = this.isChangeSelled(dbCheck, check);
		
		String checkOrderStatus = "";
		int storeN = 0;//已入库数量
		int sellN = 0;//已售出数量
		int buyN = check.getNum();//购买数量
		
		//修改 邮寄清单
		if(isChangeShipped){
			//查询邮寄清单
			Shipped newShipped = this.changeToShipped(check);
			Shipped oldShipped = shippedService.queryShippedByCheckId(check.getId());
			if(oldShipped != null){
				int storeNum = oldShipped.getStoreNum();//入库数量
				if(check.getNum().intValue() < storeNum){
					throw new BusinessException("1", "下单清单购买的数量["+check.getNum()+"]小于已入库的数量["+storeNum+"]");
				} else if(check.getNum().intValue() > storeNum && storeNum > 0){
					newShipped.setShippedStatus("7");//部分入库
				} else if(check.getNum().intValue() > storeNum && storeNum == 0){
					newShipped.setShippedStatus("1");//已邮寄
				} else if(check.getNum().intValue() == storeNum && storeNum > 0){
					newShipped.setShippedStatus("2");//已入库
				} 
					
				shippedService.updateShippedByCheckId(newShipped);
				tip.append("[邮寄清单]");
			}
				
			
		}
		
		//修改 库存清单
		if(isChangeStore){
			Store newStore = this.changeToStore(check);
			List<Store> oldStoreList = storeService.queryStoreByCheckIds(new Integer[]{check.getId()});
			if(oldStoreList != null && oldStoreList.size() == 1){
				Store oldStore = oldStoreList.get(0);
				int sellNum = oldStore.getSellNum();
				int storeNum = oldStore.getNum();//已入库数量
				sellN = sellNum;
				storeN = storeNum;
				if(check.getNum().intValue() < sellNum){
					throw new BusinessException("1", "下单清单购买的数量["+check.getNum()+"]小于已售出的数量["+sellNum+"]");
				} else if(check.getNum().intValue() < storeNum){
					throw new BusinessException("1", "下单清单购买的数量["+check.getNum()+"]小于已入库的数量["+storeNum+"]");
				} 
				
		        double unitPostage = Double.parseDouble(oldStore.getUnitPostage());//实际邮费
		        double unitPrice = check.getUnitPrice();//下单单价（美元单位）
		        double rate = check.getRate();//汇率
		        double unitRmb = unitPrice * rate;//实际单价
		        double unitCost = unitRmb + unitPostage;//实际成本
		        newStore.setUnitRmb(String.format("%.2f", unitRmb));
		        newStore.setUnitCost(String.format("%.2f", unitCost));
		        
				storeService.updateStoreByCheckId(newStore);
				tip.append("[入库清单]");
			} else if(oldStoreList != null && oldStoreList.size() >= 1){
				throw new BusinessException("1", "入库清单异常，下单清单唯一编号["+check.getId()+"]存在["+oldStoreList.size()+"]条入库记录");
			}
		}
		
		//修改 售出清单
		if(isChangeSelled){
			Selled newSelled = this.changeToSelled(check);
			List<Selled> oldSelledList = selledService.queryStoreByCheckIds(new Integer[]{check.getId()});
			if(oldSelledList != null && oldSelledList.size() >= 1){
				Selled oldSelled = oldSelledList.get(0);
				
				double unitPostage = oldSelled.getUnitPostage();//实际邮费
		        double unitPrice = check.getUnitPrice();//下单单价（美元单位）
		        double rate = check.getRate();//汇率
		        double unitRmb = unitPrice * rate;//实际单价
		        double unitCost = unitRmb + unitPostage;//实际成本
		        newSelled.setUnitRmb(Double.parseDouble(String.format("%.2f", unitRmb)));
		        newSelled.setUnitCost(Double.parseDouble(String.format("%.2f", unitCost)));
				
				selledService.updateSelledByCheckId(newSelled);
				tip.append("[售出清单]");
			}
		}
		
		//修改 下单清单
		if(buyN > storeN && storeN > 0){
			checkOrderStatus = "7";//部分入库
		}else if(buyN == sellN && sellN > 0){
			checkOrderStatus = "3";//已售罄
		}else if(buyN == storeN && storeN > 0 && sellN > 0){
			checkOrderStatus = "4";//销售中
		}else if(buyN == storeN && storeN > 0 && sellN == 0){
			checkOrderStatus = "2";//已入库
		}
		if(Strings.isNullOrEmpty(checkOrderStatus)){
			check.setOrderStatus("");//不修改状态
		}else{
			check.setOrderStatus(checkOrderStatus);
		}
		checkDao.updateCheck(check);
		
		return tip.toString();
	}
	
	/**
	 * 将下单清单转换为邮寄清单
	 * @param check
	 * @return
	 */
	private Shipped changeToShipped(Check check){
		Shipped shipped = new Shipped();
		shipped.setCheckId(check.getId());
		shipped.setTrackingNumber(check.getTrackingNumber());
		shipped.setTransferCompany(check.getTransferCompany());
		shipped.setBrandId(check.getBrandId());
		shipped.setSeriesId(check.getSeriesId());
		shipped.setSingleId(check.getSingleId());
		shipped.setPayby(check.getPayby());
		shipped.setUnitPrice(check.getUnitPrice());
		shipped.setNum(check.getNum());
		return shipped;
	}
	
	/**
	 * 将下单清单转换为入库清单
	 * @param check
	 * @return
	 */
	private Store changeToStore(Check check){
		Store store = new Store();
		store.setCheckId(check.getId());
		store.setTrackingNumber(check.getTrackingNumber());
		store.setTransferCompany(check.getTransferCompany());
		store.setBrandId(check.getBrandId());
		store.setSeriesId(check.getSeriesId());
		store.setSingleId(check.getSingleId());
		store.setPayby(check.getPayby());
		store.setUnitPrice(check.getUnitPrice()+"");
		return store;
	}
	
	/**
	 * 将下单清单转换为售出清单
	 * @param check
	 * @return
	 */
	private Selled changeToSelled(Check check){
		Selled selled = new Selled();
		selled.setCheckId(check.getId());
		selled.setBrandId(check.getBrandId());
		selled.setSeriesId(check.getSeriesId());
		selled.setSingleId(check.getSingleId());
		selled.setPayby(check.getPayby());
		selled.setUnitPrice(check.getUnitPrice()+"");
		return selled;
	}
	
	/**
	 * 是否需要修改邮寄清单
	 * @param dbCheck
	 * @param newCheck
	 * @return
	 */
	private boolean isChangeShipped(Check dbCheck, Check newCheck){
		if(!dbCheck.getTrackingNumber().equals(newCheck.getTrackingNumber()))
			return true;
		if(!dbCheck.getTransferCompany().equals(newCheck.getTransferCompany()))
			return true;
		if(dbCheck.getBrandId().intValue() != newCheck.getBrandId().intValue())
			return true;
		if(dbCheck.getSeriesId().intValue() != newCheck.getSeriesId().intValue())
			return true;
		if(dbCheck.getSingleId().intValue() != newCheck.getSingleId().intValue())
			return true;
		if(dbCheck.getNum().intValue() != newCheck.getNum().intValue())
			return true;
		if(dbCheck.getUnitPrice().doubleValue() != newCheck.getUnitPrice().doubleValue()){
			return true;
		}
		if(!dbCheck.getPayby().equals(newCheck.getPayby()))
			return true;
		
		return false;
	}
	
	/**
	 * 是否需要修改入库清单
	 * @param dbCheck
	 * @param newCheck
	 * @return
	 */
	public boolean isChangeStore(Check dbCheck, Check newCheck){
		if(!dbCheck.getTrackingNumber().equals(newCheck.getTrackingNumber()))
			return true;
		if(!dbCheck.getTransferCompany().equals(newCheck.getTransferCompany()))
			return true;
		if(dbCheck.getBrandId().intValue() != newCheck.getBrandId().intValue())
			return true;
		if(dbCheck.getSeriesId().intValue() != newCheck.getSeriesId().intValue())
			return true;
		if(dbCheck.getSingleId().intValue() != newCheck.getSingleId().intValue())
			return true;
		if(dbCheck.getUnitPrice().doubleValue() != newCheck.getUnitPrice().doubleValue())
			return true;
		if(!dbCheck.getPayby().equals(newCheck.getPayby()))
			return true;
		if(dbCheck.getRate() != newCheck.getRate())
			return true;
		return false;
	}
	
	/**
	 * 是否需要修改售出清单
	 * @param dbCheck
	 * @param newCheck
	 * @return
	 */
	public boolean isChangeSelled(Check dbCheck, Check newCheck){
		if(dbCheck.getBrandId().intValue() != newCheck.getBrandId().intValue())
			return true;
		if(dbCheck.getSeriesId().intValue() != newCheck.getSeriesId().intValue())
			return true;
		if(dbCheck.getSingleId().intValue() != newCheck.getSingleId().intValue())
			return true;
		if(dbCheck.getUnitPrice().doubleValue() != newCheck.getUnitPrice().doubleValue())
			return true;
		if(!dbCheck.getPayby().equals(newCheck.getPayby()))
			return true;
		if(dbCheck.getRate() != newCheck.getRate())
			return true;
		return false;
	}
}
