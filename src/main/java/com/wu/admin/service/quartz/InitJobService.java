package com.wu.admin.service.quartz;

import com.wu.admin.pojo.quartz.bo.JobInfoBO;
import com.wu.admin.pojo.quartz.entity.JobDetailDO;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Description : Created by intelliJ IDEA
 *
 * @author :  wubo
 * @version :  1.0.0
 *          time : 2017 10 24 下午5:34
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class InitJobService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Resource
    private JobDetailService jobDetailService;

    @PostConstruct
    public void init() throws Exception {
        System.out.println("定时任务初始化=========================");
        List<JobDetailDO> jobDetails = jobDetailService.listActiveJobs();
        logger.info("定时任务初始化:{}", jobDetails);
        for (JobDetailDO jobInfo : jobDetails) {
            this.addJob(jobInfo);
        }
    }

    public void addJob(JobDetailDO jobInfo) throws Exception {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        String identity = jobInfo.getNameCode() + "-" + jobInfo.getGroupCode();
        String cron = jobInfo.getCron();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);

        TriggerKey triggerKey = TriggerKey.triggerKey(jobInfo.getNameCode(), jobInfo.getGroupCode());

        // 不存在，创建一个
        if (!scheduler.checkExists(triggerKey)) {
            JobDetail jobDetail = JobBuilder.newJob(JobDetailFactory.class).withIdentity(jobInfo.getNameCode(), jobInfo.getGroupCode()).build();
            jobDetail.getJobDataMap().put("jobId", jobInfo.getId());
            jobDetail.getJobDataMap().put("callUrl", jobInfo.getCallUrl());
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobInfo.getNameCode(), jobInfo.getGroupCode()).withSchedule(scheduleBuilder.withMisfireHandlingInstructionDoNothing()).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder.withMisfireHandlingInstructionDoNothing()).build();
            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        }
    }

    public void pauseJob(JobDetailDO job) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(job.getNameCode(), job.getGroupCode());
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复一个job
     *
     */
    public void resumeJob(JobDetailDO job) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(job.getNameCode(), job.getGroupCode());
        scheduler.resumeJob(jobKey);
    }
    /**
     * 删除一个job
     *
     */
    public void deleteJob(JobDetailDO job) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(job.getNameCode(), job.getGroupCode());
        scheduler.deleteJob(jobKey);
    }
    /**
     * 立即执行job
     *
     */
    public void runJobNow(JobDetailDO job) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey = JobKey.jobKey(job.getNameCode(), job.getGroupCode());
        scheduler.triggerJob(jobKey);
    }


    private List<JobInfoBO> prepareList() {
        List<JobInfoBO> jobInfos = new ArrayList<>();
        JobInfoBO job = new JobInfoBO();
        job.setNameCode("job1");
        job.setGroupCode("job1");
        job.setCron("0 0/2 * * * ?");
        job.setCallUrl("http://localhost:9999/ajax/test");
        jobInfos.add(job);
        return jobInfos;
    }

}
