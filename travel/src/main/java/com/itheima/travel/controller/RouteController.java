package com.itheima.travel.controller;

import com.itheima.travel.domain.PageBean;
import com.itheima.travel.domain.ResultInfo;
import com.itheima.travel.domain.Route;
import com.itheima.travel.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @RequestMapping("findRoutesFavoriteRank")
    @ResponseBody
    private ResultInfo findRoutesFavoriteRank(@RequestParam(value="curPage",defaultValue ="1")Integer curPage,
                                              @RequestParam("rname")String rname,
                                              @RequestParam("startPrice") Integer startPrice,
                                              @RequestParam("endPrice") Integer endPrice){
        ResultInfo resultInfo = null;
        try {
            //调用业务逻辑获取国内游分页数据PageBean
            PageBean pageBean = routeService.getPageBeanByFavorite(curPage,rname,startPrice,endPrice);
            System.out.println(pageBean);
            //正常返回实例对象
            resultInfo = new ResultInfo(true, pageBean, null);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false, null, "服务器正忙");
        }
        return resultInfo;
    }



    /**
     * 黑马精选
     * @return
     */
    @RequestMapping("routeCareChoose")
    @ResponseBody
    public ResultInfo routeCareChoose(){
        ResultInfo resultInfo = null;

        try {
            //调用业务逻辑类获取黑马精选数据
            Map<String,List<Route>> map = routeService.routeCareChoose();
            //正常返回实例对象
            resultInfo = new ResultInfo(true, map, null);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false, null, "服务器正忙");
        }
        return resultInfo;
    }

    /**
     * 分页查询
     * @param cid
     * @param curPage
     * @param rname
     * @return
     */
    @RequestMapping("findRouteListByCid")
    @ResponseBody
    public ResultInfo findRouteListByCid(@RequestParam("cid")Integer cid,
                                         @RequestParam(value="curPage",defaultValue ="1")Integer curPage,
                                         @RequestParam(value = "rname",required = false) String rname){
        ResultInfo resultInfo = null;

        try {
            //调用业务逻辑获取国内游分页数据PageBean
            PageBean pageBean = routeService.getPageBean(cid,curPage,rname);
            //System.out.println(pageBean);
            //正常返回实例对象
            resultInfo = new ResultInfo(true, pageBean, null);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false, null, "服务器正忙");
        }

        return resultInfo;
    }

    @RequestMapping("findRouteListByRname")
    @ResponseBody
    public ResultInfo findRouteListByRname(@RequestParam(value="curPage",defaultValue ="1")Integer curPage,
                                         @RequestParam(value = "rname",required = false) String rname){
        ResultInfo resultInfo = null;

        try {
            //调用业务逻辑获取国内游分页数据PageBean
            PageBean pageBean = routeService.getPageBeanByRname(curPage,rname);
           // System.out.println(pageBean);
            //正常返回实例对象
            resultInfo = new ResultInfo(true, pageBean, null);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false, null, "服务器正忙");
        }

        return resultInfo;
    }

    @RequestMapping("findRouteByRid")
    @ResponseBody
    public ResultInfo findRouteByRid(@RequestParam("rid")Integer rid){
        ResultInfo resultInfo = null;

        //调用业务逻辑根据rid获取相应的线路Route
        try {
            Route route = routeService.findRouteByRid(rid);
            //正常返回实例对象
            resultInfo = new ResultInfo(true, route, null);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false, null, "服务器正忙");
        }

        return resultInfo;
    }
}
