package com.wu.admin.service.quartz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * Description : Created by intelliJ IDEA
 * Time : 2017 10 31 下午3:43
 *
 * @author :  wubo
 * @version :  1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JobExecuteHandlerTest {

    @Resource
    private JobExecuteHandler  jobExecuteHandler;

    @Test
    public void execute() throws Exception {
        jobExecuteHandler.execute(2);
    }

}