package com.cat.manage.tool;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 调度器工具类
 * @author 王航
 * @date 2015年12月22日 下午1:53:16
 */
public class SchedulerUtil {
	private static SchedulerFactory sf = new StdSchedulerFactory();
	private static Scheduler scheduler = null;
	
	synchronized public static Scheduler getScheduler(){
		if(scheduler == null)
			try {
				scheduler = sf.getScheduler();
			} catch (SchedulerException e) {
				LOG.error("创建调度器时错误,原因:"+e);
			}
		return scheduler;
	}
	
	private static final Logger LOG = LoggerFactory.getLogger(Scheduler.class);

}
