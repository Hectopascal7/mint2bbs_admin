package com.mint.service.impl;

import com.mint.common.Const;
import com.mint.common.DataResponse;
import com.mint.common.ServerResponse;
import com.mint.dao.OperationMapper;
import com.mint.dao.ResidentMapper;
import com.mint.dao.UserMapper;
import com.mint.pojo.Operation;
import com.mint.pojo.Resident;
import com.mint.pojo.User;
import com.mint.service.IResidentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("/iResidentService")
public class ResidentServiceImpl implements IResidentService {

    @Autowired
    private ResidentMapper residentMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OperationMapper operationMapper;

    // 获取住户信息（非条件查询）
    @Override
    public DataResponse<List<Resident>> getResident(int page, int limit) {
        List<Resident> list = residentMapper.getResident((page - 1) * 10, limit);
        Integer count = residentMapper.getResidentCount();
        return new DataResponse<>(0, "", count, list);
    }

    // 录入住户信息
    @Override
    public ServerResponse addResident(Resident resident, HttpSession httpSession) {
        resident.setUid(UUID.randomUUID().toString());
        Integer result = residentMapper.insert(resident);
        if (1 == result) {
            String bid = UUID.randomUUID().toString();
            User opuser = (User) httpSession.getAttribute(Const.CURRENT_USER);
            Date btime = new Date(System.currentTimeMillis());
            String note = "【添加】" + "【住户】：" + resident.toString() + "。";
            Operation operation = new Operation(bid, opuser.getUid(), resident.getUid(), btime, Const.OPERATION_TYPE_ADD, Const.OPERATION_OBJECT_RESIDENT, note);
            operationMapper.insert(operation);
            return ServerResponse.createBySuccessMessage("录入成功!");
        } else {
            return ServerResponse.createByErrorMessage("录入失败!");
        }
    }

    // 获取指定的住户信息
    @Override
    public ServerResponse<Resident> getResidentInfo(String uid) {
        Resident resident = residentMapper.selectByPrimaryKey(uid);
        return ServerResponse.createBySuccess(resident);
    }

    // 更新住户信息
    @Override
    public ServerResponse<String> updateResident(Resident resident, HttpSession httpSession) {
        Resident orResident = residentMapper.selectByPrimaryKey(resident.getUid());
        Integer result = residentMapper.updateByPrimaryKey(resident);
        if (1 == result) {
            String bid = UUID.randomUUID().toString();
            User opuser = (User) httpSession.getAttribute(Const.CURRENT_USER);
            Date btime = new Date(System.currentTimeMillis());
            String note = "【更新】" + "【住户】：【原信息】——" + orResident.toString() + "。【新信息】——" + resident.toString() + "。";
            Operation operation = new Operation(bid, opuser.getUid(), resident.getUid(), btime, Const.OPERATION_TYPE_UPDATE, Const.OPERATION_OBJECT_RESIDENT, note);
            operationMapper.insert(operation);
            return ServerResponse.createBySuccessMessage("修改住户信息成功!");
        } else {
            return ServerResponse.createByErrorMessage("修改住户信息失败!");
        }
    }

    // 删除住户信息，更新用户状态为注销
    @Override
    public ServerResponse deleteResident(String uid, HttpSession httpSession) {
        // 检查该住户是否注册了社区用户
        User user = userMapper.selectByPrimaryKey(uid);
        System.out.println("员" + user.toString());
        Resident resident = residentMapper.selectByPrimaryKey(uid);
        String bid = UUID.randomUUID().toString();
        User opuser = (User) httpSession.getAttribute(Const.CURRENT_USER);
        Date btime = new Date(System.currentTimeMillis());
        if (null == user) {
            Integer delResult = residentMapper.deleteByPrimaryKey(uid);
            if (delResult == 1) {
                String note = "【删除】" + "【住户】：【原信息】——" + resident.toString() + "。该住户未注册论坛用户。";
                Operation operation = new Operation(bid, opuser.getUid(), resident.getUid(), btime, Const.OPERATION_TYPE_DELETE, Const.OPERATION_OBJECT_RESIDENT, note);
                operationMapper.insert(operation);
                return ServerResponse.createBySuccessMessage("删除住户信息成功!");
            } else {
                return ServerResponse.createByErrorMessage("删除住户信息失败!");
            }
        } else {
            // 设置状态为Const.USER_STATUS_LOGOUT-2-注销
            User nowUser = new User(user.getUid(), user.getLoginid(), user.getPassword(), user.getNickname(), user.getRole(), user.getSex(), user.getBirthday(), user.getLicense(), user.getPoint(), user.getJointime(), user.getEmail(), user.getSignature(), Const.USER_STATUS_LOGOUT, user.getProfile());
            Integer updResult = userMapper.updateByPrimaryKey(nowUser);
            if (updResult == 1) {
                Integer delResult = residentMapper.deleteByPrimaryKey(uid);
                if (delResult == 1) {
                    String note = "【删除】" + "【住户】：【原住户信息】——" + resident.toString() + "。【原用户信息】——" + user.toString() + "。【现用户信息】——" + nowUser.toString() + "。";
                    Operation operation = new Operation(bid, opuser.getUid(), resident.getUid(), btime, Const.OPERATION_TYPE_DELETE, Const.OPERATION_OBJECT_RESIDENT, note);
                    operationMapper.insert(operation);
                    return ServerResponse.createBySuccessMessage("更新成功&删除住户信息成功!");
                } else {
                    return ServerResponse.createByErrorMessage("更新成功&删除住户信息失败!");
                }
            } else {
                return ServerResponse.createByErrorMessage("更新用户信息失败!");
            }
        }
    }

    // 获取住户信息（条件查询）
    @Override
    public DataResponse<List<Resident>> getResidentWithCondition(int page, int limit, String name, String phone, String idcnum, String nickname) {
        String uid = null;
        if (!StringUtils.isBlank(nickname)) {
            nickname = StringUtils.trim(nickname);
            uid = userMapper.getUidByNickname(nickname);
            if (!StringUtils.isBlank(uid)) {
                uid = StringUtils.trim(uid);
            } else {
                uid = "";
            }
        }
        if (!StringUtils.isBlank(name)) {
            name = StringUtils.trim(name);
        } else {
            name = null;
        }
        if (!StringUtils.isBlank(phone)) {
            phone = StringUtils.trim(phone);
        } else {
            phone = null;
        }
        if (!StringUtils.isBlank(idcnum)) {
            idcnum = StringUtils.trim(idcnum);
        } else {
            idcnum = null;
        }
        List<Resident> list = residentMapper.getResidentWithCondition((page - 1) * limit, limit, name, phone, idcnum, uid);
        Integer count = residentMapper.getResidentCountWithCondition(name, phone, idcnum, uid);
        return new DataResponse<>(0, "", count, list);
    }

    // 通过住户住所获取住户信息
    @Override
    public DataResponse<List<Resident>> getResidentByHouse(int page, int limit, String building, String unit, String floor, String room) {
        building = textHandle(building);
        unit = textHandle(unit);
        floor = textHandle(floor);
        room = textHandle(room);
        List<Resident> list = residentMapper.getResidentByHouse((page - 1) * limit, limit, building, unit, floor, room);
        Integer count = residentMapper.getResidentCountByHouse(building, unit, floor, room);
        return new DataResponse<>(0, "", count, list);
    }

    // 文本处理函数，空值条件下返回null，非空条件下去除前后空格
    public static String textHandle(String text) {
        if (!StringUtils.isBlank(text)) {
            text = StringUtils.trim(text);
        } else {
            text = null;
        }
        return text;
    }
}
