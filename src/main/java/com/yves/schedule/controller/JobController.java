package com.yves.schedule.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.SchedulingException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yves.schedule.BaseJob;
import com.yves.smpp.service.InvoiceService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class JobController implements ApplicationContextAware {
	

	private Map<String, ScheduledFuture<?>> taskFuturesMap = new ConcurrentHashMap<String, ScheduledFuture<?>>();
	
	private ApplicationContext applicationContext;
	
	@Resource
	InvoiceService invoiceService;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	@Resource
	private ThreadPoolTaskScheduler taskExecutor;
	
	@PostConstruct
	private void init() {
		log.info("===== Simple_Mart <Scheduled> init() =====");
		for(BaseJob job : applicationContext.getBeansOfType(BaseJob.class).values()) {
			ScheduledFuture<?> future = taskExecutor.schedule((Runnable)job,
					new CronTrigger(job.getCronExpression()));
			log.info("啟動排程: "+job.getClass().getName());
			taskFuturesMap.put(job.getClass().getSimpleName(), future);
		}
	}

	/**
	 * 取消任務(停止)
	 * 
	 * @param jobId
	 */
	@RequestMapping(path = "/cancel/{jobId}")
	public void cancelTriggerTask(@PathVariable(value="jobId") String jobId) {
		log.info("cancel job_id: {}", jobId);
		ScheduledFuture<?> future = taskFuturesMap.get(jobId);
		if (future != null) {
			future.cancel(true);
		}
		taskFuturesMap.remove(jobId);
	}
	
	/**
	 * 添加任務(啟動??) MyTestJob -> myTestJob
	 */
	@RequestMapping(path = "/add/{jobId}")
	public void addTriggerTask(@PathVariable(value="jobId") String jobId) {
		if (taskFuturesMap.containsKey(jobId)) {
			throw new SchedulingException("the job[" + jobId + "] was added.");
		}
		BaseJob job = applicationContext.getBean(jobId, BaseJob.class);
		ScheduledFuture<?> future = taskExecutor.schedule((Runnable)job,
				new CronTrigger(job.getCronExpression()));
		taskFuturesMap.put(jobId, future);
	}
	
	/**
	 * 重置任務(修改週期)
	 * 
	 * @throws Exception 
	 */
	@RequestMapping(path = "/reset/{jobId}")
	public void resetTriggerJob(@PathVariable(value="jobId") String jobId) throws Exception {
		cancelTriggerTask(jobId);
		addTriggerTask(jobId);
	}
	
	/** temptemptemptemp
	/** temptemptemptemp
	/** temptemptemptemp
	 * 添加任務(立即/只執行一次)
	 * 
	 * @throws Exception 
	 */
	public void addTaskNow(SysScheduler task) throws Exception {
		// 立刻執行，加個編號
		String taskId = task.getJobId() + "-1";
		if (taskFuturesMap.containsKey(task.getJobId())) {
			throw new SchedulingException("immediately taskId[" + taskId + "] was added.");
		}
//		ScheduledFuture<?> future = 
		taskExecutor.execute((Runnable)Class.forName(task.getJobClassname()).newInstance());
//		taskFuturesMap.put(task.getJobId(), future);
	}
	
	
	class SysScheduler{
		String jobId;
		String jobClassname;
		String cronExpression;
		public String getJobId() {
			return jobId;
		}
		public void setJobId(String jobId) {
			this.jobId = jobId;
		}
		public String getJobClassname() {
			return jobClassname;
		}
		public void setJobClassname(String jobClassname) {
			this.jobClassname = jobClassname;
		}
		public String getCronExpression() {
			return cronExpression;
		}
		public void setCronExpression(String cronExpression) {
			this.cronExpression = cronExpression;
		}
	}
}
