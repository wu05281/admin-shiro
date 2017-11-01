package com.wu.admin.service.quartz.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/**
 * Description : Created by intelliJ IDEA
 * Time : 2017 10 25 下午3:33
 *
 * @author :  wubo
 * @version :  1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestTemplateTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testUsefull(){
        String url = "https://free-api.heweather.com/v5/forecast?city=CN101080101&key=5c043b56de9f4371b0c7f8bee8f5b75e";
        String resp = restTemplate.getForObject(url, String.class);
        System.out.println(resp);
    }
}
