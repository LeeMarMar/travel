package com.itheima.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext*.xml")
public class TestRedisTemplate {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Test
    public void test1(){
        //向redis中存入一个数据
        redisTemplate.opsForValue().set("msg","redisTemplate");
        //取出数据
        System.out.println(redisTemplate.opsForValue().get("msg"));
    }
}
