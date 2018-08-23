package com.itheima.travel.service;

import com.itheima.travel.domain.PageBean;
import com.itheima.travel.domain.Route;

import java.util.List;
import java.util.Map;

public interface RouteService {
    /**
     * 获取黑马精选数据
     * @return
     */
    Map<String,List<Route>> routeCareChoose();

    /**
     * 获取国内游分页数据
     * @param cid
     * @param curPage
     * @param rname
     * @return
     */
    PageBean getPageBean(Integer cid, Integer curPage, String rname);

    /**
     * 获取首页搜索线路分页数据
     * @param curPage
     * @param rname
     * @return
     */
    PageBean getPageBeanByRname(Integer curPage, String rname);

    /**
     * 根据rid获取相应的线路Route
     * @param rid
     * @return
     */
    Route findRouteByRid(Integer rid);

    /**
     * 根据用户输入的最低金额,最高金额,线路名称分页查询相对应的线路信息
     * @param curPage
     * @param rname
     * @param startPrice
     * @param endPrice
     * @return
     */
    PageBean getPageBeanByFavorite(Integer curPage, String rname, Integer startPrice, Integer endPrice);
}
