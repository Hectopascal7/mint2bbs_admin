package com.mint.dao;

import com.mint.pojo.Message;

public interface MessageMapper {
    int deleteByPrimaryKey(String mid);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(String mid);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

    Integer deleteByOid(String oid);
}