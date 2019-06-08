package com.mint.service.impl;

import com.mint.common.DataResponse;
import com.mint.common.ServerResponse;
import com.mint.dao.OperationMapper;
import com.mint.pojo.Operation;
import com.mint.service.IOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("iOperationService")
public class OperationServiceImpl implements IOperationService {

    @Autowired
    private OperationMapper operationMapper;

    @Override
    public DataResponse<List<Operation>> getAllOperation() {
        List<Operation> list=operationMapper.getAllOperation();
        Integer count=operationMapper.getAllOperationCount();
        return new DataResponse<List<Operation>>(0, "", count, list);
    }
}
