package com.mint.controller;

import com.mint.common.DataResponse;
import com.mint.common.ServerResponse;
import com.mint.pojo.Resident;
import com.mint.service.IResidentService;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/resident")
public class ResidentController {

    // 自动注入-用户Service
    @Autowired
    private IResidentService iResidentService;

    @RequestMapping(value = "getResident.do")
    @ResponseBody
    public DataResponse<List<Resident>> getResident(int page, int limit) {
        return iResidentService.getResident(page, limit);
    }

    @RequestMapping(value = "addResident.do")
    @ResponseBody
    public ServerResponse addResident(Resident resident, HttpSession httpSession) {
        return iResidentService.addResident(resident, httpSession);
    }

    @RequestMapping(value = "getResidentInfo.do")
    @ResponseBody
    public ServerResponse<Resident> getResidentInfo(String uid) {
        return iResidentService.getResidentInfo(uid);
    }

    @RequestMapping(value = "updateResident.do")
    @ResponseBody
    public ServerResponse<String> updateResident(Resident resident, HttpSession httpSession) {
        System.out.println(resident);
        return iResidentService.updateResident(resident, httpSession);
    }

    @RequestMapping(value = "deleteResident.do")
    @ResponseBody
    public ServerResponse deleteResident(String uid, HttpSession httpSession) {
        return iResidentService.deleteResident(uid, httpSession);
    }

    @RequestMapping(value = "getResidentWithCondition.do")
    @ResponseBody
    public DataResponse<List<Resident>> getResidentWithCondition(int page, int limit, String s_name, String s_phone, String s_idcnum, String s_nickname) {
        return iResidentService.getResidentWithCondition(page, limit, s_name, s_phone, s_idcnum, s_nickname);
    }

    @RequestMapping(value = "getResidentByHouse.do")
    @ResponseBody
    public DataResponse<List<Resident>> getResidentByHouse(int page, int limit, String building, String unit, String floor, String room) {
        return iResidentService.getResidentByHouse(page, limit, building, unit, floor, room);
    }
}
