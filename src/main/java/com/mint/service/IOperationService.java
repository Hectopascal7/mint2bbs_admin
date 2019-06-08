package com.mint.service;

import com.mint.common.DataResponse;
import com.mint.common.ServerResponse;
import com.mint.pojo.Operation;

import java.util.List;

public interface IOperationService {
    DataResponse<List<Operation>> getAllOperation();
}
