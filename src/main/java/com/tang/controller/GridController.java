package com.tang.controller;


import com.tang.config.LogAnnotation;
import com.tang.dao.RentRecordsRepository;
import com.tang.entity.Grid;
import com.tang.entity.RentRecords;
import com.tang.interceptor.ResponseBean;
import com.tang.interceptor.UnicomResponseEnums;
import com.tang.service.GridService;
import com.tang.service.RentRecordsService;
import com.tang.util.MyDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "格子铺管理")
@RestController
@RequestMapping("/grids")
public class GridController {
    @Resource
    private GridService gridService;

    @Autowired
    private RentRecordsService rentRecordsService;

    //通过id查找*
    @ApiOperation(value = "通过ID查询")
    @GetMapping("/{id}")
    public ResponseBean<Optional<Grid>> findByID(@PathVariable Integer id){
       return new ResponseBean(true,
               gridService.findById(id), UnicomResponseEnums.SUCCESS_OPTION);
    }

    //查找全部*
    @GetMapping
    @ApiOperation(value = "分页查询全部")
    public ResponseBean<Page<Grid>>findAll(
            @RequestParam(value = "page",defaultValue = "0",required = false) Integer page,
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "shopName",required = false) String shopName,
            @RequestParam(value = "status",required = false) Boolean status
            ){
        return new ResponseBean<Page<Grid>>(true,
                gridService.findAll(new PageRequest(page,10),name,shopName,status),
                UnicomResponseEnums.SUCCESS_OPTION);
    }

    //添加
    @ApiOperation(value = "添加格子铺")
    @LogAnnotation(module = "添加格子铺")
    @PostMapping
    public ResponseBean<Grid> addOne(@RequestBody Grid grid){

        return new ResponseBean(true,gridService.save(grid)
                ,UnicomResponseEnums.SUCCESS_OPTION);
    }

    //更新*
    @ApiOperation(value = "更新格子铺信息")
    @LogAnnotation(module = "更新格子铺信息")
    @PutMapping
    public ResponseBean<Grid> updateOne(@RequestBody Grid grid){
        return new ResponseBean<Grid>(true,gridService.save(grid),
                UnicomResponseEnums.SUCCESS_OPTION);
    }
    //删除*
    @ApiOperation(value = "删除格子铺")
    @LogAnnotation(module = "删除格子铺")
    @DeleteMapping("/{id}")
    public ResponseBean<Boolean> deleteOne(@PathVariable Integer id){

        return new ResponseBean(true,gridService.delete(id),
                UnicomResponseEnums.SUCCESS_OPTION);
    }
    @ApiOperation(value = "批量删除格子铺")
    @LogAnnotation(module = "删除格子铺")
    //批量删除
    @DeleteMapping
    public ResponseBean<Boolean> deleteSelected(@RequestBody ArrayList<Integer> list){


        return new ResponseBean(true,gridService.deleteByIdIn(list),
                UnicomResponseEnums.SUCCESS_OPTION);
    }

    //解除租赁
    @ApiOperation(value = "格子解租")
    @LogAnnotation(module = "格子解租")
    @Transactional
    @PostMapping("/relieve")
    public ResponseBean<Integer>relieve(@RequestBody RentRecords record){
        record.setStatus(false);
        rentRecordsService.save(record);
        return new ResponseBean(true,gridService.relieve(record.getGridId()),
                UnicomResponseEnums.SUCCESS_OPTION);
    }

    //租赁
    @ApiOperation(value = "格子租赁")
    @LogAnnotation(module = "格子租赁")
    @Transactional
    @PostMapping("/rent")
    public ResponseBean<RentRecords>rent(@RequestBody RentRecords rentRecords){

        Integer gridId = rentRecords.getGridId();
        //如果格子为空 那么参数不合法
        if (gridId==null||rentRecords.getTenantId()==null){
            return new ResponseBean<RentRecords>(false,UnicomResponseEnums.ILLEGAL_ARGUMENT);
        }
        rentRecords.setStatus(true);
        rentRecords.setDate(new Timestamp(new Date().getTime()));
        RentRecords records = rentRecordsService.save(rentRecords);
        gridService.rent(gridId,records.getId());
        return new ResponseBean<RentRecords>(true,records,UnicomResponseEnums.SUCCESS_OPTION);
    }
}
