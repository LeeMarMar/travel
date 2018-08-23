package com.itheima.travel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.travel.domain.ResultInfo;
import com.itheima.travel.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 异步请求查询所有分类
     * @return
     */
    @RequestMapping("findAllCategory")
    @ResponseBody
    public String findAllCategory(){
        //调用业务逻辑层获取分类列表的json数据
        String jsonData = null;
        //定义json转换对象
        ObjectMapper objectMapper = new ObjectMapper();
        //定义返回数据对象
        ResultInfo resultInfo = null;
        try {
            jsonData = categoryService.findAllCategory();
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false, null, "服务器正忙");
        }
        return jsonData;
    }
}
