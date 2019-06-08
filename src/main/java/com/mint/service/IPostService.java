package com.mint.service;

import com.mint.common.DataResponse;
import com.mint.common.ServerResponse;
import com.mint.pojo.Post;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface IPostService {

    DataResponse<List<Post>> getAllPost(int page, int limit);

    DataResponse<List<Post>> getAllNoticePost(int page, int limit);

    ServerResponse postANotice(String title, String content, HttpSession httpSession);

    ServerResponse deletePost(String tid, String sid);

    ServerResponse updatePostIsBest(String sid, String tid, int isbest);

    ServerResponse updatePostIsSticky(String sid, String tid, int issticky);

    DataResponse<List<Post>> getPostWithCondition(int page, int limit, String s_section, String s_title, Integer s_isbest, Integer s_issticky);
}