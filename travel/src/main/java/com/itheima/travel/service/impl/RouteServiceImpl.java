package com.itheima.travel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.travel.domain.PageBean;
import com.itheima.travel.domain.Route;
import com.itheima.travel.mapper.RouteMapper;
import com.itheima.travel.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteMapper routeMapper;

    @Override
    public Map<String, List<Route>> routeCareChoose() {

        Map<String, List<Route>> map = new HashMap<>();
        //获取人气线路列表
        List<Route> popularityRoute = routeMapper.getPopularityRouteList();
        //获取最新线路列表
        List<Route> newsRoute = routeMapper.getNewsRouteList();
        //获取主题线路列表
        List<Route> themesRoute = routeMapper.getThemesRouteList();

        //将3个线路列表分别存入map中
        map.put("popularity", popularityRoute);
        map.put("news", newsRoute);
        map.put("themes", themesRoute);
        return map;
    }

    @Override
    public PageBean getPageBean(Integer cid, Integer curPage, String rname) {
        //封装分页类数据
        PageBean<Route> pageBean = new PageBean<>();
        //封装首页
        //pageBean.setFirstPage(1);
        //封装当前页
        pageBean.setCurPage(curPage);
        //每页数据的条数
        int pageSize = 3;
        pageBean.setPageSize(pageSize);
        //在调用查询方法之前，调用分页插件的静态方法，中间最好不要间隔任何代码
        PageHelper.startPage(curPage,pageSize);
        //动态获取当前页数据列表
        List<Route> routeList = routeMapper.findRouteListByPage(cid,rname);
        PageInfo<Route> pageInfo = new PageInfo<Route>(routeList);
        pageBean.setData(pageInfo.getList());
        //获取线路条路总和
        //int count = routeMapper.getCountByCid(cid,rname);
        pageBean.setCount((int) pageInfo.getTotal());
        return pageBean;
    }

    @Override
    public PageBean getPageBeanByRname(Integer curPage, String rname) {
        //封装分页类数据
        PageBean<Route> pageBean = new PageBean<>();
        //封装首页
        //pageBean.setFirstPage(1);
        //封装当前页
        pageBean.setCurPage(curPage);
        //每页数据的条数
        int pageSize = 3;
        pageBean.setPageSize(pageSize);
        //在调用查询方法之前，调用分页插件的静态方法，中间最好不要间隔任何代码
        PageHelper.startPage(curPage,pageSize);

        //动态获取当前页数据列表
        List<Route> routeList = routeMapper.findRouteListByPageByRname(rname);
        PageInfo<Route> pageInfo = new PageInfo<>(routeList);
        pageBean.setData(pageInfo.getList());
        //获取线路条路总和
        //int count = routeMapper.getCountByCidByRname(rname);
        pageBean.setCount((int) pageInfo.getTotal());
        return pageBean;
    }

    @Override
    public Route findRouteByRid(Integer rid) {
        Route route = routeMapper.findRouteByRid(rid);
        System.out.println(route);
        return route;
    }

    @Override
    public PageBean getPageBeanByFavorite(Integer curPage, String rname, Integer startPrice, Integer endPrice) {
        //封装分页类数据
        PageBean<Route> pageBean = new PageBean<>();
        //封装当前页
        pageBean.setCurPage(curPage);
        //每页数据的条数
        int pageSize = 4;
        pageBean.setPageSize(pageSize);
        //在调用查询方法之前，调用分页插件的静态方法，中间最好不要间隔任何代码
        PageHelper.startPage(curPage,pageSize);

        //动态获取当前页数据列表
        List<Route> routeList = routeMapper.findRouteListByPageByFavorite(rname,startPrice,endPrice);
        PageInfo<Route> pageInfo = new PageInfo<>(routeList);
        pageBean.setData(pageInfo.getList());

        //获取线路条路总和
        pageBean.setCount((int) pageInfo.getTotal());
        return pageBean;
    }
}
