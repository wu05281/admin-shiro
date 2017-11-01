package com.wu.admin.service.quartz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * Description : Created by intelliJ IDEA
 * Time : 2017 10 25 下午12:19
 *
 * @author :  wubo
 * @version :  1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JobDetailServiceTest {

    @Resource
    private JobDetailService jobDetailService;

    @Test
    public void listActiveJobs() throws Exception {
        jobDetailService.listActiveJobs();
    }

}