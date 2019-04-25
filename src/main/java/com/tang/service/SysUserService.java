package com.tang.service;

import com.tang.dao.SysRoleUserRepository;
import com.tang.dao.SysUserRepository;
import com.tang.entity.SysRole;
import com.tang.entity.SysRoleUser;

import com.tang.entity.SysUser;
import com.tang.entity.DTO.SysUserDto;
import com.tang.util.MyDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class SysUserService {

	private static final Logger log = LoggerFactory.getLogger("adminLogger");
	@Autowired
	private SysUserRepository userRepository;

	@Autowired
	private SysRoleUserRepository sysRoleUserRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;


	@Transactional
	public SysUser insertUser(SysUserDto user) {

		SysUser user1 = new SysUser();
		user1.setId(user.getId());
		user1.setUserName(user.getUserName());
		user1.setMoney(0.0);
		user1.setPhone(user.getPhone());
		user1.setSex(user.getSex());
		user1.setStatus(new Byte("1"));
		user1.setCreateTime(user.getCreateTime());
		user1.setUpdateTime(new Timestamp(new Date().getTime()));
		user1.setSalary(user.getSalary());
		user1.setPayTime(user.getPayTime());
		String pswd = user.getPassword();
		if (user1.getId()==null){
			pswd=passwordEncoder.encode(pswd);
			user1.setCreateTime(new Timestamp(new Date().getTime()));
			user1.setUpdateTime(user1.getCreateTime());
			user1.setPayTime(user1.getCreateTime());
		}
		user1.setPassword(pswd);
		//插入之后新的user 用于获取id
		SysUser sysUser = userRepository.save(user1);
		//添加角色并保存
		saveUserRoles(sysUser.getId(), user.getRoleIds());
		log.debug("新增用户{}", user.getUserName());
		return user;
	}

	@Transactional
	void saveUserRoles(Integer userId, List<Integer> roleIds) {
		if (roleIds != null) {
			
			sysRoleUserRepository.deleteByUserId(userId);
			
			if (!CollectionUtils.isEmpty(roleIds)) {
				for(int i=0;i<roleIds.size();i++){
					Integer roleId = roleIds.get(i);

					sysRoleUserRepository.insert(userId,roleId, new Timestamp(new Date().getTime()));
				}

			}
		}
	}


	public void changePassword(String username, String oldPassword, String newPassword) {
		SysUser u = userRepository.findByUserName(username);
		if (u == null) {
			throw new IllegalArgumentException("用户不存在");
		}

		if (!passwordEncoder.matches(oldPassword, u.getPassword())) {
			throw new IllegalArgumentException("旧密码错误");
		}

		userRepository.changePassword(u.getId(), passwordEncoder.encode(newPassword));

		log.debug("修改{}的密码", username);
	}

//
//	@Transactional
//	public SysUser update(SysUserDto user) {
//		saveUserRoles(user.getId(), user.getRoleIds());
//		return user;
//	}


	private Integer flag = 1;

	public SysUser findById(Integer id) {
		return userRepository.getOne(id);
	}


	public SysUser findByPhone(String phone) {
		return userRepository.findByPhone(phone);
	}


	public void delete(Integer id) {
		userRepository.deleteById(id);
	}
	@Transactional
	public void deleteByIdIn(List<Integer> list){
		userRepository.deleteByIdIn(list);
	}

	@Transactional
	public Page<SysUser> findAll(Pageable pageable, Date start,Date end,
								 String name,String phone,Boolean account) {
		if (account&&flag==1){
			try {
				updateMoney2();
				flag++;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		System.out.println(start+"\n"+end+" "+ name);
		Specification<SysUser> sp = new Specification <SysUser>() {

			@Nullable
			@Override
			public Predicate toPredicate(
					Root root, CriteriaQuery criteriaQuery,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();

				if (null != start) {

					predicates.add(criteriaBuilder.greaterThanOrEqualTo(
							root.get("createTime"), start));
				}
				if (null != end) {
					predicates.add(criteriaBuilder.lessThanOrEqualTo(
							root.get("createTime"), end));
				}
				if (null != phone&&!phone.equals("")){
					predicates.add(criteriaBuilder.like(root.get("phone"),"%"+phone+"%"));
				}
				if (null != name&&!name.equals("")){
					predicates.add(criteriaBuilder.like(root.get("userName"),"%"+name+"%"));
				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};

		return (Page<SysUser>) userRepository.findAll(sp,pageable);
	}

	//    修改status
	public int updateStatusById(Byte status,Integer id){
		return userRepository.updateStatusById(status,id);
	}

	public SysUser findByNameAndPassword(String name,String password) {
		return userRepository.findByUserNameAndPassword(name, password);
	}


	public Page<SysUser> findByNameLike(Pageable pageable,String name) {
		return userRepository.findByUserNameLike(pageable,name);
	}

	public SysUser findByName(String name) {
		return userRepository.findByUserName(name);
	}

	public List<SysUser> findByIdIn(Collection<String> ids) {
		return findByIdIn(ids);
	}

//	//每天执行一次 增加工资
//	@Scheduled(cron = "0 0 0 * * ?")
//	@Transactional
//	public void updateMoney(){
//		List<SysRoleUser> list = sysRoleUserRepository.findAll();
//
//		for (SysRoleUser roleUser : list){
//			SysRole role = roleUser.getSysRoleByRoleId();
//			Double salary = role.getSalary();
//			userRepository.updateMoneyById(salary/30,roleUser.getUserId());
//		}
//
//	}
//

	@Scheduled(cron = "0 0 0 * * ?")
	public void changeFlag(){
		flag = 1;
	}
	//查询前调用
	@Transactional
	public void updateMoney2() throws ParseException {
		List<SysRoleUser> list = sysRoleUserRepository.findAll();
		for (SysRoleUser roleUser : list){
			SysRole role = roleUser.getSysRoleByRoleId();
			Double salary = role.getSalary();
			Double daySalary = salary/30;
			Integer userId = roleUser.getUserId();
			Optional<SysUser> user = userRepository.findById(userId);
			Date payDate = user.get().getPayTime();
			Date today = new Date();

			Integer days = MyDateFormat.daysBetween(payDate,today);
			System.out.println(days);

			Double amount = daySalary*days;
			user.get().setSalary(amount);
			userRepository.save(user.get());
		}

	}

}
