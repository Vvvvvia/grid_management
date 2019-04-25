package com.tang.service;

import com.tang.dao.RentRecordsRepository;
import com.tang.dao.TenantRepository;
import com.tang.entity.Tenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;


import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class TenantService {
    
    @Resource
    private TenantRepository tenantRepository;

    @Autowired
    private RentRecordsRepository rentRecordsRepository;
   

    public Tenant findById(Integer id) {
        return tenantRepository.getOne(id);
    }

    public Tenant save(Tenant Tenant) {
        return tenantRepository.save(Tenant);
    }

    public Boolean delete(Integer id) {
        List rl = rentRecordsRepository.findByTenantIdAndStatus(id,true);
        if (rl.size()==0){
            tenantRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Page<Tenant> findAll(Pageable pageable,String name,String phone,String sex) {

        Specification<Tenant> sp = new Specification <Tenant>() {

            @Nullable
            @Override
            public Predicate toPredicate(
                    Root root, CriteriaQuery criteriaQuery,
                    CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                if (null != name&&!name.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("name"),"%"+name+"%"));
                }
                if (null != phone&&!phone.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("phone"),"%"+phone+"%"));
                }
                if (null != sex&&!sex.equals("")){
                    predicates.add(criteriaBuilder.equal(root.get("sex"),sex));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        return tenantRepository.findAll(sp,pageable);
    }


    public Boolean deleteByIdIn(ArrayList<Integer> list){
        List l = rentRecordsRepository.findByTenantIdInAndStatus(list,true);
        if (l.size()==0){
            tenantRepository.deleteByIdIn(list);
            return true;
        }
        return false;
    }
//    通过手机号码查找信息
    public Tenant findByPhone(String phone){
        return tenantRepository.findByPhone(phone);
    }
    //修改账户余额
    public Integer updateMoney(Double money,Integer id){
        return tenantRepository.updateMoneyById(money,id);
    }
}
