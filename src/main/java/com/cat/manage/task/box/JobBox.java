package com.cat.manage.task.box;

import java.io.Serializable;
import java.util.List;

import org.quartz.Job;

/**
 * @Description: 定时任务列表类
 * @author 王航
 * @date 2015年12月21日 下午3:50:11
 */
public class JobBox implements Serializable{
	private List<Job> jobList;
	private boolean state;

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public List<Job> getJobList() {
		return jobList;
	}

	public void setJobList(List<Job> jobList) {
		this.jobList = jobList;
	}
	
	@Override
	public String toString() {
		return "JobStore [jobList=" + jobList + "]";
	}

	private static final long serialVersionUID = 5516499189660211454L;
}
