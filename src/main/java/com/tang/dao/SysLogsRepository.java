package com.tang.dao;



import com.tang.entity.SysLogs;
import com.tang.entity.SysUser;


import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Repository
public interface SysLogsRepository extends JpaRepository<SysLogs,Integer> ,JpaSpecificationExecutor{
    int deleteByCreateTimeBefore(Date date);


}
