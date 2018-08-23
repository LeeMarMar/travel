package com.itheima.travel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.travel.domain.Favorite;
import com.itheima.travel.domain.PageBean;
import com.itheima.travel.mapper.FavoriteMapper;
import com.itheima.travel.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;



    @Override
    public Boolean isFavoriteByRidAndUserId(Integer rid, Integer uid) {
        boolean flag = false;
        Favorite favorite = favoriteMapper.findFavoriteByRidAndUserId(rid,uid);
        if(favorite!=null){
            flag = true;
        }
        return flag;

    }

    @Override
    public void addFavorite(Integer rid, Integer uid) {
        //1.根据rid和uid添加收藏数据
        favoriteMapper.addFavorite(rid,uid);
        //2.根据rid去线路表中修改该条线路的最新收藏数量
        favoriteMapper.updateRouteFavoriteNum(rid);
    }

    @Override
    public PageBean<Favorite> findFavoriteByPage(Integer curPage, Integer uid) {
        PageBean<Favorite> pageBean = new PageBean<Favorite>();
        //封装当前页
        pageBean.setCurPage(curPage);
        //封装每页的展示的信息数量
        int pageSize = 4;
        pageBean.setPageSize(pageSize);

        //在调用查询方法之前，调用分页插件的静态方法，中间最好不要间隔任何代码
        PageHelper.startPage(curPage, pageSize);

        //分页查找数据
        List<Favorite> favoriteList = favoriteMapper.findFavoriteByPage(uid);
        PageInfo<Favorite> pageInfo = new PageInfo<>(favoriteList);
        pageBean.setData(pageInfo.getList());
        //查询用户总收藏数

        pageBean.setCount((int) pageInfo.getTotal());

        return pageBean;
    }
}
