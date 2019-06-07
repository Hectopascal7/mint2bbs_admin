package com.mint.service;

import com.mint.common.DataResponse;
import com.mint.common.ServerResponse;
import com.mint.pojo.User;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

public interface IUserService {
    DataResponse<List<User>> getBBSUser(int page, int limit);

    ServerResponse login(String loginid, String password);

    ServerResponse<String> quit(HttpSession httpSession);

    ServerResponse<User> getUserInfo(HttpSession httpSession);

    ServerResponse resetPassword(String uid, HttpSession httpSession);

    ServerResponse deleteUser(String uid);

    ServerResponse updateUserStatus(String uid, int status, HttpSession httpSession);

    ServerResponse<HashMap<String, String>> getPublisherInfo(String uid);

    DataResponse<List<User>> getUserWithCondition(int page, int limit, String nickname, Integer status, String loginid);

    DataResponse<List<User>> getUserWithPhone(int page, int limit, String phone);
}
