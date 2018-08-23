package com.itheima.travel.mapper;

import com.itheima.travel.domain.Favorite;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FavoriteMapper {
    Favorite findFavoriteByRidAndUserId(@Param("rid")Integer rid,@Param("uid")Integer uid);

    void addFavorite(@Param("rid")Integer rid, @Param("uid")Integer uid);

    void updateRouteFavoriteNum(@Param("rid")Integer rid);

    List<Favorite> findFavoriteByPage(@Param("uid") Integer uid);

}
