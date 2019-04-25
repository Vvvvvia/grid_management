package com.tang.controller;


import com.tang.config.LogAnnotation;
import com.tang.entity.RentRecords;
import com.tang.interceptor.ResponseBean;
import com.tang.interceptor.UnicomResponseEnums;
import com.tang.service.RentRecordsService;
import com.tang.util.MyDateFormat;

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

@Api(tags = "租赁记录")
@RestController
@RequestMapping("/rentRecords")
public class RentRecordsController {
    @Resource
    private RentRecordsService rentRecordsService;

    //通过id查找*
    @ApiOperation(value = "通过ID查询")
    @GetMapping("/{id}")
    public ResponseBean<Optional<RentRecords>> findByID(@PathVariable Integer id){
       return new ResponseBean(true,
               rentRecordsService.findById(id), UnicomResponseEnums.SUCCESS_OPTION);
    }

    //通过格主查找*
    @ApiOperation(value = "通过格主ID查询")
    @GetMapping("/tenant/{id}/{page}")
    public ResponseBean<RentRecords> findByTenantID(@PathVariable(name = "id") Integer id,
                                                    @PathVariable(name = "page")Integer page){
        return new ResponseBean(true,
                rentRecordsService.findByTenantId(new PageRequest(page,10,new Sort(Sort.Order.desc("date"))),id),
                UnicomResponseEnums.SUCCESS_OPTION);
    }

    //通过格子查找*
    @ApiOperation(value = "通过格子ID查询")
    @GetMapping("/grid/{id}/{page}")
    public ResponseBean<RentRecords> findByGridID(@PathVariable(name = "id") Integer id,
                                                    @PathVariable(name = "page")Integer page){
        return new ResponseBean(true,
                rentRecordsService.findByGridId(new PageRequest(page,10,new Sort(Sort.Order.desc("date"))),id),
                UnicomResponseEnums.SUCCESS_OPTION);
    }

    //查找全部*
    @ApiOperation(value = "分页查询全部")
    @GetMapping
    public ResponseBean<Page<RentRecords>>findAll(
            @RequestParam(value = "page",defaultValue = "0",required = false) Integer page,
            @RequestParam(value = "gridName",required = false)String gridName,
            @RequestParam(value = "tenantName",required = false)String tenantName,
            @RequestParam(value = "status",required = false) Boolean status,
            @RequestParam(value = "type",required = false) String type,
            @RequestParam(value = "start",required = false) String start,
            @RequestParam(value = "end",required = false) String end

            ){
        return new ResponseBean(true,
                rentRecordsService.findAll(new PageRequest(page,10,new Sort(Sort.Order.desc("date"))),status,type,tenantName,gridName,
                        MyDateFormat.dateFormat(start),MyDateFormat.dateFormat(end)),
                UnicomResponseEnums.SUCCESS_OPTION);
    }

    //删除
    @ApiOperation(value = "删除租赁记录")
    @LogAnnotation(module = "删除租赁记录")
    @DeleteMapping("/{id}")
    public ResponseBean<Boolean> deleteById(@PathVariable Integer id){
        return new ResponseBean(true,
                rentRecordsService.delete(id), UnicomResponseEnums.SUCCESS_OPTION);
    }

    //批量删除
    @ApiOperation(value = "批量删除租赁记录")
    @DeleteMapping
    @LogAnnotation(module = "删除租赁记录")
    public ResponseBean<Boolean> deleteByIdIn(@RequestBody List<Integer> list){
        return new ResponseBean(true,
                rentRecordsService.deleteByIdIn(list), UnicomResponseEnums.SUCCESS_OPTION);
    }
}
