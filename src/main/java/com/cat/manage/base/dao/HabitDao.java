package com.cat.manage.base.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cat.manage.base.domain.Habit;
/**
 * 偏好Dao
 * @author wanghang
 *
 */
@Repository
public interface HabitDao {
	/**
	 * 添加偏好
	 * @param habit
	 */
	public void addHabit(Habit habit);
	
	/**
	 * 修改偏好信息
	 * @param habit
	 */
	public void updateHabit(Habit habit);
	
	/**
	 * 删除偏好信息
	 * @param id
	 */
	public void deleteHabit(Integer id);
	
	/**
	 * 查询偏好信息
	 * @param habit
	 * @return
	 */
	public List<Habit> queryHabit(Habit habit);
}
