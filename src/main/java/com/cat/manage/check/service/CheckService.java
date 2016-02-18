package com.cat.manage.check.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.check.dao.CheckDao;
import com.cat.manage.check.domain.Check;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 下单清单服务
 * @author wanghang
 *
 */
@Service
public class CheckService {
	@Autowired
	private CheckDao checkDao;
	
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
	 * 删除一条下单清单
	 * @param id
	 */
	public void  deleteCheck(Integer id){
		//删除下单清单记录
		checkDao.deleteCheck(id);
		//删除邮寄清单记录
		//TODO
		//删除入库清单记录
		
		//删除售出清单记录
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
}
