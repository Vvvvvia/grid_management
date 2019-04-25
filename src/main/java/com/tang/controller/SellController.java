package com.tang.controller;

import com.tang.config.LogAnnotation;
import com.tang.entity.DTO.SellRecordsDto;
import com.tang.entity.TenantPayRecords;
import com.tang.entity.UserPayRecords;
import com.tang.service.SellService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "销售管理")
@RestController
public class SellController {

    @Autowired
    private SellService sellService;

    @ApiOperation(value = "售货收银")
    @LogAnnotation(module = "售货收银")
    @PostMapping("/sell")
    public Object sell(@RequestBody SellRecordsDto sellRecords){
//        System.out.println(sellRecords);
        return sellService.sellAndcollectMoney(sellRecords.getSellRecordsList());
    }
    @ApiOperation(value = "客户退货")
    @LogAnnotation(module = "客户退货")
    @PostMapping("/return")
    public Object saleReturn(@RequestBody SellRecordsDto sellRecords){
//        System.out.println(sellRecords);
        return sellService.saleReturn(sellRecords.getSellRecordsList());
    }

    @ApiOperation(value = "格主结账")
    @LogAnnotation(module = "格主结账")
    @PostMapping("/pay/tenant")
    public Object tenantPay(@RequestBody TenantPayRecords tenantPayRecords){
        return sellService.tenantPay(tenantPayRecords);
    }

    @ApiOperation(value = "员工结账")
    @LogAnnotation(module = "员工结账")
    @PostMapping("/pay/user")
    public Object userPay(@RequestBody UserPayRecords userPayRecords){
        return sellService.userPay(userPayRecords);
    }

}
