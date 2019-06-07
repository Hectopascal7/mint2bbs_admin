package com.mint.service.impl;

import com.mint.common.Const;
import com.mint.common.DataResponse;
import com.mint.common.ServerResponse;
import com.mint.dao.OperationMapper;
import com.mint.dao.UserMapper;
import com.mint.dao.WeiboMapper;
import com.mint.pojo.Operation;
import com.mint.pojo.User;
import com.mint.pojo.Weibo;
import com.mint.service.IWeiboService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("iWeiboService")
public class WeiboServiceImpl implements IWeiboService {

    @Autowired
    private WeiboMapper weiboMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OperationMapper operationMapper;

    @Override
    public DataResponse<List<Weibo>> getWeibo(int page, int limit) {
        List<Weibo> list = weiboMapper.getWeibo((page - 1) * 10, limit);
        Integer count = weiboMapper.getWeiboCount();
        return new DataResponse(0, "1", count, list);
    }

    @Override
    public ServerResponse deleteWeibo(String tid, HttpSession httpSession) {
        Weibo weibo = weiboMapper.selectByPrimaryKey(tid);
        Integer result = weiboMapper.deleteByPrimaryKey(tid);
        String bid = UUID.randomUUID().toString();
        User opuser = (User) httpSession.getAttribute(Const.CURRENT_USER);
        Date btime = new Date(System.currentTimeMillis());
        String note = "【删除】" + "【微博】：【原微博信息】——" + weibo.toString() + "。";
        if (1 == result) {
            Operation operation = new Operation(bid, opuser.getUid(), weibo.getUid(), btime, Const.OPERATION_TYPE_DELETE, Const.OPERATION_OBJECT_WEIBO, note);
            operationMapper.insert(operation);
            return ServerResponse.createBySuccessMessage("删除微博成功！");
        } else {
            return ServerResponse.createByErrorMessage("删除微博失败！");
        }
    }

    @Override
    public DataResponse<List<Weibo>> getWeiboWithCondition(int page, int limit, String nickname, String content) {
        if (!StringUtils.isBlank(nickname)) {
            nickname = StringUtils.trim(nickname);
        } else {
            nickname = null;
        }
        if (!StringUtils.isBlank(content)) {
            content = StringUtils.trim(content);
            content = "%" + content + "%";
        } else {
            content = null;
        }
        // 用户名和内容都是空，直接检索全部
        if (StringUtils.isBlank(nickname) && StringUtils.isBlank(content)) {
            return getWeibo(page, limit);
        } else if (StringUtils.isBlank(nickname) && !StringUtils.isBlank(content)) {
            List<Weibo> list = weiboMapper.getWeiboWithCondition((page - 1) * limit, limit, null, content);
            Integer count = weiboMapper.getWeiboCountWithCondition(null, content);
            return new DataResponse<List<Weibo>>(0, "", count, list);
        } else {
            String uid = userMapper.getUidByNickname(nickname);
            if (StringUtils.isBlank(uid)) {
                uid = "";
            }
            List<Weibo> list = weiboMapper.getWeiboWithCondition((page - 1) * limit, limit, uid, content);
            Integer count = weiboMapper.getWeiboCountWithCondition(uid, content);
            return new DataResponse<List<Weibo>>(0, "", count, list);
        }
    }
}
