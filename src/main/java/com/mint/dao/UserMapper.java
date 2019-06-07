package com.mint.dao;

import com.mint.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(String uid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String uid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKeyWithBLOBs(User record);

    int updateByPrimaryKey(User record);

    List<User> getBBSUser(@Param("start") int start, @Param("limit") int limit);

    Integer getBBSUserCount();

    User login(@Param("loginid") String loginid, @Param("password") String password);

    Integer resetPassword(@Param("uid") String uid, @Param("password") String password);

    Integer updateUserStatus(@Param("uid") String uid, @Param("status") int status);

    String getUidByNickname(String nickname);

    List<User> getUserWithCondition(@Param("start") int start, @Param("limit") int limit, @Param("nickname") String nickname, @Param("status") Integer status, @Param("loginid") String loginid);

    Integer getUserCountWithCondition(@Param("nickname") String nickname, @Param("status") Integer status, @Param("loginid") String loginid);
}