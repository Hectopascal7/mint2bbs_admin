package com.mint.controller;


import com.mint.common.DataResponse;
import com.mint.common.ServerResponse;
import com.mint.pojo.Operation;
import com.mint.pojo.Resident;
import com.mint.service.IOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/operation")
public class OperationController {

    @Autowired
    private IOperationService iOperationService;


    @RequestMapping(value = "getAllOperation.do")
    @ResponseBody
    public DataResponse<List<Operation>> getAllOperation() {
        return iOperationService.getAllOperation();
    }

}
