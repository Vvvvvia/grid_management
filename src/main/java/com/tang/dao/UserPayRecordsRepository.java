package com.tang.dao;



import com.tang.entity.UserPayRecords;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserPayRecordsRepository extends JpaRepository<UserPayRecords,Integer>,JpaSpecificationExecutor{
}
