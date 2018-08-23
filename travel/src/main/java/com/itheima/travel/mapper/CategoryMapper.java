package com.itheima.travel.mapper;

import com.itheima.travel.domain.Category;

import java.util.List;

public interface CategoryMapper {

    /**
     * 从数据库中获取所有商品的分类信息
     * @return
     */
    List<Category> findAllCategory();
}
