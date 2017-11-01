package com.wu.admin.service.quartz;

import com.wu.admin.base.DaoSupport;
import com.wu.admin.pojo.quartz.dto.JobRequestDTO;
import com.wu.admin.pojo.quartz.dto.JobResponseDTO;
import com.wu.admin.pojo.quartz.entity.JobDetailDO;
import com.wu.admin.utils.SnowflakeIdWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * Description : Created by intelliJ IDEA
 * Time : 2017 10 27 上午10:04
 *
 * @author :  wubo
 * @version :  1.0.0
 */
@Service
public class JobExecuteHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private JobDetailService jobDetailService;

    @Resource
    private DaoSupport daoSupport;

    @Resource
    private SnowflakeIdWorker snowflakeIdWorker;

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public void execute(Integer jobId) {
        JobRequestDTO jobRequestDTO = new JobRequestDTO();
        JobDetailDO jobDetailDO = daoSupport.get(jobId, JobDetailDO.class);
        jobRequestDTO.setReqSequence(String.valueOf(snowflakeIdWorker.nextId()));
        jobRequestDTO.setJobGroupCode(jobDetailDO.getGroupCode());
        jobRequestDTO.setJobNameCode(jobDetailDO.getNameCode());
        JobResponseDTO jobResponseDTO = restTemplate.postForObject(jobDetailDO.getCallUrl(), jobRequestDTO, JobResponseDTO.class);
        logger.info("请求地址：{},执行结果为：{}", jobDetailDO.getCallUrl(), jobResponseDTO);
    }
}
