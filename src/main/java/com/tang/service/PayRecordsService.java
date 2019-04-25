package com.tang.service;

import com.tang.dao.GoodsRepository;
import com.tang.dao.GridRepository;
import com.tang.dao.GridSellRecordsRepository;
import com.tang.dao.MemberRepository;
import com.tang.dao.SellRecordsRepository;
import com.tang.dao.SysUserRepository;
import com.tang.dao.TenantPayRecordsRepository;
import com.tang.dao.TenantRepository;
import com.tang.dao.TenantSellRecordsRepository;
import com.tang.dao.UserPayRecordsRepository;
import com.tang.entity.GridSellRecords;
import com.tang.entity.SellRecords;
import com.tang.util.MyDateFormat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class PayRecordsService {

    @Resource
    private SellRecordsRepository sellRecordsRepository;

    @Resource
    private SysUserRepository userRepository;
    @Resource
    private TenantRepository tenantRepository;
    @Resource
    private TenantPayRecordsRepository tenantPayRecordsRepository;
    @Resource
    private UserPayRecordsRepository userPayRecordsRepository;

    public Page<GridSellRecords> findUserPayRecords(Date start ,Date end,Integer id,Pageable pageable){


        Specification<GridSellRecords> sp = new Specification <GridSellRecords>() {

            @Nullable
            @Override
            public Predicate toPredicate(
                    Root root, CriteriaQuery criteriaQuery,
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
                if(null != id){
                    predicates.add(criteriaBuilder.equal(
                            root.get("userId"), id));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return userPayRecordsRepository.findAll(sp,pageable);
    }
    public Page<GridSellRecords> findTenantPayRecords(Date start ,Date end,Integer id,Pageable pageable){


        Specification<GridSellRecords> sp = new Specification <GridSellRecords>() {

            @Nullable
            @Override
            public Predicate toPredicate(
                    Root root, CriteriaQuery criteriaQuery,
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
                if(null != id){
                    predicates.add(criteriaBuilder.equal(
                            root.get("tenantId"), id));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return tenantPayRecordsRepository.findAll(sp,pageable);
    }
    public Optional<SellRecords> findById(Integer id) {
        return sellRecordsRepository.findById(id);
    }

    public SellRecords save(SellRecords SellRecords) {
        return sellRecordsRepository.save(SellRecords);
    }

}
