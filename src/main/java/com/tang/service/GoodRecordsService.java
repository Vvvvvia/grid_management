package com.tang.service;

import com.tang.dao.GoodsRecordsRepository;
import com.tang.dao.GoodsRepository;
import com.tang.dao.GridRepository;
import com.tang.dao.TenantRepository;
import com.tang.entity.Goods;
import com.tang.entity.GoodsRecords;
import com.tang.entity.Grid;
import com.tang.entity.SysLogs;
import com.tang.entity.Tenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
@Service
public class GoodRecordsService {

    @Autowired
    private GoodsRecordsRepository goodsRecordsRepository;
    @Autowired
    private GridRepository gridRepository;
    @Autowired
    private TenantRepository tenantRepository;
    @Autowired
    private GoodsRepository goodsRepository;

    public Page<GoodsRecords> findAll(Integer pageNum, Date startDate,Date endDate,
                                      String name,String namePrefix,Byte type){
        
        Specification<GoodsRecords> sp = new Specification <GoodsRecords>() {

            @Nullable
            @Override
            public Predicate toPredicate(
                    Root root, CriteriaQuery criteriaQuery,
                    CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                if(null != startDate){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                            root.get("createTime"), startDate));
                }
                if(null != endDate){
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(
                            root.get("createTime"), endDate));
                }
                if (null!=type){
                    predicates.add(criteriaBuilder.equal(
                            root.get("type"), type));
                }
                if (null!=name&&!name.equals("")){
                    List<Integer> gl = null;
                    String namePath = "";
                    if (namePrefix.equals("goods")){
                        gl = goodsRepository.findIdByNameLike("%"+name+"%");
                        namePath = "goodsId";
                    }
                    if (namePrefix.equals("tenant")){
                        gl = tenantRepository.findIdByNameLike("%"+name+"%");
                        namePath = "tenantId";
                    }
                    if (namePrefix.equals("grid")){
                        List<Integer> girdIds = gridRepository.findIdByName("%"+name+"%");
                        if (girdIds!=null&&girdIds.size()!=0){
                            gl = goodsRepository.IdListByGridIdIn(girdIds);
                        }else gl = null;
                        namePath = "goodsId";
                    }
                    System.out.println("namePath:"+namePath);
                    if (gl!=null&&gl.size()!=0){
                        CriteriaBuilder.In<Integer> in = criteriaBuilder.in(root.get(namePath));
                        for (int i=0;i<gl.size();i++){
                            in.value(gl.get(i));
                        }
                        predicates.add(in);
                    }else predicates.add(criteriaBuilder.equal(root.get("id"),0));


                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return goodsRecordsRepository.findAll(sp,new PageRequest(pageNum,10,new Sort(Sort.Order.desc("createTime"))));
    }
    public void deleteById(Integer id){
        goodsRecordsRepository.deleteById(id);
    }

    public void deleteByIdIn(List<Integer> list){
        goodsRecordsRepository.deleteByIdIn(list);
    }
}
