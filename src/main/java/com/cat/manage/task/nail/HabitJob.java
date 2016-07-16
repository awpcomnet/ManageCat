package com.cat.manage.task.nail;

import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.cat.manage.base.domain.Habit;
import com.cat.manage.base.service.HabitService;
import com.cat.manage.task.steel.BasicJob;
import com.cat.manage.tool.AssignmentTool;
import com.google.common.collect.Maps;

/**
 * 偏好任务
 * @author wanghang
 *
 */
public class HabitJob extends BasicJob{
	private static final long SLEEPTIME = 2000;
	
	@Autowired
	private HabitService habitService;

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Map<String, Habit> map = Maps.newHashMap();
		System.out.println("偏好定时任务被执行!!!!!!");
		while(AssignmentTool.isHaveHabitJob()){
			System.out.println("偏好有任务！！！有任务！！！！！");
			map = AssignmentTool.getHabitJob();
			if(map == null){
				continue;
			}
			
			if(map.containsKey(AssignmentTool.HABITADD)){//添加
				habitService.addHabit(map.get(AssignmentTool.HABITADD));
			} else if(map.containsKey(AssignmentTool.HABITMODIFY)){//修改
				habitService.updateHabit(map.get(AssignmentTool.HABITMODIFY));
			} else if(map.containsKey(AssignmentTool.HABITDELETE)){//删除
				Habit habit = map.get(AssignmentTool.HABITDELETE);
				habitService.deleteHabit(habit.getId());
			} else {
				continue;
			}
		}
		System.out.println("偏好没有任务！！！没有任务！！！！！");
		
	}

	private static final long serialVersionUID = -8249337546354373948L;
}
