package com.tang.controller;


import com.tang.config.LogAnnotation;
import com.tang.entity.Shop;
import com.tang.interceptor.ResponseBean;
import com.tang.interceptor.UnicomResponseEnums;
import com.tang.service.ShopService;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "挂靠商铺管理")
@RestController
@RequestMapping("/shops")
public class ShopController {
    @Resource
    private ShopService shopService;

    //通过id查找
    @ApiOperation(value = "通过ID查询")
    @GetMapping("/{id}")
    public ResponseBean<Optional<Shop>> findByID(@PathVariable Integer id){

        Optional<Shop> good = shopService.findById(id);

       return new ResponseBean(true,
               shopService.findById(id), UnicomResponseEnums.SUCCESS_OPTION);
    }

    //查找全部 *
    @ApiOperation(value = "分页查询全部")
    @GetMapping
    public ResponseBean<Page<Shop>>findAll(
            @RequestParam(value = "page",defaultValue = "0",required = false) Integer pageNum,
            @RequestParam(value = "address",required = false) String address,
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "phone",required = false)String phone){
        return new ResponseBean<Page<Shop>>(true,
                shopService.findAll(new PageRequest(pageNum,10),
                        name,phone,address),
                UnicomResponseEnums.SUCCESS_OPTION);
    }

    //添加 *
    @ApiOperation(value = "添加挂靠商铺")
    @LogAnnotation(module = "添加挂靠商铺")
    @PostMapping
    public ResponseBean<Shop> addOne(@RequestBody Shop shop){

        return new ResponseBean(true,shopService.save(shop)
                ,UnicomResponseEnums.SUCCESS_OPTION);
    }

    //更新 *
    @ApiOperation(value = "更新商铺信息")
    @LogAnnotation(module = "更新挂靠商铺信息")
    @PutMapping
    public ResponseBean<Shop> updateOne(@RequestBody Shop shop){
        return new ResponseBean<Shop>(true,shopService.save(shop),
                UnicomResponseEnums.SUCCESS_OPTION);
    }
    //删除 *
    @ApiOperation(value = "删除挂靠商铺")
    @LogAnnotation(module = "删除挂靠商铺")
    @DeleteMapping("/{id}")
    public ResponseBean<Boolean> deleteOne(@PathVariable Integer id){
        Boolean flag = shopService.delete(id);
        return new ResponseBean(flag,flag,
                UnicomResponseEnums.SUCCESS_OPTION);
    }
    //批量删除 *
    @ApiOperation(value = "批量删除挂靠商铺")
    @LogAnnotation(module = "删除挂靠商铺")
    @DeleteMapping
    public ResponseBean<Boolean> deleteSelected(@RequestBody List<Integer> list){
        Boolean flag = shopService.deleteByIdIn(list);
        return new ResponseBean(flag,flag,
                UnicomResponseEnums.SUCCESS_OPTION);
    }
}
