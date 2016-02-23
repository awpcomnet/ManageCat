package com.cat.manage.selled.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.common.exception.BusinessException;
import com.cat.manage.selled.dao.SelledDao;
import com.cat.manage.selled.domain.Selled;
import com.cat.manage.store.domain.Store;
import com.cat.manage.store.service.StoreService;
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
	
	/**
	 * 添加售出记录
	 * selled参数中必须包含 storeId(入库唯一编号)
	 * 检查仓库剩余数量
	 * 修改仓库记录，售出数量。售出数量等于入库数量时，修改状态为 [3已售出]
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
		
	}
}
