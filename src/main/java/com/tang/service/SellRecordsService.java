package com.tang.service;

import com.tang.dao.GoodsRepository;
import com.tang.dao.GridRepository;
import com.tang.dao.GridSellRecordsRepository;
import com.tang.dao.MemberRepository;
import com.tang.dao.SellRecordsRepository;
import com.tang.dao.SysUserRepository;
import com.tang.dao.TenantRepository;
import com.tang.dao.TenantSellRecordsRepository;
import com.tang.entity.Goods;
import com.tang.entity.Grid;
import com.tang.entity.GridSellRecords;
import com.tang.entity.SellRecords;
import com.tang.entity.SysUser;
import com.tang.entity.Tenant;
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
public class SellRecordsService {

    @Resource
    private SellRecordsRepository sellRecordsRepository;

    @Resource
    private GridRepository gridRepository;
    @Resource
    private SysUserRepository userRepository;
    @Resource
    private TenantRepository tenantRepository;
    @Resource
    private GoodsRepository goodsRepository;
    @Resource
    private MemberRepository memberRepository;
    @Resource
    private GridSellRecordsRepository gridSellRecordsRepository;
    @Resource
    private TenantSellRecordsRepository tenantSellRecordsRepository;

    public Page<GridSellRecords> findGridSellRecords(Date start ,Date end,Integer id,Pageable pageable){


        Specification<GridSellRecords> sp = new Specification <GridSellRecords>() {

            @Nullable
            @Override
            public Predicate toPredicate(
                    Root root, CriteriaQuery criteriaQuery,
                    CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                if(null != start){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                            root.get("date"), start));
                }
                if(null != end){
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(
                            root.get("date"), end));
                }
                if(null != id){
                    predicates.add(criteriaBuilder.equal(
                            root.get("gridId"), id));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return gridSellRecordsRepository.findAll(sp,pageable);
    }
    public Page<GridSellRecords> findTenantSellRecords(Date start ,Date end,Integer id,Pageable pageable){


        Specification<GridSellRecords> sp = new Specification <GridSellRecords>() {

            @Nullable
            @Override
            public Predicate toPredicate(
                    Root root, CriteriaQuery criteriaQuery,
                    CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                if(null != start){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                            root.get("date"), start));
                }
                if(null != end){
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(
                            root.get("date"), end));
                }
                if(null != id){
                    predicates.add(criteriaBuilder.equal(
                            root.get("tenantId"), id));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return tenantSellRecordsRepository.findAll(sp,pageable);
    }
    public Optional<SellRecords> findById(Integer id) {
        return sellRecordsRepository.findById(id);
    }

    public SellRecords save(SellRecords SellRecords) {
        return sellRecordsRepository.save(SellRecords);
    }

    public void delete(Integer id) {
        sellRecordsRepository.deleteById(id);
    }

    public Page<SellRecords> findAll(Pageable pageable) {
        return  sellRecordsRepository.findAll(pageable);
    }

    public void deleteByIdIn(List list){
        sellRecordsRepository.deleteByIdIn(list);
    }

//    通过格子查询*
    public Page<SellRecords> findByGridId(Pageable pageable,Integer gridId){
        return sellRecordsRepository.findByGridId(pageable,gridId);
    }
//    通过格主查询*
    public Page<SellRecords> findByTenantId(Pageable pageable,Integer tenantId){
        return sellRecordsRepository.findByTenantId(pageable,tenantId);
    }
    //    通过会员查询*
    public Page<SellRecords> findByMemberId(Pageable pageable,Integer memberId){
        return sellRecordsRepository.findByMemberId(pageable,memberId);
    }

    //    通过商品查询*
    public Page<SellRecords> findByGoodsId(Pageable pageable,Integer goodsId){
        return sellRecordsRepository.findByGoodsId(pageable,goodsId);
    }

    //    通过员工查询*
    public Page<SellRecords> findByUserId(Pageable pageable,Integer userId){
        return sellRecordsRepository.findByUserId(pageable,userId);
    }

//    通过时间查询*
    public Page<SellRecords> findByDate(Pageable pageable,Date date){
        Date date2 = MyDateFormat.dateAdd(date,1);
        System.out.println(date+"  "+date2);
        return sellRecordsRepository.findByDate(pageable,date,date2);
    }

    @Transactional
    public Page<SellRecords> findAll(Integer pageNum, Date startDate,Date endDate,
                                      String name,String namePrefix,Boolean status,
                                     Integer type,Integer gridId,Integer tenantId,
                                     String barcode){

        Specification<SellRecords> sp = new Specification <SellRecords>() {

            @Nullable
            @Override
            public Predicate toPredicate(
                    Root root, CriteriaQuery criteriaQuery,
                    CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicates = new ArrayList<>();
                if(null != startDate){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                            root.get("date"), startDate));
                }
                if(null != endDate){
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(
                            root.get("date"), endDate));
                }
                if (null!=status){
                    predicates.add(criteriaBuilder.equal(
                            root.get("status"), status));
                }
                if (null!=barcode&&!barcode.equals("")){
                    Goods goods = goodsRepository.findByCreateTime(barcode);
                    Integer goodsId = 0;
                    if (goods!=null){
                        goodsId = goods.getId();
                    }
                    predicates.add(criteriaBuilder.equal(
                            root.get("goodsId"), goodsId));
                }
                if (null!=gridId){
                    predicates.add(criteriaBuilder.equal(
                            root.get("gridId"), gridId));
                }
                if (null!=tenantId){
                    predicates.add(criteriaBuilder.equal(
                            root.get("tenantId"), tenantId));
                }
                if (null!=type&&!type.equals("")){
                    if (type==0){
                        predicates.add(criteriaBuilder.isNull(
                                root.get("memberId")));
                    }else {
                        predicates.add(criteriaBuilder.isNotNull(
                                root.get("memberId")));
                    }
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
                        gl = gridRepository.findIdByName("%"+name+"%");
                        namePath = "gridId";
                    }
                    if (namePrefix.equals("user")){
                        gl = userRepository.findIdByNameLike("%"+name+"%");
                        namePath = "userId";
                    }
                    if (namePrefix.equals("member")){
                        gl = memberRepository.findIdByNameLike("%"+name+"%");
                        namePath = "memberId";
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
        return sellRecordsRepository.findAll(sp,new PageRequest(pageNum,10,new Sort(Sort.Order.desc("date"))));
    }
    public void deleteById(Integer id){
        sellRecordsRepository.deleteById(id);
    }

    
}
