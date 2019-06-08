package com.mint.controller;


import com.mint.common.ServerResponse;
import com.mint.service.ISectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/section")
public class SectionController {

    @Autowired
    private ISectionService iSectionService;

    @RequestMapping(value = "getSnameBySid.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> getSnameBySid(String sid) {
        return iSectionService.getSnameBySid(sid);
    }
}
