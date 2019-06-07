package com.mint.dao;

import com.mint.pojo.Weibo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WeiboMapper {
    int deleteByPrimaryKey(String tid);

    int insert(Weibo record);

    int insertSelective(Weibo record);

    Weibo selectByPrimaryKey(String tid);

    int updateByPrimaryKeySelective(Weibo record);

    int updateByPrimaryKeyWithBLOBs(Weibo record);

    int updateByPrimaryKey(Weibo record);

    List<Weibo> getWeibo(@Param("start") int start, @Param("limit") int limit);

    Integer getWeiboCount();

    List<Weibo> getWeiboWithCondition(@Param("start") int start, @Param("limit") int limit, @Param("uid") String uid, @Param("content") String content);

    Integer getWeiboCountWithCondition(@Param("uid") String uid, @Param("content") String content);
}