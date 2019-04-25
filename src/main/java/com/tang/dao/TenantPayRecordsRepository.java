package com.tang.dao;



import com.tang.entity.TenantPayRecords;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TenantPayRecordsRepository extends JpaRepository<TenantPayRecords,Integer>,JpaSpecificationExecutor{
}
