package com.tang.config;


import com.tang.dao.SysLogsRepository;
import com.tang.entity.SysLogs;
import com.tang.service.SysLogService;
import com.tang.util.UserUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;


/**
 * 统一日志处理
 *
 */
@Aspect
@Component
public class LogAdvice {

	@Autowired
	private SysLogsRepository sysLogsRepository;

	@Around(value = "@annotation(com.tang.config.LogAnnotation)")
	public Object logSave(ProceedingJoinPoint joinPoint) throws Throwable {
		SysLogs sysLogs = new SysLogs();
        sysLogs.setUserId(UserUtil.getLoginUser().getId()); // 设置当前登录用户
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

		String module = null;
		LogAnnotation logAnnotation = methodSignature.getMethod().getDeclaredAnnotation(LogAnnotation.class);
		module = logAnnotation.module();
//		if (StringUtils.isEmpty(module)) {
//			ApiOperation apiOperation = methodSignature.getMethod().getDeclaredAnnotation(ApiOperation.class);
//			if (apiOperation != null) {
//				module = apiOperation.value();
//			}
//		}

		if (StringUtils.isEmpty(module)) {
			throw new RuntimeException("没有指定日志module");
		}
		sysLogs.setContent(module);

		try {
			Object object = joinPoint.proceed();
			sysLogs.setFlag(true);
			return object;
		} catch (Exception e) {
			sysLogs.setFlag(false);
			sysLogs.setContent(e.getMessage());
			throw e;
        } finally {

			Timestamp timestamp = new Timestamp(System.currentTimeMillis()) ;
			sysLogs.setCreateTime(timestamp);
			System.out.println(sysLogs);
			if (sysLogs.getUserId() != null) {
                sysLogsRepository.save(sysLogs);
            }
        }

	}
}
