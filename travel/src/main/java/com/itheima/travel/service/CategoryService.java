package com.itheima.travel.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface CategoryService {
    /**
     * 异步请求查询所有分类
     * @return
     */
    String findAllCategory() throws JsonProcessingException;
}
