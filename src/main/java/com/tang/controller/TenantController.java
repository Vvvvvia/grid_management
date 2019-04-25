package com.tang.controller;


import com.tang.config.LogAnnotation;
import com.tang.entity.Tenant;
import com.tang.interceptor.ResponseBean;
import com.tang.interceptor.UnicomResponseEnums;
import com.tang.service.TenantService;

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
import java.util.Optional;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "格主管理")
@RestController
@RequestMapping("/tenants")
//@CrossOrigin
public class TenantController {
    @Resource
    private TenantService tenantService;

    //通过id查找*
    @ApiOperation(value = "通过ID查询")
    @GetMapping("/{id}")
    public ResponseBean<Optional<Tenant>> findByID(@PathVariable Integer id){
       return new ResponseBean(true,
               tenantService.findById(id), UnicomResponseEnums.SUCCESS_OPTION);
    }
    //通过phone查找*
    @ApiOperation(value = "通过手机查询")
    @GetMapping("/phone/{phone}")
    public ResponseBean<Tenant> findByID(@PathVariable String phone){
        return new ResponseBean(true,
                tenantService.findByPhone(phone), UnicomResponseEnums.SUCCESS_OPTION);
    }

    //查找全部*

    @ApiOperation(value = "分页查询全部")
    @GetMapping
    public ResponseBean<Page<Tenant>>findAll(
            @RequestParam(value = "page",defaultValue = "0",required = false) Integer page,
            @RequestParam(value = "sex",required = false) String sex,
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "phone",required = false)String phone
            ){
        return new ResponseBean(true,
                tenantService.findAll(new PageRequest(page,10),
                        name,phone,sex),
                UnicomResponseEnums.SUCCESS_OPTION);
    }

    //添加*
    @ApiOperation(value = "添加格主")
    @LogAnnotation(module = "添加格主")
    @PostMapping
    public ResponseBean<Tenant> addOne(@RequestBody Tenant tenant){

        return new ResponseBean(true,tenantService.save(tenant)
                ,UnicomResponseEnums.SUCCESS_OPTION);
    }

    //更新*
    @ApiOperation(value = "更新格主信息")
    @LogAnnotation(module = "更新格主信息")
    @PutMapping
    public ResponseBean<Tenant> updateOne(@RequestBody Tenant tenant){
        return new ResponseBean<Tenant>(true,tenantService.save(tenant),
                UnicomResponseEnums.SUCCESS_OPTION);
    }
    //删除*
    @ApiOperation(value = "删除格主")
    @LogAnnotation(module = "删除格主")
    @DeleteMapping("/{id}")
    public ResponseBean<Boolean> deleteOne(@PathVariable Integer id){

        return new ResponseBean(true,tenantService.delete(id),
                UnicomResponseEnums.SUCCESS_OPTION);
    }
    @ApiOperation(value = "批量删除格主")
    @LogAnnotation(module = "删除格主")
    //批量删除
    @DeleteMapping
    public ResponseBean<Boolean> deleteSelected(@RequestBody ArrayList<Integer> list){

        return new ResponseBean(true,tenantService.deleteByIdIn(list),UnicomResponseEnums.SUCCESS_OPTION);
    }
}
