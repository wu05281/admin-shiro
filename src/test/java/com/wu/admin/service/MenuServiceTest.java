package com.wu.admin.service;

import com.wu.admin.service.auth.MenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Description : Created by intelliJ IDEA
 *
 * @author wubo
 * @version 创建时间 : 2017 10 22 上午10:20
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class MenuServiceTest {

    @Resource
    private MenuService menuService;
    @Test
    public void getAllMenuCode() throws Exception {
    }

    @Test
    public void listAllMenus() throws Exception {
    }

    @Test
    public void delete(){
        menuService.del("10204");
    }
}