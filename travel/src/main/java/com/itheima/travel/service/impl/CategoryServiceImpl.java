package com.itheima.travel.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.travel.domain.Category;
import com.itheima.travel.mapper.CategoryMapper;
import com.itheima.travel.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public String findAllCategory() throws JsonProcessingException {
        //定义返回的json
        String jsonData = null;

        try {
            //获取jedis里面的数据
            jsonData = (String) redisTemplate.opsForValue().get("categoryList");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //判断有效性，是否空
        if(jsonData==null || "".equals(jsonData)){
            //获取的数据为空,这是应该从数据库中去获取种类信息
            List<Category> categoryList = categoryMapper.findAllCategory();
            //将获取到的集合对象转换为json
            jsonData = new ObjectMapper().writeValueAsString(categoryList);

            try {
                //将json写入redis缓存数据库中
                redisTemplate.opsForValue().set("categoryList",jsonData);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //将json返回
        return jsonData;
    }
}
