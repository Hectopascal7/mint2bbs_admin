package com.mint.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonResponse;
import com.mint.common.Const;
import com.mint.common.DataResponse;
import com.mint.common.ServerResponse;
import com.mint.dao.OperationMapper;
import com.mint.dao.ResidentMapper;
import com.mint.dao.UserMapper;
import com.mint.pojo.Operation;
import com.mint.pojo.Resident;
import com.mint.pojo.User;
import com.mint.service.IUserService;
import com.mint.util.PasswordUtil;
import com.mint.util.SMSUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ResidentMapper residentMapper;
    @Autowired
    private OperationMapper operationMapper;

    @Override
    public DataResponse<List<User>> getBBSUser(int page, int limit) {
        List<User> list = userMapper.getBBSUser((page - 1) * 10, limit);
        Integer count = userMapper.getBBSUserCount();
        return new DataResponse(0, "1", count, list);
    }

    @Override
    public ServerResponse login(String loginid, String password) {
        User user = userMapper.login(loginid, password);
        if (null == user) {
            return ServerResponse.createByErrorMessage("用户名密码不匹配，登录失败！");
        } else {
            // 登录id和密码匹配，保障账户安全，将用户实体密码置为空
            user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
            return ServerResponse.createBySuccess("登录成功，正在跳转...", user);
        }
    }

    @Override
    public ServerResponse<String> quit(HttpSession httpSession) {
        httpSession.removeAttribute("user");
        return ServerResponse.createBySuccessMessage("退出成功，正在跳转...");
    }

    @Override
    public ServerResponse<User> getUserInfo(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess(user);
    }

    @Override
    public ServerResponse resetPassword(String uid, HttpSession httpSession) {
        User user = userMapper.selectByPrimaryKey(uid);
        String password = PasswordUtil.randomPassword();
        Integer result = userMapper.resetPassword(uid, password);
        String bid = UUID.randomUUID().toString();
        User opuser = (User) httpSession.getAttribute(Const.CURRENT_USER);
        Date btime = new Date(System.currentTimeMillis());
        if (Const.OP_SUCCESS == result) {
            Resident resident = residentMapper.selectByPrimaryKey(uid);
            JSONObject json = new JSONObject();
            json.put("password", password);
            CommonResponse response = SMSUtil.SendNewPasswordMessage(resident.getPhone(), JSON.toJSONString(json));
            JSONObject re = JSONObject.parseObject(response.getData());
            if ("OK".equals(re.get("Code"))) {
                User nowUser = userMapper.selectByPrimaryKey(uid);
                String note = "【重置】" + "【密码】：【原用户信息】——" + user.toString() + "。【新用户信息】——" + nowUser.toString() + "。";
                Operation operation = new Operation(bid, opuser.getUid(), uid, btime, Const.OPERATION_TYPE_UPDATE, Const.OPERATION_OBJECT_PASSWORD, note);
                operationMapper.insert(operation);
                return ServerResponse.createBySuccessMessage("密码重置成功！新密码将发送到用户绑定手机上。");
            } else {
                return ServerResponse.createByErrorMessage("密码重置成功！密码发送失败。");
            }

        } else {
            return ServerResponse.createByErrorMessage("密码重置失败！");
        }
    }

    @Override
    public ServerResponse deleteUser(String uid) {
        return ServerResponse.createBySuccessMessage("成功了！");
    }

    @Override
    public ServerResponse updateUserStatus(String uid, int status, HttpSession httpSession) {
        Integer result = userMapper.updateUserStatus(uid, status);
        if (Const.OP_ERROR == result) {
            return ServerResponse.createByErrorMessage("设置失败！");
        } else {
            String oid = UUID.randomUUID().toString();
            User u = (User) httpSession.getAttribute(Const.CURRENT_USER);
            Date otime = new Date(System.currentTimeMillis());
            Operation operation = new Operation(oid, uid, u.getUid(), otime, 1, 1, "设置" + uid + "封禁");
            operationMapper.insert(operation);
            return ServerResponse.createBySuccessMessage("设置成功！");
        }
    }

    @Override
    public ServerResponse<HashMap<String, String>> getPublisherInfo(String uid) {
        User user = userMapper.selectByPrimaryKey(uid);
        Resident resident = residentMapper.selectByPrimaryKey(uid);
        HashMap<String, String> map = new HashMap<>();
        map.put("uid", user.getUid());
        map.put("name", resident.getName());
        map.put("phone", resident.getPhone());
        map.put("idcnum", resident.getIdcnum());
        map.put("building", resident.getBuilding());
        map.put("unit", resident.getUnit());
        map.put("floor", resident.getFloor());
        map.put("room", resident.getRoom());
        map.put("loginid", user.getLoginid());
        map.put("nickname", user.getNickname());
        map.put("sex", user.getSex().toString());
        map.put("role", user.getRole().toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        map.put("birthday", sdf.format(user.getBirthday()).toString());
        map.put("license", user.getLicense());
        sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        map.put("jointime", sdf.format(user.getJointime()).toString());
        map.put("point", user.getPoint().toString());
        map.put("email", user.getEmail());
        map.put("signature", user.getSignature());
        map.put("status", user.getStatus().toString());
        return ServerResponse.createBySuccess(map);
    }

    @Override
    public DataResponse<List<User>> getUserWithCondition(int page, int limit, String nickname, Integer status, String loginid) {
        if (!StringUtils.isBlank(nickname)) {
            nickname = StringUtils.trim(nickname);
        } else {
            nickname = null;
        }
        if (!StringUtils.isBlank(loginid)) {
            loginid = StringUtils.trim(loginid);
        } else {
            loginid = null;
        }
        List<User> list = userMapper.getUserWithCondition((page - 1) * limit, limit, nickname, status, loginid);
        Integer count = userMapper.getUserCountWithCondition(nickname, status, loginid);
        return new DataResponse<>(0, "", count, list);
    }

    @Override
    public DataResponse<List<User>> getUserWithPhone(int page, int limit, String phone) {
        if (!StringUtils.isBlank(phone)) {
            phone = StringUtils.trim(phone);
        } else {
            phone = null;
        }
        List<Resident> list = residentMapper.getResidentsByPhone(phone);
        if (0 == list.size()) {
            List<User> userList = userMapper.getBBSUser(page, limit);
            int count = userMapper.getBBSUserCount();
            return new DataResponse<>(0, "", count, userList);
        } else {
            List<User> userList = new ArrayList<>();
            for (Resident r : list) {
                String uid = r.getUid();
                User user = userMapper.selectByPrimaryKey(uid);
                if (null != user) {
                    userList.add(user);
                }
            }
            List<User> users = new ArrayList<>();
            for (int i = (page - 1) * 10; i < (page - 1) * 10 + limit - 1; i++) {
                if (i >= userList.size()) {
                    break;
                }
                users.add(userList.get(i));
            }
            return new DataResponse<>(0, "", userList.size(), users);
        }
    }

}
