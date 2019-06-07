package com.mint.controller;

import com.mint.common.DataResponse;
import com.mint.common.ServerResponse;
import com.mint.pojo.Good;
import com.mint.service.IGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/good")
public class GoodController {


    // 自动注入-商品Service
    @Autowired
    private IGoodService iGoodService;

    @RequestMapping(value = "/getGood.do")
    @ResponseBody
    public DataResponse<List<Good>> getGood(int page, int limit, Integer s_isused, String s_title, Integer s_issaled) {
        return iGoodService.getGood(page, limit, s_isused, s_title, s_issaled);
    }

    @RequestMapping(value = "deleteGood.do")
    @ResponseBody
    public ServerResponse deleteGood(String gid, String uid) {
        return iGoodService.deleteGood(gid, uid);
    }


}
