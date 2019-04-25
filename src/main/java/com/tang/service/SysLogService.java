package com.tang.service;


import com.tang.dao.SysLogsRepository;
import com.tang.dao.SysUserRepository;

import com.tang.entity.SysLogs;


import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


@Service
public class SysLogService {

	private static final Logger log = LoggerFactory.getLogger("adminLogger");

	@Autowired
	private SysLogsRepository sysLogsRepository;

	@Autowired
	private SysUserRepository sysUserRepository;

//	/**
//	 * 2018.05.12将该方法改为异步,用户由调用者设置
//	 *
//	 * @param sysLogs
//	 * @see com.boot.security.server.advice.LogAdvice
//	 */
	@Async
	public void save(SysLogs sysLogs) {
//		SysUser user = UserUtil.getLoginUser();
		if (sysLogs == null || sysLogs.getSysUserByUserId() == null || 
				sysLogs.getSysUserByUserId().getId() == null) {
			return;
		}

//		sysLogs.setUser(user);
		sysLogsRepository.save(sysLogs);
	}

	@Async
	public void save(Integer userId, String content,Boolean flag) {
		SysLogs sysLogs = new SysLogs();
		sysLogs.setContent(content);
		sysLogs.setUserId(userId);
		sysLogs.setFlag(flag);
		sysLogsRepository.save(sysLogs);
	}

	@Scheduled(cron = "0 0 0 * * ?")
	//每天凌晨0点执行
	public void deleteLogs() {
		Date date = DateUtils.addMonths(new Date(), -3);
//		String time = DateFormatUtils.format(date, DateFormatUtils.ISO_DATETIME_FORMAT.getPattern());

		int n = sysLogsRepository.deleteByCreateTimeBefore(date);
		log.info("删除{}之前日志{}条", date, n);
	}

	public Page<SysLogs> findAll(Pageable pageable, Date start, Date end, String content, String name, Boolean flag){
		Specification<SysLogs> sp = new Specification<SysLogs>() {
			@Nullable
			@Override
			public Predicate toPredicate(Root<SysLogs> root, CriteriaQuery<?> criteriaQuery,
										 CriteriaBuilder criteriaBuilder) {

				List<Predicate> predicates = new ArrayList<>();
				if(null != start){
					predicates.add(criteriaBuilder.greaterThanOrEqualTo(
							root.get("createTime"), start));
				}
				if(null != end){
					predicates.add(criteriaBuilder.lessThanOrEqualTo(
							root.get("createTime"), end));
				}
				if(null != content&&!content.equals("")){
					predicates.add(criteriaBuilder.equal(root.get("content"), content));
				}
				if (null!=flag){
					predicates.add(criteriaBuilder.equal(root.get("flag"),flag));
				}
				if (null!=name&&!name.equals("")){
					List<Integer> gl = sysUserRepository.findIdByNameLike("%"+name+"%");
					if (gl!=null&&gl.size()!=0){
						CriteriaBuilder.In<Integer> in = criteriaBuilder.in(root.get("userId"));
						for (int i=0;i<gl.size();i++){
							in.value(gl.get(i));
						}
						predicates.add(in);
					}else predicates.add(criteriaBuilder.equal(root.get("id"),0));
				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		return sysLogsRepository.findAll(sp,pageable);
	}

}
