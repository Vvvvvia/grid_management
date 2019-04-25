package com.tang.controller;


import com.tang.config.LogAnnotation;
import com.tang.dao.GridSellRecordsRepository;
import com.tang.entity.GridSellRecords;
import com.tang.entity.SellRecords;
import com.tang.entity.TenantSellRecords;
import com.tang.interceptor.ResponseBean;
import com.tang.interceptor.UnicomResponseEnums;
import com.tang.service.SellRecordsService;
import com.tang.util.MyDateFormat;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "销售记录")
@RestController
@RequestMapping("/sellRecords")
public class SellRecordsController {
    @Resource
    private SellRecordsService sellRecordsService;

    //通过id查找*
    @ApiOperation(value = "通过ID查询")
    @GetMapping("/{id}")
    public ResponseBean<Optional<SellRecords>> findByID(@PathVariable Integer id){
       return new ResponseBean(true,
               sellRecordsService.findById(id), UnicomResponseEnums.SUCCESS_OPTION);
    }


    //查找全部gridSellRecords*
    @ApiOperation(value = "通过格子ID查询")
    @GetMapping("/grid")
    public ResponseBean<Page<GridSellRecords>>findAllGridSellRecords(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "start",required = false) String start,
            @RequestParam(value = "end",required = false) String end,
            @RequestParam(value = "id",required = false) Integer id
    ){
        return new ResponseBean(true,
                sellRecordsService.findGridSellRecords(MyDateFormat.dateFormat(start),
                        MyDateFormat.dateFormat(end),id,new PageRequest(page,10,new Sort(Sort.Order.desc("date")))),
                UnicomResponseEnums.SUCCESS_OPTION);
    }

    //查找全部gridSellRecords*
    @ApiOperation(value = "通过格主ID查询")
    @GetMapping("/tenant")
    public ResponseBean<Page<TenantSellRecords>>findAllTenantSellRecords(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "start",required = false) String start,
            @RequestParam(value = "end",required = false) String end,
            @RequestParam(value = "id",required = false) Integer id
    ){
        return new ResponseBean(true,
                sellRecordsService.findTenantSellRecords(MyDateFormat.dateFormat(start),
                        MyDateFormat.dateFormat(end),id,new PageRequest(page,10,new Sort(Sort.Order.desc("date")))),
                UnicomResponseEnums.SUCCESS_OPTION);
    }

    //查找全部*
    @ApiOperation(value = "分页查询全部")
    @GetMapping
    public ResponseBean<Page<SellRecords>>findAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "start",required = false) String start,
            @RequestParam(value = "end",required = false) String end,
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "prefix",required = false)String prefix,
            @RequestParam(value = "status",required = false)Boolean status,
            @RequestParam(value = "type",required = false)Integer type,
            @RequestParam(value = "gridId",required = false)Integer gridId,
            @RequestParam(value = "tenantId",required = false)Integer tenantId,
            @RequestParam(value = "barcode",required = false)String barcode
            ){
        return new ResponseBean(true,
                sellRecordsService.findAll(page, MyDateFormat.dateFormat(start),
                        MyDateFormat.dateFormat(end),name,prefix,status,type,gridId,tenantId,barcode),
                UnicomResponseEnums.SUCCESS_OPTION);
    }
    //删除
    @ApiOperation(value = "删除销售记录")
    @LogAnnotation(module = "删除订单")
    @DeleteMapping("/{id}")
    public ResponseBean deleteById(@PathVariable Integer id){
        sellRecordsService.deleteById(id);
        return new ResponseBean(true,
                UnicomResponseEnums.SUCCESS_OPTION);
    }

    //批量删除
    @ApiOperation(value = "批量删除销售记录")
    @LogAnnotation(module = "删除订单")
    @DeleteMapping
    public ResponseBean deleteByIdIn(@RequestBody List<Integer> list){
        sellRecordsService.deleteByIdIn(list);
        return new ResponseBean(true,
                UnicomResponseEnums.SUCCESS_OPTION);
    }
}
