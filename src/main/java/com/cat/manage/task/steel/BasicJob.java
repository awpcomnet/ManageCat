package com.cat.manage.task.steel;

import java.io.Serializable;

import org.quartz.Job;

/**
 * @Description: 定时任务的基础类，任务需要继承该类
 * @author 王航
 * @date 2015年12月21日 下午5:04:05
 */
public abstract class BasicJob implements Job, Serializable{
	//定时任务频率
	private String frequency;
	
	//定时任务默认启停
	private String defaultSwitch;
	
	//任务名称
	private String jobName;
	
	//任务组别
	private String jobGroup;
	
	//任务描述
	private String description;
	
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getDefaultSwitch() {
		return defaultSwitch;
	}

	public void setDefaultSwitch(String defaultSwitch) {
		this.defaultSwitch = defaultSwitch;
	}

	@Override
	public String toString() {
		return "BasicJob [frequency=" + frequency + ", defaultSwitch="
				+ defaultSwitch + ", jobName=" + jobName + ", jobGroup="
				+ jobGroup + ", description=" + description + "]";
	}

	private static final long serialVersionUID = 5370895821377474226L;
}
