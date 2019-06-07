package com.mint.dao;

import com.mint.pojo.Resident;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResidentMapper {
    int deleteByPrimaryKey(String uid);

    int insert(Resident record);

    int insertSelective(Resident record);

    Resident selectByPrimaryKey(String uid);

    int updateByPrimaryKeySelective(Resident record);

    int updateByPrimaryKey(Resident record);

    List<Resident> getResident(@Param("start") int start, @Param("limit") int limit);

    Integer getResidentCount();

    String getResidentByBUFR(@Param("building") String building, @Param("unit") String unit, @Param("floor") String floor, @Param("room") String room);

    List<Resident> getResidentsByPhone(String phone);

    List<Resident> getResidentWithCondition(@Param("start") int start, @Param("limit") int limit, @Param("name") String name, @Param("phone") String phone, @Param("idcnum") String idcnum, @Param("uid") String uid);

    Integer getResidentCountWithCondition(@Param("name") String name, @Param("phone") String phone, @Param("idcnum") String idcnum, @Param("uid") String uid);

    List<Resident> getResidentByHouse(@Param("start") int start, @Param("limit") int limit, @Param("building") String building, @Param("unit") String unit, @Param("floor") String floor, @Param("room") String room);

    Integer getResidentCountByHouse(@Param("building") String building, @Param("unit") String unit, @Param("floor") String floor, @Param("room") String room);
}