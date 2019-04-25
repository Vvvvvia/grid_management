package com.tang.dao;

import com.tang.entity.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public interface AccountRepository extends JpaRepository<Account,Integer> ,JpaSpecificationExecutor<Account>{
   Account findByDate(Date date);


   @Transactional
   @Modifying(clearAutomatically = true)
   @Query(value = "update account set account.profit = account.profit+?2 AND" +
           " account.income = account.income+?1 where account.date = ?3",nativeQuery = true)
   int updateIncomeAndProfitByDate( Double income, Double profit,  Date id);

   @Transactional
   @Modifying(clearAutomatically = true)
   @Query(value = "update account set account.expense = account.expense+?1 " +
           "where account.date = ?2",nativeQuery = true)
   int updateExpenseByDate( Double expense,  Date id);


}
