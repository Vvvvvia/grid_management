package com.tang.service;

import com.tang.dao.GridRepository;
import com.tang.dao.RentRecordsRepository;
import com.tang.dao.TenantRepository;
import com.tang.entity.Goods;
import com.tang.entity.Grid;
import com.tang.entity.RentRecords;
import com.tang.entity.SysUser;
import com.tang.entity.Tenant;
import com.tang.util.MyDateFormat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class RentRecordsService {
    
    @Resource
    private RentRecordsRepository rentRecordsRepository;
    @Resource
    private TenantRepository tenantRepository;
    @Resource
    private GridRepository gridRepository;

    public Optional<RentRecords> findById(Integer id) {
        return rentRecordsRepository.findById(id);
    }

    public RentRecords save(RentRecords RentRecords) {
        return rentRecordsRepository.save(RentRecords);
    }

    @Transactional
    public Boolean delete(Integer id) {
        if (!rentRecordsRepository.getOne(id).getStatus()){
            rentRecordsRepository.deleteById(id);
            return true;
        }
      return false;
    }

    public Page<RentRecords> findAll(Pageable pageable, Boolean status,String type,
                               String tenantName, String gridName,Date start,Date end){
        String datePattern = "date";
        if (type.equals("end")){
            datePattern="endDate";
        }
        String finalDatePattern = datePattern;
        Specification<RentRecords> sp = new Specification <RentRecords>() {

            @Nullable
            @Override
            public Predicate toPredicate(
                    Root root, CriteriaQuery criteriaQuery,
                    CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                if(null != start){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                            root.get(finalDatePattern), start));
                }
                if(null != end){
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(
                            root.get(finalDatePattern), end));
                }
                if (null!=status){
                    predicates.add(criteriaBuilder.equal(
                            root.get("status"), status));
                }
                if (null!=tenantName&&!tenantName.equals("")){
                    List<Integer> gl = tenantRepository.findIdByNameLike("%"+tenantName+"%");
                    if (gl!=null&&gl.size()!=0){
                        CriteriaBuilder.In<Integer> in = criteriaBuilder.in(root.get("tenantId"));
                        for (int i=0;i<gl.size();i++){
                            in.value(gl.get(i));
                        }
                        predicates.add(in);
                    }else predicates.add(criteriaBuilder.equal(root.get("id"),0));
                }
                if (null!=gridName&&!gridName.equals("")){
                    List<Integer> gl = tenantRepository.findIdByNameLike("%"+gridName+"%");
                    if (gl!=null&&gl.size()!=0){
                        CriteriaBuilder.In<Integer> in = criteriaBuilder.in(root.get("gridId"));
                        for (int i=0;i<gl.size();i++){
                            in.value(gl.get(i));
                        }
                        predicates.add(in);
                    }else predicates.add(criteriaBuilder.equal(root.get("id"),0));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
       
        return rentRecordsRepository.findAll(sp,pageable);
    }

    @Transactional
    public boolean deleteByIdIn(List<Integer> list){
        for (Integer id:list){
            if (rentRecordsRepository.getOne(id).getStatus()){
                return false;
            }
        }
        rentRecordsRepository.deleteByIdIn(list);
        return true;
    }

//    通过格子查询*
    public Page<RentRecords> findByGridId(Pageable pageable,Integer gridId){
        return rentRecordsRepository.findByGridId(pageable,gridId);
    }
//    通过格主查询*
    public Page<RentRecords> findByTenantId(Pageable pageable,Integer tenantId){
        return rentRecordsRepository.findByTenantIdOrderByIdDesc(pageable,tenantId);
    }
//    通过时间查询*
    public Page<RentRecords> findByDate(Pageable pageable,Date date){
        Date date2 = MyDateFormat.dateAdd(date,1);
        System.out.println(date+"  "+date2);
        return rentRecordsRepository.findByDate(pageable,date,date2);
    }
}
