package com.tang.controller;


import com.tang.config.LogAnnotation;
import com.tang.entity.Parameter;
import com.tang.interceptor.ResponseBean;
import com.tang.interceptor.UnicomResponseEnums;
import com.tang.service.ParameterService;


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

@Api(tags = "系统参数设置")
@RestController
@RequestMapping("/paras")
public class PraemeterController {
    @Resource
    private ParameterService parameterService;

    //通过id查找*
    @ApiOperation(value = "通过ID查询")
    @GetMapping("/{id}")
    public ResponseBean
            <Optional<Parameter>> findByID(@PathVariable Integer id){
       return new ResponseBean(true,
               parameterService.findById(id), UnicomResponseEnums.SUCCESS_OPTION);
    }
   
    //更新*
    @ApiOperation(value = "更新系统参数")
    @LogAnnotation(module = "更新系统参数")
    @PutMapping
    public ResponseBean<Parameter> updateOne(@RequestBody Parameter parameter){
        parameter.setId(1);
        return new ResponseBean<Parameter>(true,parameterService.save(parameter),
                UnicomResponseEnums.SUCCESS_OPTION);
    }
//    //删除*
//    @DeleteMapping("/{id}")
//    public ResponseBean deleteOne(@PathVariable Integer id){
//        parameterService.delete(id);
//        return new ResponseBean(true,
//                UnicomResponseEnums.SUCCESS_OPTION);
//    }
}
