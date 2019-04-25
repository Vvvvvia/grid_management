package com.tang.service;

import com.tang.dao.MemberRepository;
import com.tang.dao.SellRecordsRepository;
import com.tang.entity.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

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
public class MemberService {
    
    @Resource
    private MemberRepository memberRepository;
    @Autowired
    private SellRecordsRepository sellRecordsRepository;

    public Optional<Member> findById(Integer id) {
        return memberRepository.findById(id);
    }

    public Member save(Member Member) {
        return memberRepository.save(Member);
    }

    public void delete(Integer id) {
        memberRepository.deleteById(id);
    }

    public Page<Member> findAll(Pageable pageable, Date start,Date end,String name,String phone) {
        System.out.println(start+"\n"+end+" "+ name);
        Specification<Member> sp = new Specification <Member>() {

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
                if (null != name&&!name.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("name"),"%"+name+"%"));
                }
                if (null != phone&&!phone.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("phone"),"%"+phone+"%"));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        return (Page<Member>) memberRepository.findAll(sp,pageable);
    }

    public Page<Member> findByNameContaining(Pageable pageable,String name) {
        return memberRepository.findByNameContaining(pageable,name);
    }

    public void deleteByIdIn(ArrayList<Integer> list){
        memberRepository.deleteByIdIn(list);
    }
//    通过会员的手机号码查找会员信息
    public Member findByPhone(String phone){
        return memberRepository.findByPhone(phone);
    }
//    修改会员的积分
    public int updateScoreById(Double score,Integer id){
        return memberRepository.updateScoreIdById(score,id);
    }
    //    修改会员的status
    public int updateStatusById(Boolean status,Integer id){
        return memberRepository.updateStatusById(status,id);
    }
}
