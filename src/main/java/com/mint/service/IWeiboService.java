package com.mint.service;

import com.mint.common.DataResponse;
import com.mint.common.ServerResponse;
import com.mint.pojo.Weibo;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface IWeiboService {
    DataResponse<List<Weibo>> getWeibo(int page, int limit);

    ServerResponse deleteWeibo(String tid, HttpSession httpSession);

    DataResponse<List<Weibo>> getWeiboWithCondition(int page, int limit, String nickname, String content);
}
