package com.cat.manage.base.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.manage.base.dao.HabitDao;
import com.cat.manage.base.domain.Habit;
import com.cat.manage.selled.domain.Selled;
import com.cat.manage.user.domain.User;

/**
 * 偏好服务
 * @author wanghang
 *
 */
@Service
public class HabitService {
	public static final String TYPESINGLE = "singleproduct";
	
	@Autowired
	private HabitDao habitDao;
	
	/**
	 * 添加偏好信息
	 * @param habit
	 */
	public void addHabit(Habit habit){
		habitDao.addHabit(habit);
	}
	
	/**
	 * 添加/修改 单品偏好信息
	 * @param single
	 */
	public void addHabit(Selled selled, User user){
		Habit habit = new Habit();
		habit.setType(TYPESINGLE);
		habit.setHabitKey(selled.getSingleId()+"");
		habit.setHabitValue(selled.getSellingPrice()+"");
		habit.setUserId(user.getUserId()+"");
		
		List<Habit> list = this.queryHabit(habit);
		if(list == null || list.size() <= 0){
			this.addHabit(habit);
		} else if(list.size() == 1) {
			Habit temp = list.get(0);
			habit.setId(temp.getId());
			this.updateHabit(habit);
		}
	}
	
	/**
	 * 修改偏好信息
	 * @param habit
	 */
	public void updateHabit(Habit habit){
		habitDao.updateHabit(habit);
	}
	
	/**
	 * 删除偏好信息
	 * @param id
	 */
	public void deleteHabit(Integer id){
		habitDao.deleteHabit(id);
	}
	
	/**
	 * 查询偏好信息
	 * @param habit
	 * @return
	 */
	public List<Habit> queryHabit(Habit habit){
		return habitDao.queryHabit(habit);
	}
}
