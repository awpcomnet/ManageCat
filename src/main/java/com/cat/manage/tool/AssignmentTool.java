package com.cat.manage.tool;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.cat.manage.base.domain.Habit;
import com.google.common.collect.Maps;

/**
 * 任务队列工具
 * @author wanghang
 *
 */
public class AssignmentTool {
	public static final String HABITADD = "ADDHABIT";//添加偏好
	public static final String HABITMODIFY = "MODIFYHABIT";//修改偏好
	public static final String HABITDELETE = "DELETEHABIT";//删除偏好
	
	private static ConcurrentLinkedQueue<Map<String, Habit>> jobQueue = new ConcurrentLinkedQueue<Map<String, Habit>>();
	
	/**
	 * 添加偏好信息到队列
	 * @param habit
	 */
	public static void addHabitJob(Habit habit){
		Map<String, Habit> map = Maps.newHashMap();
		map.put(HABITADD, habit);
		jobQueue.add(map);
	}
	
	/**
	 * 修改偏好信息
	 * @param habit
	 */
	public static void updateHabitJob(Habit habit){
		Map<String, Habit> map = Maps.newHashMap();
		map.put(HABITMODIFY, habit);
		jobQueue.add(map);
	}
	
	/**
	 * 删除偏好信息
	 * @param habit
	 */
	public static void deleteHabitJob(Habit habit){
		Map<String, Habit> map = Maps.newHashMap();
		map.put(HABITDELETE, habit);
		jobQueue.add(map);
	}
	
	/**
	 *  获取队列中的偏好信息
	 * @return
	 */
	public synchronized static Map<String, Habit> getHabitJob(){
		return jobQueue.poll();
	}
	
	/**
	 * 返回是否还有偏好任务
	 * true -有
	 * false -无
	 * @return
	 */
	public synchronized static boolean isHaveHabitJob(){
		return !jobQueue.isEmpty();
	}
}
