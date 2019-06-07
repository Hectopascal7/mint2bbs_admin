package com.mint.dao;

import com.mint.pojo.Section;

public interface SectionMapper {
    int deleteByPrimaryKey(String sid);

    int insert(Section record);

    int insertSelective(Section record);

    Section selectByPrimaryKey(String sid);

    int updateByPrimaryKeySelective(Section record);

    int updateByPrimaryKey(Section record);

}