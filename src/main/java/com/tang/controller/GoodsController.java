package com.tang.controller;


import com.tang.config.LogAnnotation;
import com.tang.dao.GoodsRecordsRepository;
import com.tang.entity.Goods;
import com.tang.entity.GoodsRecords;
import com.tang.interceptor.ResponseBean;
import com.tang.interceptor.UnicomResponseEnums;
import com.tang.service.GoodRecordsService;
import com.tang.service.GoodsService;
import com.tang.util.MyDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api(tags = "商品管理")
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Resource
    private GoodsService goodsService;
    @Autowired
    private GoodRecordsService goodRecordsService;

    //通过条码查找
    @ApiOperation(value = "通过条码查询")
    @GetMapping("/{barcode}")
    public ResponseBean<Goods> findByID(@PathVariable String barcode){

        Goods good = goodsService.findByBarcode(barcode);

       return new ResponseBean(true,
               good, UnicomResponseEnums.SUCCESS_OPTION);
    }


    //查找全部 *
    @ApiOperation(value = "分页查询全部")
    @GetMapping
    public ResponseBean<Page<Goods>>findAll(
            @RequestParam(value = "page",defaultValue = "0",required = false) Integer page,
            @RequestParam(value = "start",required = false)String start,
            @RequestParam(value = "end",required =false)String end,
            @RequestParam(value = "status",required = false)Boolean status,
            @RequestParam(value = "name",required =false)String name,
            @RequestParam(value = "gridName",required = false)String gridName,
            @RequestParam(value = "warning",required = false)Boolean warning
            ){
        return new ResponseBean<Page<Goods>>(true,
                goodsService.findAll(new PageRequest(page,10,
                                new Sort(Sort.Order.desc("createTime"))),
                        MyDateFormat.dateFormat(start),MyDateFormat.dateFormat(end),
                        status,name,gridName,warning),
                UnicomResponseEnums.SUCCESS_OPTION);
    }

    //添加 *
    @ApiOperation(value = "添加商品")
    @LogAnnotation(module = "添加商品")
    @PostMapping
    public ResponseBean<Goods> addOne(@RequestBody Goods goods){

        return new ResponseBean(true,goodsService.save(goods)
                ,UnicomResponseEnums.SUCCESS_OPTION);
    }

    //更新 *
    @ApiOperation(value = "更新商品信息")
    @LogAnnotation(module = "更新商品信息")
    @PutMapping
    public ResponseBean<Goods> updateOne(@RequestBody Goods goods){
        return new ResponseBean<Goods>(true,goodsService.save(goods),
                UnicomResponseEnums.SUCCESS_OPTION);
    }
    //更新status *
    @LogAnnotation(module = "更新商品状态")
    @ApiOperation(value = "更新商品状态")
    @PutMapping("/status/{id}/{status}")
    public ResponseBean<Integer> updateStatusById(@PathVariable("id")Integer id,
                                                @PathVariable("status")Boolean status){
        return new ResponseBean(true,goodsService.updateStatusById(id,status),
                UnicomResponseEnums.SUCCESS_OPTION);
    }
    //更新num *
    @ApiOperation(value = "更新商品库存")
    @LogAnnotation(module = "更新商品库存")
    @PutMapping("/num/{id}/{num}")
    public ResponseBean<Integer> updateStatusById(@PathVariable("id")Integer id,
                                                   @PathVariable("num")Integer num){
        return new ResponseBean(true,goodsService.updateNumById(id,num),
                UnicomResponseEnums.SUCCESS_OPTION);
    }
    //删除 *
    @ApiOperation(value = "删除商品")
    @LogAnnotation(module = "删除商品")
    @DeleteMapping("/{id}")
    public ResponseBean deleteOne(@PathVariable Integer id){
        goodsService.delete(id);
        return new ResponseBean(true,
                UnicomResponseEnums.SUCCESS_OPTION);
    }
    //批量删除 *
    @ApiOperation(value = "批量删除商品")
    @LogAnnotation(module = "删除商品")
    @DeleteMapping
    public ResponseBean deleteSelected(@RequestBody ArrayList<Integer> list){
        System.out.println(list);
        goodsService.deleteByIdIn(list);
        return new ResponseBean(true,UnicomResponseEnums.SUCCESS_OPTION);
    }
    //进出货记录
    @GetMapping("/records")
    @ApiOperation(value = "查询进退货记录")
    public ResponseBean<Page<GoodsRecords>> findGoodsRecords(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "start",required = false) String start,
            @RequestParam(value = "end",required = false) String end,
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "prefix",required = false)String prefix,
            @RequestParam(value = "type",required = false)Byte type
    ){
        return new ResponseBean(true,
                goodRecordsService.findAll(page, MyDateFormat.dateFormat(start),
                        MyDateFormat.dateFormat(end),name,prefix,type),
                UnicomResponseEnums.SUCCESS_OPTION);
    }

    //删除
    @ApiOperation(value = "删除进退货记录")
    @LogAnnotation(module = "删除进退货记录")
    @DeleteMapping("/records/{id}")
    public ResponseBean deleteById(@PathVariable Integer id){
        goodRecordsService.deleteById(id);
        return new ResponseBean(true,
                 UnicomResponseEnums.SUCCESS_OPTION);
    }

    //批量删除
    @ApiOperation(value = "批量删除进退货记录")
    @LogAnnotation(module = "删除进退货记录")
    @DeleteMapping("/records")
    public ResponseBean deleteByIdIn(@RequestBody List<Integer> list){
        goodRecordsService.deleteByIdIn(list);
        return new ResponseBean(true,
                 UnicomResponseEnums.SUCCESS_OPTION);
    }
}
