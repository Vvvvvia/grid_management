package com.tang.controller;


import com.tang.dao.SysLogsRepository;
import com.tang.dao.SysUserRepository;
import com.tang.entity.SysLogs;
import com.tang.interceptor.ResponseBean;
import com.tang.interceptor.UnicomResponseEnums;
import com.tang.service.SysLogService;
import com.tang.util.MyDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "系统日志")
@RestController
@RequestMapping("/logs")
public class SysLogsController {

	@Autowired
	private SysLogService sysLogService;

	@GetMapping
//	@PreAuthorize("hasAuthority('sys:log:query')")
	@ApiOperation(value = "日志列表")

	public ResponseBean<Page<SysLogs>> findAll(
			@RequestParam(value = "page",defaultValue = "0",required = false) Integer page,
			@RequestParam(value = "start",required = false) String start,
			@RequestParam(value = "end",required = false) String end,
			@RequestParam(value = "name",required = false) String name,
			@RequestParam(value = "flag",required = false) Boolean flag,
			@RequestParam(value = "content",required = false) String content){

		return  new ResponseBean(true,
				sysLogService.findAll(new PageRequest(page,10,new Sort(Sort.Order.desc("createTime"))),
						MyDateFormat.dateFormat(start),MyDateFormat.dateFormat(end),content,name,flag),
				UnicomResponseEnums.SUCCESS_OPTION);
	}

}
