package com.mint.service.impl;

import com.mint.common.Const;
import com.mint.common.DataResponse;
import com.mint.common.ServerResponse;
import com.mint.dao.*;
import com.mint.pojo.*;
import com.mint.service.IPostService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("iPostservice")
public class PostServiceImpl implements IPostService {

    @Autowired
    private PostMapper postMapper;
    @Autowired
    private SectionMapper sectionMapper;
    @Autowired
    private CountMapper countMapper;
    @Autowired
    private CollectionMapper collectionMapper;
    @Autowired
    private ReplyMapper replyMapper;
    @Autowired
    private PraiseMapper praiseMapper;
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public DataResponse<List<Post>> getAllPost(int page, int limit) {
        List<Post> list = postMapper.getAllPost((page - 1) * 10, limit);
        Integer count = postMapper.getAllPostCount();
        return new DataResponse<>(0, "", count, list);
    }

    @Override
    public DataResponse<List<Post>> getAllNoticePost(int page, int limit) {
        List<Post> list = postMapper.getAllNoticePost((page - 1) * 10, limit);
        int count = postMapper.getNoticePostCount();
        return new DataResponse<>(0, "", count, list);
    }

    @Override
    public ServerResponse postANotice(String title, String content, HttpSession httpSession) {
        String tid = UUID.randomUUID().toString();
        Date ptime = new Date(System.currentTimeMillis());
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        Post post = new Post(tid, user.getUid(), "cd444079-7080-4841-ae45-59d97f7bbb17", title, ptime, 0, 0, 0, 0, 0, content);
        Integer result = postMapper.insert(post);
        if (1 == result) {
            return ServerResponse.createBySuccessMessage("发布成功！");
        } else {
            return ServerResponse.createByErrorMessage("发布失败！");
        }

    }

    @Override
    public ServerResponse deletePost(String tid, String sid, HttpSession httpSession) {
        Section section = sectionMapper.selectByPrimaryKey(sid);
        String tb_name = section.getTbname();
//        Count count = countMapper.selectByPrimaryKey(uid);
//        count.setGcount(count.getGcount() - 1);
//        Integer upd_result = countMapper.updateByPrimaryKey(count);
        // 删除回复
        replyMapper.deleteByTid(tid);
        // 删除收藏
        collectionMapper.deleteByIid(tid);
        // 删除点赞
        praiseMapper.deleteByIid(tid);
        // 删除消息
        messageMapper.deleteByOid(tid);
        // 删除帖子
        // 需要修改
        Post post = postMapper.selectByPrimaryKey(tid);
        String note = "【删除】" + "【帖子】：" + post.toString();
        Integer del_result = postMapper.deletePostByTbnameAndTid(tb_name, tid);
        if (Const.OP_SUCCESS == del_result) {
            String bid = UUID.randomUUID().toString();
            User opUser = (User) httpSession.getAttribute(Const.CURRENT_USER);
            String opuid = opUser.getUid();
            String uid = post.getUid();
            Date btime = new Date(System.currentTimeMillis());
            Operation operation = new Operation(bid, opuid, uid, btime, Const.OPERATION_TYPE_DELETE, Const.OPERATION_OBJECT_POST, note);
            return ServerResponse.createBySuccessMessage("删除帖子成功！");
        } else {
            return ServerResponse.createBySuccessMessage("删除帖子失败！");
        }
    }

    @Override
    public ServerResponse updatePostIsBest(String sid, String tid, int isbest) {
        Section section = sectionMapper.selectByPrimaryKey(sid);
        Integer result = postMapper.updatePostIsBest(section.getTbname(), tid, isbest);
        if (Const.OP_ERROR == result) {
            return ServerResponse.createByErrorMessage("设置失败！");
        } else {
            return ServerResponse.createBySuccessMessage("设置成功！");
        }
    }

    @Override
    public ServerResponse updatePostIsSticky(String sid, String tid, int issticky) {
        Section section = sectionMapper.selectByPrimaryKey(sid);
        Integer result = postMapper.updatePostIsSticky(section.getTbname(), tid, issticky);
        if (Const.OP_ERROR == result) {
            return ServerResponse.createByErrorMessage("设置失败！");
        } else {
            return ServerResponse.createBySuccessMessage("设置成功！");
        }
    }

    @Override
    public DataResponse<List<Post>> getPostWithCondition(int page, int limit, String section, String title, Integer isbest, Integer issticky) {
        if (!StringUtils.isBlank(title)) {
            title = StringUtils.trim(title);
            title = "%" + title + "%";
        } else {
            title = null;
        }
        if (!StringUtils.isBlank(section)) {
            System.out.println("Y");
            String tb_name = "tb_" + section;
            List<Post> list = postMapper.getPostWithSection((page - 1) * limit, limit, tb_name, title, isbest, issticky);
            Integer count = postMapper.getPostCountWithSection(tb_name, title, isbest, issticky);
            return new DataResponse<List<Post>>(0, "", count, list);
        } else {
            System.out.println("N");
            List<Post> list = postMapper.getPostWithoutSection((page - 1) * limit, limit, title, isbest, issticky);
            Integer count = postMapper.getPostCountWithoutSection(title, isbest, issticky);
            return new DataResponse<List<Post>>(0, "", count, list);
        }
    }
}
