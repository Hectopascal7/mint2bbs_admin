package com.mint.controller;

import com.mint.common.DataResponse;
import com.mint.common.ServerResponse;
import com.mint.pojo.Weibo;
import com.mint.service.IWeiboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Program: mint2bbs
 * @Description: 用户Action
 * @Author: Jeanne d'Arc
 * @Create: 2019-03-05 15:43:26
 **/
@Controller
@RequestMapping("/weibo")
public class WeiboController {
    // 自动注入-用户Service
    @Autowired
    private IWeiboService iWeiboService;

    @RequestMapping(value = "getWeibo.do")
    @ResponseBody
    public DataResponse<List<Weibo>> getWeibo(int page, int limit) {
        return iWeiboService.getWeibo(page, limit);
    }

    @RequestMapping(value = "deleteWeibo.do")
    @ResponseBody
    public ServerResponse deleteWeibo(String tid, HttpSession httpSession) {
        return iWeiboService.deleteWeibo(tid,httpSession);
    }

    @RequestMapping(value = "getWeiboWithCondition.do")
    @ResponseBody
    public DataResponse<List<Weibo>> getWeiboWithCondition(int page, int limit,String s_nickname,String s_content) {
        return iWeiboService.getWeiboWithCondition(page,limit,s_nickname,s_content);
    }
}