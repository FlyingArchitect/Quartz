package com.demo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.po.JobDetailPo;
import com.demo.service.QuartzService;
/**
 * 修改基本没有判断，偷懒和添加写一起了
 * @author WANGJS-PC
 *
 */
@Service
public class QuartzServiceImpl implements QuartzService {
	@Autowired
	private Scheduler quartzScheduler;

	/* 添加任务 */
	@Override
	public void addJob(JobDetailPo jobDetailPo) {
		// 获取调度器
		Scheduler sched = quartzScheduler;
		TriggerKey triggerKey = TriggerKey.triggerKey(jobDetailPo.getJobName(), jobDetailPo.getJobGroupName());

		CronTrigger trigger = null;
		try {
			trigger = (CronTrigger) sched.getTrigger(triggerKey);
		} catch (SchedulerException e1) {
			e1.printStackTrace();
		}
		if (null == trigger) {
			String clz = jobDetailPo.getClz();
			Class cls = null;
			try {
				cls = Class.forName(clz);
			} catch (ClassNotFoundException e) {
				System.out.println("对象转换错误");
				e.printStackTrace();
			}
			// 创建一个任务
			JobDetail jobDetail = JobBuilder.newJob(cls)
					.withIdentity(jobDetailPo.getJobName(), jobDetailPo.getJobGroupName()).build();
			// 触发器
			trigger = TriggerBuilder.newTrigger()
					.withIdentity(jobDetailPo.getTriggerName(), jobDetailPo.getTriggerGroupName())
					.withSchedule(CronScheduleBuilder.cronSchedule(jobDetailPo.getCron())).build();

			try {
				sched.scheduleJob(jobDetail, trigger);
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}

	}

	/* 启动任务 */
	public void startJob() {
		Scheduler sched = quartzScheduler;
		try {
			sched.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/* 启动已配置好的定时任务 */
	public void resumeJob() {
		try {
			Scheduler sched = quartzScheduler;
			// ①获取调度器中所有的触发器组
			List<String> triggerGroups = sched.getTriggerGroupNames();
			System.out.println("triggerGroups:" + triggerGroups);
			// ②重新恢复在tgroup1组中，名为trigger1_1触发器的运行
			for (int i = 0; i < triggerGroups.size(); i++) {
				List<String> triggers = sched.getTriggerGroupNames();
				System.out.println("triggers:" + triggers);
				for (int j = 0; j < triggers.size(); j++) {
					Trigger tg = sched.getTrigger(new TriggerKey(triggers.get(j), triggerGroups.get(i)));
					// ②-1:根据名称判断
					if (tg instanceof CronTrigger && tg.getDescription().equals("trigger1_1.tGroup1") || j > -1) {
						// ②-1:恢复运行
						sched.resumeJob(new JobKey(triggers.get(j), triggerGroups.get(i)));
					}
				}
			}
			sched.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* 任务列表 */
	@Override
	public List<JobDetailPo> getList() {
		List<JobDetailPo> jobInfos = new ArrayList<>();
		List<String> triggerGroupNames;
		try {
			triggerGroupNames = quartzScheduler.getTriggerGroupNames();
			for (String triggerGroupName : triggerGroupNames) {
				Set<TriggerKey> triggerKeySet = quartzScheduler
						.getTriggerKeys(GroupMatcher.triggerGroupEquals(triggerGroupName));
				for (TriggerKey triggerKey : triggerKeySet) {
					Trigger t = quartzScheduler.getTrigger(triggerKey);
					if (t instanceof CronTrigger) {
						CronTrigger trigger = (CronTrigger) t;
						JobKey jobKey = trigger.getJobKey();
						JobDetail jd = quartzScheduler.getJobDetail(jobKey);
						JobDetailPo jobInfo = new JobDetailPo();
						System.out.println("jobKey:" + jobKey.toString());
						jobInfo.setClz(String.valueOf(jobKey.getClass()));

						jobInfo.setJobName(jobKey.getName());
						jobInfo.setJobGroupName(jobKey.getGroup());
						jobInfo.setTriggerName(triggerKey.getName());
						jobInfo.setTriggerGroupName(triggerKey.getGroup());
						jobInfo.setCron(trigger.getCronExpression());
						// jobInfo.setNextFireTime(trigger.getNextFireTime());
						// jobInfo.setPreviousFireTime(trigger.getPreviousFireTime());
						// jobInfo.setStartTime(trigger.getStartTime());
						// jobInfo.setEndTime(trigger.getEndTime());
						// jobInfo.setJobClass(jd.getJobClass().getCanonicalName());
						// jobInfo.setDuration(Long.parseLong(jd.getDescription()));
						Trigger.TriggerState triggerState = quartzScheduler.getTriggerState(trigger.getKey());
						// jobInfo.setJobStatus(triggerState.toString());//
						// NONE无,
						// NORMAL正常,
						// PAUSED暂停,
						// COMPLETE完全,
						// ERROR错误,
						// BLOCKED阻塞
						System.out.println("triggerState.toString():" + triggerState.toString());
						JobDataMap map = quartzScheduler.getJobDetail(jobKey).getJobDataMap();
						// if (null != map&&map.size() != 0) {
						// jobInfo.setCount(Integer.parseInt((String) map
						// .get("count")));
						// jobInfo.setJobDataMap(map);
						// } else {
						// jobInfo.setJobDataMap(new JobDataMap());
						// }
						jobInfos.add(jobInfo);
					}
				}
			}
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jobInfos;
	}
	
    /*修改任务*/
	@Override
	public void modfiy(JobDetailPo jobDetailPo) {
		try {
			Scheduler sched = quartzScheduler;
			CronTrigger trigger = (CronTrigger) sched
					.getTrigger(TriggerKey.triggerKey(jobDetailPo.getTriggerName(), jobDetailPo.getTriggerGroupName()));
			if (trigger == null) {
				// 该任务不存在，是添加
				addJob(jobDetailPo);
//				或直接返回
//				return;
			}
			// 该任务已存在，是修改
			JobKey jobKey = JobKey.jobKey(jobDetailPo.getJobName(), jobDetailPo.getJobGroupName());
			TriggerKey triggerKey = TriggerKey.triggerKey(jobDetailPo.getJobName(), jobDetailPo.getJobGroupName());
			JobDetail job = sched.getJobDetail(jobKey);
			Class jobClass = job.getJobClass();
			// 停止触发器
			sched.pauseTrigger(triggerKey);
			// 移除触发器
			sched.unscheduleJob(triggerKey);
			// 删除任务
			sched.deleteJob(jobKey);
			addJob(jobDetailPo);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void pause(JobDetailPo JobDetailPo) {
		Scheduler sched = quartzScheduler;
		JobKey jobKey = JobKey.jobKey(JobDetailPo.getJobName(), JobDetailPo.getJobGroupName());
		if (jobKey != null) {
			try {
				sched.pauseJob(jobKey);
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
	}
	
    /*暂停所有*/
	public void pauseAllJob() throws SchedulerException {
		Scheduler sched = quartzScheduler;
		sched.pauseAll();
	}

	@Override
	public void delete(JobDetailPo JobDetailPo) {
		Scheduler sched = quartzScheduler;
		JobKey jobKey = JobKey.jobKey(JobDetailPo.getJobName(), JobDetailPo.getJobGroupName());
		if (jobKey != null) {
			try {
				sched.deleteJob(jobKey);
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void resume(JobDetailPo JobDetailPo) {
		Scheduler scheduler = quartzScheduler;
		JobKey jobKey = JobKey.jobKey(JobDetailPo.getJobName(), JobDetailPo.getJobGroupName());
		if (jobKey != null) {
			try {
				scheduler.resumeJob(jobKey);
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
	}
}
