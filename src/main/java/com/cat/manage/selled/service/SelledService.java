package com.cat.manage.selled.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.check.domain.Check;
import com.cat.manage.check.service.CheckService;
import com.cat.manage.common.exception.BusinessException;
import com.cat.manage.selled.dao.SelledDao;
import com.cat.manage.selled.domain.Selled;
import com.cat.manage.store.domain.Store;
import com.cat.manage.store.service.StoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;

/**
 * @Description: 售出清单服务类
 * @author 王航
 * @date 2016年2月23日 下午3:01:39
 */
@Service
public class SelledService {
	@Autowired
	private SelledDao selledDao;
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private CheckService checkService;
	
	/**
	 * 添加售出记录
	 * selled参数中必须包含 storeId(入库唯一编号)
	 * 检查仓库剩余数量
	 * 修改仓库记录，售出数量。售出数量等于入库数量时，修改状态为 [3已售出],同时下单清单状态修改为[3已售出]
	 * @param selled
	 */
	public void addSelled(Selled selled){
		Integer storeId = selled.getStoreId();
		Store store = storeService.queryStoreById(storeId);
		if(store == null)
			throw new BusinessException("1", "仓库记录不存在");
		if("3".equals(store.getStoreStatus()))
			throw new BusinessException("1", "["+store.getSingleName()+"]已售完");
		
		Integer storeNum = store.getNum();//入库数量
		Integer storeSelledNum = store.getSellNum();//已售数量
		Integer sellNum = selled.getSellNum();//售出数量
		if(storeNum - storeSelledNum < sellNum)
			throw new BusinessException("1", "销售["+sellNum+"]个，库存["+(storeNum - storeSelledNum)+"]个，库存不足");
		
		//添加售出记录
		selled.setSelledStatus("3");//3已售出
		if(Strings.isNullOrEmpty(selled.getRemark()))
			selled.setRemark(store.getRemark());//如果未填写注释，则默认入库时备注
		selledDao.addSelled(selled, store);
		
		//更新仓库记录
		storeSelledNum += sellNum;
		if(storeSelledNum == storeNum){
			store.setStoreStatus("3");//已售出
		} else if(storeSelledNum > 0){
			store.setStoreStatus("4");//销售中
		}
		store.setSellNum(storeSelledNum);//设置售出数量
		storeService.updateStore(store);
		
		//查询下单清单
		Integer checkId = store.getCheckId();
		Check check = checkService.queryCheckById(checkId);
		if(check != null){
			if(storeSelledNum == storeNum){
				checkService.updateCheckForStatus(new Integer[]{checkId}, "3");//3已售出
			} else if(storeSelledNum > 0){
				checkService.updateCheckForStatus(new Integer[]{checkId}, "4");//销售中
			}
		}
		
	}
	
	/**
	 * 查询售出记录
	 * @param selled
	 * @param startTime
	 * @param endTime
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Selled> querySelledForPage(Selled selled, String startTime, String endTime, Integer pageNum, Integer pageSize){
		PageHelper.startPage(pageNum, pageSize);
		List<Selled> list = selledDao.querySelled(selled, startTime, endTime);
		PageInfo<Selled> page = new PageInfo<Selled>(list);
		return page;
	}
	
	/**
	 * 修改售出记录
	 * @param selled
	 */
	public void updateSelled(Selled selled){
		selledDao.updateSelled(selled);
	}
	
	/**
	 * 根据下单清单唯一编号删除售出记录
	 * @param checkId
	 */
	public void deleteSelledByCheckId(Integer checkId){
		selledDao.deleteSelledByCheckId(checkId);
	}
	
	/**
	 * 根据售出清单唯一编号删除记录
	 * 1.售出记录删除
	 * 2.入库记录中 售出数目 修改。修改入库记录状态  当售出数目为0时修改为[2已入库] 当售出数据小于入库数目时[4销售中] 当售出数目等于入库数目时[3已售出]
	 * @param id
	 */
	public void deleteSelled(Integer id){
		//下单清单状态
		String checkStatus = null;//[已入库]
		
		//查询售出记录
		Selled selled = selledDao.querySelledById(id);
		if(selled == null)
			throw new BusinessException("1", "售出记录不存在");
		
		Integer storeId = selled.getStoreId();
		Store store = storeService.queryStoreById(storeId);
		if(store == null)
			throw new BusinessException("1", "仓库记录不存在");
		
		Integer selledNum = selled.getSellNum();//售出清单中售出数目
		Integer storeNum = store.getNum();//入库数目
		Integer storeSellNum = store.getSellNum();//已售出数目
		
		if(storeSellNum < selledNum)
			throw new BusinessException("1", "售出数目与仓库售出数目不匹配，售出清单数目["+selledNum+"]，仓库售出记录数目["+storeSellNum+"]");
		
		Integer storeSellNumResult = storeSellNum - selledNum;//计算仓库售出记录数目
		if(storeSellNumResult == 0){//售出数目为0 [2已入库]
			store.setStoreStatus("2");
			checkStatus = "2";
		} else if(storeNum > storeSellNumResult){//售出数目小于入库数目 [4销售中]
			store.setStoreStatus("4");
			checkStatus = "4";
		} else if(storeNum == storeSellNumResult){//售出数目等于入库数目 [3已售出]
			store.setStoreStatus("3");
			checkStatus = "3";
		} else if(storeNum < storeSellNumResult){
			throw new BusinessException("1", "入库数目与仓库售出记录不匹配，入库数目["+storeNum+"]，售出数目["+storeSellNumResult+"]");
		}
		
		//设置售出数目
		store.setSellNum(storeSellNumResult);
		
		//修改入库记录
		storeService.updateStore(store);
		
		//删除售出记录
		selledDao.deleteSelledById(id);
		
		//修改下单清单状态
		checkService.updateCheckForStatus(new Integer[]{selled.getCheckId()}, checkStatus);
	}
	
	/**
	 * 更新售出清单为 补损 
	 * 如果补损金额为0 修改状态为[3已售出],金额大于0 修改状态为[5已售出（补损）]
	 * @param selled
	 */
	public void updateSelledForRefund(Selled selled){
		Double refund = selled.getRefund();
		
		if(refund == null){
			refund = 0.0;
			selled.setRefund(refund);
		}
		
		if(refund > 0){
			//设置状态为[5已售出（补损）]
			selled.setSelledStatus("5");
		} else if(refund == 0){
			//设置状态为[3已售出]
			selled.setSelledStatus("3");
		} else {
			throw new BusinessException("1", "补损金额不能为负值。当前金额为["+refund+"]");
		}
		
		selledDao.updateSelledForStatus(selled);
	}
	

	/**
	 * 将损坏的商品移动至售出清单中(已损坏状态)
	 * @param Selled
	 */
	public void addStoreForDestroy(Selled selled){
		Integer storeId = selled.getStoreId();
		Integer selledNum = selled.getSellNum();
		if(selledNum <= 0)
			return;
		
		//查询仓库信息
		Store store = storeService.queryStoreById(storeId);
		if(store == null)
			throw new BusinessException("1", "仓库信息不存在");
		if("3".equals(store.getStoreStatus()))
			throw new BusinessException("1", "["+store.getSingleName()+"]已售完");
		
		Integer storeNum = store.getNum();
		Integer storeSelledNum = store.getSellNum();
		
		if(storeNum - storeSelledNum < selledNum)
			throw new BusinessException("1", "损坏数目超过现存数目，仓库剩余数量["+(storeNum - storeSelledNum)+"]，损坏数目["+selledNum+"]");
		
		//添加售出记录
		selled.setSelledStatus("98");//98已损坏
		selled.setSellingPrice(0.0);//售价
		selledDao.addSelled(selled, store);
		
		//修改仓库记录
		storeSelledNum += selledNum;
		if(storeSelledNum == storeNum){
			store.setStoreStatus("3");//已售出
		} else if(storeSelledNum > 0){
			store.setStoreStatus("4");//销售中
		}
		store.setSellNum(storeSelledNum);//设置售出数量
		storeService.updateStore(store);
		
		//查询下单清单
		Integer checkId = store.getCheckId();
		Check check = checkService.queryCheckById(checkId);
		if(check != null){
			checkService.updateCheckForStatus(new Integer[]{checkId}, "3");//3已售出
		}
	}
	
	/**
	 * 根据下单清单唯一编号（多个）查询售出记录
	 * @param checkIds
	 * @return
	 */
	public List<Selled> queryStoreByCheckIds(Integer[] checkIds){
		return selledDao.querySelledByCheckIds(checkIds);
	}
	
	/**
	 * 查询时间段内售出记录
	 * @param selled
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Selled> querySelledForTimeQuantum(Selled selled, String startTime, String endTime){
		return selledDao.querySelled(selled, startTime, endTime);
	}
}
