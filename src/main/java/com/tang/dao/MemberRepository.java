package com.tang.dao;

import com.tang.entity.Member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member,Integer>,JpaSpecificationExecutor<Member> {
    Page<Member> findByNameContaining(Pageable pageable, String name);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "SELECT member.id FROM member where member.name LIKE ?1",nativeQuery = true)
    List<Integer>findIdByNameLike(String name);

    Member findByPhone(String phone);
    @Transactional
    void deleteByIdIn(ArrayList<Integer> list);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update member set member.score = member.score+?1 where member.id = ?2",nativeQuery = true)
    int updateScoreIdById(Double score,  Integer id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update member set member.status =?1 where member.id = ?2",nativeQuery = true)
    int updateStatusById(Boolean status,  Integer id);
}
