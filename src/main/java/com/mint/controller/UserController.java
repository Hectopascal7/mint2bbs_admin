package com.mint.controller;

import com.mint.common.Const;
import com.mint.common.DataResponse;
import com.mint.common.ServerResponse;
import com.mint.pojo.User;
import com.mint.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * @Program: mint2bbs
 * @Description: 用户Action
 * @Author: Jeanne d'Arc
 * @Create: 2019-03-05 15:43:26
 **/
@Controller
@RequestMapping("/user")
public class UserController {

    // 自动注入-用户Service
    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "getBBSUser.do")
    @ResponseBody
    public DataResponse<List<User>> getBBSUser(int page, int limit) {
        return iUserService.getBBSUser(page, limit);
    }

    @RequestMapping(value = "login.do")
    @ResponseBody
    public ServerResponse login(String loginid, String password, HttpSession session) {
        ServerResponse<User> response = iUserService.login(loginid, password);
        if (response.isSuccess()) {
            // 账号密码校验成功，将当前用户信息写入session，登录成功
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    @RequestMapping(value = "quit.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> quit(HttpSession httpSession) {
        ServerResponse<String> response = iUserService.quit(httpSession);
        return response;
    }

    @RequestMapping(value = "getUserInfo.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession httpSession) {
        return iUserService.getUserInfo(httpSession);
    }

    @RequestMapping(value = "resetPassword.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse resetPassword(String uid, HttpSession httpSession) {
        return iUserService.resetPassword(uid, httpSession);
    }

    @RequestMapping(value = "deleteUser.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse deleteUser(String uid) {
        return iUserService.deleteUser(uid);
    }

    @RequestMapping(value = "updateUserStatus.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updateUserStatus(String uid, int status, HttpSession httpSession) {
        return iUserService.updateUserStatus(uid, status, httpSession);
    }

    @RequestMapping(value = "getPublisherInfo.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<HashMap<String, String>> getPublisherInfo(String uid) {
        return iUserService.getPublisherInfo(uid);
    }

    @RequestMapping(value = "getUserWithCondition.do", method = RequestMethod.POST)
    @ResponseBody
    public DataResponse<List<User>> getUserWithCondition(int page, int limit, String s_nickname, Integer s_status, String s_loginid) {
        return iUserService.getUserWithCondition(page, limit, s_nickname, s_status, s_loginid);
    }

    @RequestMapping(value = "getUserWithPhone.do", method = RequestMethod.POST)
    @ResponseBody
    public DataResponse<List<User>> getUserWithPhone(int page, int limit, String s_phone) {
        return iUserService.getUserWithPhone(page, limit, s_phone);
    }

}
