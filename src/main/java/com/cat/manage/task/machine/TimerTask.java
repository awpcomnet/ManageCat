package com.cat.manage.task.machine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cat.manage.task.box.JobBox;
import com.cat.manage.tool.SchedulerUtil;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class TimerTask implements ServletContextListener{
    private Scheduler scheduler = SchedulerUtil.getScheduler();

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			run(event);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			LOG.error("定时任务错误", e);
		} catch (SecurityException e) {
			e.printStackTrace();
			LOG.error("定时任务错误", e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			LOG.error("定时任务错误", e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			LOG.error("定时任务错误", e);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			LOG.error("定时任务错误", e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			LOG.error("定时任务错误", e);
		} catch (SchedulerException e) {
			e.printStackTrace();
			LOG.error("定时任务错误", e);
		}
		
	}
	
	//定时任务启动
	private void run(ServletContextEvent event) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, SchedulerException{
		LOG.info("-----------读取定时任务配置------------");
		/*----------获取springContext-----------*/
		ServletContext webContext = event.getServletContext();
		WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(webContext);
		
		JobBox timer = (JobBox) springContext.getBean("jobBox");
		if(!timer.isState()){
			LOG.info("-----------定时任务未启动------------");
			return;
		}
		
		
		/*----------获取定时任务列表bean------------------*/
		LOG.info("-----------读取定时任务列表------------");
		List<Map> parameterList = getJobList(springContext);
		
		/*----------添加任务到调度器------------------------*/
		LOG.info("-----------添加任务到调度器------------");
		setScheduler(parameterList);
		
		/*----------启动调度器------------------------*/
		LOG.info("-----------启动调度器------------");
		scheduler.start();
	}
	
	//添加到调度器
	private void setScheduler(List<Map> parameterList) throws SchedulerException{
		scheduler.clear();
		if(parameterList == null || parameterList.size() <= 0)
			return;
		for(int i=0,len=parameterList.size(); i<len; i++){
			Map map = parameterList.get(i);
			setJobAndTrigger(map);
		}
	}
	
	//设置任务和触发器
	private void setJobAndTrigger(Map map) throws SchedulerException{
		String[] frequencyArray = ((String) map.get("frequency")).split("\\|");
		String[] defaultSwitch = ((String) map.get("defaultSwitch")).split("\\|"); 
		String jobName = (String) map.get("jobName");
		String jobGroup = (String) map.get("jobGroup");
		String description = (String) map.get("description");
		Class jobClass = (Class) map.get("jobClass");
		Map<String, Object> switchMap = this.getdefaultSwitch(defaultSwitch);
		
		JobDetail job = JobBuilder.newJob(jobClass)
				.withDescription(description)
				.withIdentity(jobName, jobGroup)
				.storeDurably(true)
				.build();
		
		scheduler.addJob(job, false);
		
		for(int i=0,len=frequencyArray.length; i<len; i++){
			String frequency = frequencyArray[i];
			ScheduleBuilder<CronTrigger> scheduleBuilder = CronScheduleBuilder.cronSchedule(frequency).withMisfireHandlingInstructionDoNothing();
			CronTrigger trigger  = TriggerBuilder.newTrigger()
					.withIdentity(jobName+"Trigger_"+i, jobGroup+"Trigger")
					.forJob(job.getKey())
					.withSchedule(scheduleBuilder)
					.build();
			
			scheduler.scheduleJob(trigger);
			
			if("true".equalsIgnoreCase((String)switchMap.get(i+"")) || "true".equals((String)switchMap.get("default"))){
				LOG.info("任务["+jobName+"],触发器["+trigger.getKey().getName()+"] 设置为启动");
			} else {
				scheduler.pauseTrigger(trigger.getKey());
				LOG.info("任务["+jobName+"],触发器["+trigger.getKey().getName()+"] 设置为暂停");
			}
		}
		
	}
	
	//获取定时任务列表
	private List<Map> getJobList(WebApplicationContext springContext) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException{
		List<Map> parameterList = Lists.newArrayList();
		JobBox timer = (JobBox) springContext.getBean("jobBox");
		List<Job> jobList = timer.getJobList();
		if(jobList != null && jobList.size() > 0){
			LOG.info("-----------检测到"+jobList.size()+"个定时任务------------");
			for(int i=0; i<jobList.size(); i++){
				Job job = jobList.get(i);
				Map parameters = this.getParameters(job);
				if(parameters == null)
					LOG.info("#>>>定时任务["+job.getClass().getName()+"]参数配置缺失,任务将不能正常启动！");
				parameters.put("jobClass", job.getClass());
				parameterList.add(parameters);
			}
		} else {
			LOG.info("-----------未检测到定时任务------------");
		}
		return parameterList;
	}
	
	//获取配置参数
	private Map<String, Object> getParameters(Job job) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Map<String, Object> map = Maps.newHashMap();
		Class<? extends Job> clazz = job.getClass();
		Method method =  clazz.getMethod("getFrequency", (Class<?>[])null);
		String frequency =  (String)method.invoke(job, (Object[])null);
		method = clazz.getMethod("getDefaultSwitch", (Class<?>[])null);
		String defaultSwitch = (String)method.invoke(job, (Object[])null);
		method = clazz.getMethod("getJobName", (Class<?>[])null);
		String jobName = (String) method.invoke(job, (Object[])null);
		method = clazz.getMethod("getJobGroup", (Class<?>[])null);
		String jobGroup = (String) method.invoke(job, (Object[])null);
		method = clazz.getMethod("getDescription", (Class<?>[])null);
		String description = (String) method.invoke(job, (Object[])null);
		
		if(Strings.isNullOrEmpty(frequency) || Strings.isNullOrEmpty(jobName) || Strings.isNullOrEmpty(jobGroup) || Strings.isNullOrEmpty(description))
			return null;
		
		map.put("frequency", frequency);
		map.put("defaultSwitch", defaultSwitch);
		map.put("jobName", jobName);
		map.put("jobGroup", jobGroup);
		map.put("description", description);
		return map;
		
	}
	
	//处理开关
	private Map<String, Object> getdefaultSwitch(String[] defaultSwitch){
		Map<String, Object> map = Maps.newHashMap();
		for(String ds : defaultSwitch){
			String[] dsArray = ds.split(":");
			if(dsArray.length == 2)
				map.put(dsArray[0], dsArray[1]);
		}
		return map;
	}
	
	private static final Logger LOG = LoggerFactory.getLogger(TimerTask.class);
}
