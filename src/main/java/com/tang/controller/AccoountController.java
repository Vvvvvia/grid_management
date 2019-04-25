package com.tang.controller;


import com.tang.entity.Account;
import com.tang.interceptor.ResponseBean;
import com.tang.interceptor.UnicomResponseEnums;
import com.tang.service.AccountService;
import com.tang.util.MyDateFormat;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "系统收支")
@RequestMapping("/accounts")
public class AccoountController {

    @Autowired
    AccountService accountService;

//    *查询全部
//    @PreAuthorize("hasAuthority('sys:account:query')")
    @GetMapping
    @ApiOperation(value = "分页查询全部")
    public ResponseBean<Page<Account>> findAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "start",required = false) String start,
            @RequestParam(value = "end",required = false) String end){
        System.out.println(start+" "+end);
        return new ResponseBean(true,
                accountService.findAllMulti(page,
                        MyDateFormat.dateFormat(start),MyDateFormat.dateFormat(end)),
                UnicomResponseEnums.SUCCESS_OPTION);
    }
//    *按日期查询
    @ApiOperation(value = "按日期查询")
    @GetMapping("/{date}")
    public ResponseBean<Account> findByDate(@PathVariable String date){
        Date std_date = MyDateFormat.dateFormat(date);
        return new ResponseBean<Account>(true,
                accountService.findByDate(std_date),
                UnicomResponseEnums.SUCCESS_OPTION);
    }

}
