package com.mint.controller;

import com.mint.common.DataResponse;
import com.mint.common.ServerResponse;
import com.mint.pojo.Post;
import com.mint.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {

    // 自动注入-用户Service
    @Autowired
    private IPostService iPostService;

    @RequestMapping(value = "/getAllPost.do")
    @ResponseBody
    public DataResponse<List<Post>> getAllPost(int page, int limit) {
        return iPostService.getAllPost(page, limit);
    }

    @RequestMapping(value = "/getAllNoticePost.do")
    @ResponseBody
    public DataResponse<List<Post>> getAllNoticePost(int page, int limit) {
        return iPostService.getAllNoticePost(page, limit);
    }

    @RequestMapping(value = "/postANotice.do")
    @ResponseBody
    public ServerResponse postANotice(String title, String content, HttpSession httpSession) {
        return iPostService.postANotice(title, content, httpSession);
    }

    @RequestMapping(value = "/deletePost.do")
    @ResponseBody
    public ServerResponse deletePost(String tid, String sid) {
        return iPostService.deletePost(tid, sid);
    }

    @RequestMapping(value = "updatePostIsBest.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updatePostIsBest(String sid, String tid, int isbest) {
        return iPostService.updatePostIsBest(sid, tid, isbest);
    }

    @RequestMapping(value = "updatePostIsSticky.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updatePostIsSticky(String sid, String tid, int issticky) {
        return iPostService.updatePostIsSticky(sid, tid, issticky);
    }

    @RequestMapping(value = "/getPostWithCondition.do", method = RequestMethod.POST)
    @ResponseBody
    public DataResponse<List<Post>> getPostWithCondition(int page, int limit, String s_section, String s_title, Integer s_isbest, Integer s_issticky) {
        System.out.println(s_section);
        System.out.println(s_isbest);
        System.out.println(s_issticky);
        System.out.println(s_title);
        return iPostService.getPostWithCondition(page, limit, s_section, s_title, s_isbest, s_issticky);
    }

}
