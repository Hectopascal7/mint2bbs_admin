package com.mint.service;

import com.mint.common.DataResponse;
import com.mint.common.ServerResponse;
import com.mint.pojo.Resident;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface IResidentService {
    DataResponse<List<Resident>> getResident(int page, int limit);

    ServerResponse addResident(Resident resident, HttpSession httpSession);

    ServerResponse<Resident> getResidentInfo(String uid);

    ServerResponse<String> updateResident(Resident resident, HttpSession httpSession);

    ServerResponse deleteResident(String uid, HttpSession httpSession);

    DataResponse<List<Resident>> getResidentWithCondition(int page, int limit, String name, String phone, String idcnum, String nickname);

    DataResponse<List<Resident>> getResidentByHouse(int page, int limit, String building, String unit, String floor, String room);
}
