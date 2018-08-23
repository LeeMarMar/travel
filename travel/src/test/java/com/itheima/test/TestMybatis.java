package com.itheima.test;

import com.itheima.travel.domain.PageBean;
import com.itheima.travel.service.RouteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class TestMybatis {

    @Autowired
    private RouteService routeService;

    @Test
    public void test1(){
        PageBean pageBean = routeService.getPageBean(5,1,null);

        System.out.println(pageBean);
    }
}
