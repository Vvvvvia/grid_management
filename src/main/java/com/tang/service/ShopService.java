package com.tang.service;

import com.tang.dao.ShopRepository;
import com.tang.entity.Shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class ShopService {
    
    @Resource
    private ShopRepository shopRepository;

    public Optional<Shop> findById(Integer id) {
        return shopRepository.findById(id);
    }

    public Shop save(Shop Shop) {
        return shopRepository.save(Shop);
    }
    @Transactional
    public Boolean delete(Integer id) {
        Shop s = shopRepository.getOne(id);
        System.out.println(s);
        if (s.getStatus()){
            shopRepository.deleteById(id);
            return true;
        }
        return false;
    }
    @Transactional
    public Boolean deleteByIdIn(List list){
        List<Shop> list1 = shopRepository.findByIdInAndStatus(list,false);
        if (list1.size()==0){
            shopRepository.deleteByIdIn(list);
            return true;
        }
        return false;
    }

    public Page<Shop> findAll(Pageable pageable,String name,String phone,String address) {

        Specification<Shop> sp = new Specification <Shop>() {

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
                if (null != address&&!address.equals("")){
                    predicates.add(criteriaBuilder.like(root.get("address"),"%"+address+"%"));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        return shopRepository.findAll(sp,pageable);
    }

    
}
