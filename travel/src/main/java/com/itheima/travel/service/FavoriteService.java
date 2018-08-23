package com.itheima.travel.service;

import com.itheima.travel.domain.Favorite;
import com.itheima.travel.domain.PageBean;

public interface FavoriteService {
    /**
     * 根据线路rid和用户uid查询该线路是否已被该用户收藏
     * @param rid
     * @param uid
     * @return
     */
    Boolean isFavoriteByRidAndUserId(Integer rid, Integer uid);

    /**
     * 添加线路的被收藏信息
     * @param rid
     * @param uid
     */
    void addFavorite(Integer rid, Integer uid);

    /**
     * 分页查询用户收藏
     * @param curPage
     * @param uid
     * @return
     */
    PageBean<Favorite> findFavoriteByPage(Integer curPage, Integer uid);
}
