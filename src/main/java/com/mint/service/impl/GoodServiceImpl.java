package com.mint.service.impl;

import com.mint.common.Const;
import com.mint.common.DataResponse;
import com.mint.common.ServerResponse;
import com.mint.dao.CountMapper;
import com.mint.dao.GoodMapper;
import com.mint.dao.UserMapper;
import com.mint.pojo.Count;
import com.mint.pojo.Good;
import com.mint.service.IGoodService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("iGoodService")
public class GoodServiceImpl implements IGoodService {

    @Autowired
    private GoodMapper goodMapper;
    @Autowired
    private CountMapper countMapper;
    @Autowired
    private UserMapper userMapper;


    @Override
    public DataResponse<List<Good>> getGood(int page, int limit, Integer isused, String title, Integer issaled) {
        System.out.println(isused);
        System.out.println(title);
        System.out.println(issaled);
        if (!StringUtils.isBlank(title)) {
            title = StringUtils.trim(title);
            title = "%" + title + "%";
        } else {
            title = null;
        }
        List<Good> list = goodMapper.getGood((page - 1) * 10, limit, title, isused, issaled);
        Integer count = goodMapper.getGoodCount(title, isused, issaled);
        return new DataResponse<List<Good>>(0, "", count, list);
    }

    @Override
    public ServerResponse deleteGood(String gid, String uid) {
        Count count = countMapper.selectByPrimaryKey(uid);
        count.setGcount(count.getGcount() - 1);
        Integer upd_result = countMapper.updateByPrimaryKey(count);
        if (Const.OP_SUCCESS == upd_result) {
            Integer del_result = goodMapper.deleteByPrimaryKey(gid);
            if (Const.OP_SUCCESS == del_result) {
                return ServerResponse.createBySuccessMessage("删除商品成功！");
            } else {
                return ServerResponse.createBySuccessMessage("更新成功&删除商品失败！");
            }
        } else {
            return ServerResponse.createByErrorMessage("更新用户数据失败！");
        }
    }
}
