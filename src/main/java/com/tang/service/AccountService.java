package com.tang.service;

import com.tang.dao.AccountRepository;
import com.tang.entity.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class AccountService {
    @Resource
    private AccountRepository accountRepository;


    public Account findByDate(Date date) {
        return accountRepository.findByDate(date);
    }



    public Account save(Account account) {
        return  accountRepository.save(account);
    }


    public void delete(Account account) {
        accountRepository.delete(account);
    }


    public Page<Account> findAll(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }
    public Page<Account> findAllMulti(Integer pageNum,Date startDate,Date endDate) {
        System.out.println(startDate+"\n"+endDate);
        Specification<Account> sp = new Specification <Account>() {

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
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return accountRepository.findAll(sp,new PageRequest(pageNum,10,new Sort(Sort.Order.desc("date"))));
    }

}
