package com.itheima.travel.controller;

import com.itheima.travel.domain.*;
import com.itheima.travel.service.FavoriteService;
import com.itheima.travel.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("favorite")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private RouteService routeService;

    /**
     * 用户收藏
     * @param curPage
     * @param session
     * @return
     */
    @RequestMapping("findFavoriteByPage")
    @ResponseBody
    private ResultInfo findFavoriteByPage(@RequestParam(value = "curPage",defaultValue = "1")Integer curPage,HttpSession session){
        ResultInfo resultInfo = null;

        try {
            User user = (User) session.getAttribute("loginUser");

            PageBean<Favorite> pageBean = favoriteService.findFavoriteByPage(curPage,user.getUid());
            System.out.println(pageBean);

            //封装
            resultInfo = new ResultInfo(true, pageBean, null);
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false, null, "服务器正忙");
        }
        return resultInfo;
    }


    /**
     * 收藏该旅游线路
     * @param rid
     * @param session
     * @return
     */
    @RequestMapping("addFavorite")
    @ResponseBody
    public ResultInfo addFavorite(@RequestParam("rid")Integer rid, HttpSession session){
        ResultInfo resultInfo = null;

        try {
            //判断用户是否登录
            User user = (User) session.getAttribute("loginUser");
            if(user==null){
                //用户未登录
                resultInfo = new ResultInfo(true, 0, null);
            }else {
                //用户已登录,根据用户uid和路线rid去数据库添加该用户的收藏信息
                favoriteService.addFavorite(rid,user.getUid());

                //根据rid去获取线路对象
                Route route = routeService.findRouteByRid(rid);
                //获取该线路最新的被收藏数量
                int count = route.getCount();

                resultInfo = new ResultInfo(true, count, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false, null, "该旅游线路已被收藏");
        }

        return resultInfo;
    }

    /**
     * 获取该旅游线路是否已被收藏
     * @param rid
     * @param session
     * @return
     */
    @RequestMapping("isFavoriteByRid")
    @ResponseBody
    public ResultInfo isFavoriteByRid(@RequestParam("rid")Integer rid, HttpSession session){
        ResultInfo resultInfo = null;

        try {
            //判断用户是否登录
            User user = (User) session.getAttribute("loginUser");
            if(user==null){
                //用户未登录
                resultInfo = new ResultInfo(true,false,null);
            }else{
                //用户登录,根据rid和用户去查询此线路是否已被该用户收藏
                Boolean flag = favoriteService.isFavoriteByRidAndUserId(rid,user.getUid());
                if(flag){
                    //已被收藏
                    resultInfo = new ResultInfo(true, true, null);
                }else {
                    //未被收藏
                    resultInfo = new ResultInfo(true,false,null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false, null, "服务器正忙");
        }

        return resultInfo;
    }


}
