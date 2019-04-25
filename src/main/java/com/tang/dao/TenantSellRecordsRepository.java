package com.tang.dao;


        import com.tang.entity.TenantSellRecords;
        import org.springframework.data.domain.Page;
        import org.springframework.data.domain.Pageable;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
        import org.springframework.stereotype.Repository;

        import java.sql.Date;

        @Repository
public interface TenantSellRecordsRepository extends JpaRepository <TenantSellRecords,Integer> ,JpaSpecificationExecutor{

    TenantSellRecords findByTenantIdAndDate(Integer tenantId,Date date);
    Page<TenantSellRecords> findByDate(Pageable pageable, Date date);

    Page<TenantSellRecords> findByTenantId(Pageable pageable, Integer gridId);

}
