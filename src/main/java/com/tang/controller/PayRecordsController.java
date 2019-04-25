package com.tang.controller;

import com.tang.entity.UserPayRecords;
import com.tang.interceptor.ResponseBean;
import com.tang.interceptor.UnicomResponseEnums;
import com.tang.service.PayRecordsService;
import com.tang.util.MyDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "结账记录")
@RestController
@RequestMapping("/payRecords")
public class PayRecordsController {

    @Autowired
    private PayRecordsService payRecordsService;


    @ApiOperation(value = "通过员工ID查询")
    @GetMapping("/user")
    public ResponseBean<Page<UserPayRecords>>findUserPayRecords(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "start",required = false) String start,
            @RequestParam(value = "end",required = false) String end,
            @RequestParam(value = "id",required = false) Integer id
    ){
        return new ResponseBean(true,
                payRecordsService.findUserPayRecords(MyDateFormat.dateFormat(start),
                        MyDateFormat.dateFormat(end),id,new PageRequest(page,10,new Sort(Sort.Order.desc("createTime"))))
                , UnicomResponseEnums.SUCCESS_OPTION);
    }

    @ApiOperation(value = "通过格主ID查询")
    @GetMapping("/tenant")
    public ResponseBean<Page<UserPayRecords>>findTenantPayRecords(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "start",required = false) String start,
            @RequestParam(value = "end",required = false) String end,
            @RequestParam(value = "id",required = false) Integer id
    ){
        return new ResponseBean(true,
                payRecordsService.findTenantPayRecords(MyDateFormat.dateFormat(start),
                        MyDateFormat.dateFormat(end),id,new PageRequest(page,10,new Sort(Sort.Order.desc("createTime"))))
                , UnicomResponseEnums.SUCCESS_OPTION);
    }

}
