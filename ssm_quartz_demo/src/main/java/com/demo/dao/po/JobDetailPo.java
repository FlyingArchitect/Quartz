package com.demo.dao.po;

import java.io.Serializable;

public class JobDetailPo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String clz;
	private String jobName;
	private String jobGroupName;
	private String triggerName;
	private String triggerGroupName;
	private String cron;
	public String getClz() {
		return clz;
	}
	public void setClz(String clz) {
		this.clz = clz;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroupName() {
		return jobGroupName;
	}
	public void setJobGroupName(String jobGroupName) {
		this.jobGroupName = jobGroupName;
	}
	public String getTriggerName() {
		return triggerName;
	}
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}
	public String getTriggerGroupName() {
		return triggerGroupName;
	}
	public void setTriggerGroupName(String triggerGroupName) {
		this.triggerGroupName = triggerGroupName;
	}
	public String getCron() {
		return cron;
	}
	public void setCron(String cron) {
		this.cron = cron;
	}
	@Override
	public String toString() {
		return "JobDetailPo [clz=" + clz + ", jobName=" + jobName + ", jobGroupName=" + jobGroupName + ", triggerName="
				+ triggerName + ", triggerGroupName=" + triggerGroupName + ", cron=" + cron + "]";
	}
	public JobDetailPo() {
		super();
	}
	

}
