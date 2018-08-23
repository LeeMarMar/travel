package com.itheima.travel.mapper;

import com.itheima.travel.domain.Route;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RouteMapper {
    /**
     * 获取人气线路列表
     * @return
     */
    List<Route> getPopularityRouteList();

    /**
     * 获取最新线路列表
     * @return
     */
    List<Route> getNewsRouteList();

    /**
     * 获取主题线路列表
     * @return
     */
    List<Route> getThemesRouteList();

    /**
     * 动态获取当前页数据列表
     * @param cid
     * @param rname
     * @return
     */
    List<Route> findRouteListByPage(@Param("cid")Integer cid,@Param("rname")String rname);



    List<Route> findRouteListByPageByRname(@Param("rname")String rname);


    Route findRouteByRid(@Param("rid")Integer rid);

    /**
     * 收藏排行榜分页查询
     * @param rname
     * @param startPrice
     * @param endPrice
     * @return
     */
    List<Route> findRouteListByPageByFavorite(@Param("rname")String rname,@Param("startPrice")Integer startPrice,@Param("endPrice")Integer endPrice);
}
