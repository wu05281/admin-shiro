package com.wu.admin.service.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description : Created by intelliJ IDEA
 * @author  :  wubo
 * @version :  1.0.0
 * time : 2017 10 24 下午5:35
 */
public class JobDetailFactory implements Job {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        Integer id = (Integer) context.getMergedJobDataMap().get("jobId");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        String current = sdf.format(new Date());
        JobExecuteHandler jobExecuteHandler = (JobExecuteHandler) SpringContextHolder.getBean(JobExecuteHandler.class);
        jobExecuteHandler.execute(id);
        logger.info("job execute time = [ {} ], 任务id: {}", current, id);
    }
}
