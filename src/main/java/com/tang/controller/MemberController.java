package com.tang.controller;


import com.tang.config.LogAnnotation;
import com.tang.entity.Member;
import com.tang.interceptor.ResponseBean;
import com.tang.interceptor.UnicomResponseEnums;
import com.tang.service.MemberService;
import com.tang.util.MyDateFormat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import java.util.Date;
import java.util.Optional;

import javax.annotation.Resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "会员管理")
@RestController
@RequestMapping("/members")
//@CrossOrigin
public class MemberController {
    @Resource
    private MemberService memberService;

    //通过id查找*
    @ApiOperation(value = "通过ID查询")
    @GetMapping("/{id}")
    public ResponseBean<Optional<Member>> findByID(@PathVariable Integer id){
       return new ResponseBean(true,
               memberService.findById(id), UnicomResponseEnums.SUCCESS_OPTION);
    }
    //通过phone查找*
    @ApiOperation(value = "通过手机查询")
    @GetMapping("/phone/{phone}")
    public ResponseBean<Member> findByPhone(@PathVariable String phone){
        return new ResponseBean(true,
                memberService.findByPhone(phone), UnicomResponseEnums.SUCCESS_OPTION);
    }

    //查找全部*
    @ApiOperation(value = "分页查询全部")
    @GetMapping
    public ResponseBean<Page<Member>>findAll(
            @RequestParam(value = "page",defaultValue = "0",required = false) Integer page,
            @RequestParam(value = "start",required = false) String start,
            @RequestParam(value = "end",required = false) String end,
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "phone",required = false)String phone
            ){
        System.out.println(start+" "+end);
        return new ResponseBean(true,
                memberService.findAll(new PageRequest(page,10),
                        MyDateFormat.dateFormat(start),
                        MyDateFormat.dateFormat(end),name,phone),
                UnicomResponseEnums.SUCCESS_OPTION);
    }

    //添加*
    @ApiOperation(value = "添加会员")
    @LogAnnotation(module = "添加会员")
    @PostMapping
    public ResponseBean<Object> addOne(@RequestBody Member member){


        if (memberService.findByPhone(member.getPhone())!=null){
            return  new ResponseBean(false,"phone"
                    ,UnicomResponseEnums.INVALID_MOBILE);
        }
        return new ResponseBean(true,memberService.save(member)
                ,UnicomResponseEnums.SUCCESS_OPTION);
    }

    //更新*
    @ApiOperation(value = "跟新会员信息")
    @LogAnnotation(module = "更新会员信息")
    @PutMapping
    public ResponseBean<Member> updateOne(@RequestBody Member member){

        Member m =  memberService.findByPhone(member.getPhone());
        if (m!=null&&m.getId()!=member.getId()){
            return  new ResponseBean(false,"phone"
                    ,UnicomResponseEnums.INVALID_MOBILE);
        }
        return new ResponseBean(true,memberService.save(member)
                ,UnicomResponseEnums.SUCCESS_OPTION);
    }
    //删除*
    @ApiOperation(value = "删除会员")
    @LogAnnotation(module = "删除会员")
    @DeleteMapping("/{id}")
    public ResponseBean deleteOne(@PathVariable Integer id){
        memberService.delete(id);
        return new ResponseBean(true,
                UnicomResponseEnums.SUCCESS_OPTION);
    }
    //批量删除
    @ApiOperation(value = "批量删除会员")
    @LogAnnotation(module = "删除会员")
    @DeleteMapping
    public ResponseBean deleteSelected(@RequestBody ArrayList<Integer> list){
        memberService.deleteByIdIn(list);
        return new ResponseBean(true,UnicomResponseEnums.SUCCESS_OPTION);
    }
    //更新积分信息
    @ApiOperation(value = "更新积分信息")
    @LogAnnotation(module = "更新积分信息")
    @PutMapping("/score/{id}/{score}")
    public int updateScoreById(@PathVariable("score") Double score,
                               @PathVariable("id") Integer id){
        return memberService.updateScoreById(score,id);
    }
    //更新状态
    @ApiOperation(value = "更新会员状态")
    @LogAnnotation(module = "更新会员状态")
    @PutMapping("/status/{id}/{status}")
    public int updateStatusById(@PathVariable("status")String status,
                                @PathVariable("id") Integer id){

        return memberService.updateStatusById(new Boolean(status),id);
    }

}
