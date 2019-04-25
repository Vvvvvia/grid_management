package com.tang.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "tenant_sell_records", schema = "gzp", catalog = "")
public class TenantSellRecords {
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date date;
    private Integer id;
    private Integer tenantId;
    private Double totalIncome;

    public TenantSellRecords(Integer id,Date date, Integer tenantId, Double totalIncome) {
        this.date = date;
        this.id=id;
        this.tenantId = tenantId;
        this.totalIncome = totalIncome;
    }


    public TenantSellRecords() {
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "tenant_id")
    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    @Basic
    @Column(name = "total_income")
    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TenantSellRecords that = (TenantSellRecords) o;

        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (tenantId != null ? !tenantId.equals(that.tenantId) : that.tenantId != null)
            return false;
        if (totalIncome != null ? !totalIncome.equals(that.totalIncome) : that.totalIncome != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (tenantId != null ? tenantId.hashCode() : 0);
        result = 31 * result + (totalIncome != null ? totalIncome.hashCode() : 0);
        return result;
    }
}
