package com.tang.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "rent_records", schema = "gzp", catalog = "")
public class RentRecords {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Timestamp date;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date endDate;
    private Integer gridId;
    private Integer tenantId;
    private Double fee;
    private Boolean status;
    private Grid gridByGridId;
    private Tenant tenantByTenantId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date")
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "grid_id")
    public Integer getGridId() {
        return gridId;
    }

    public void setGridId(Integer gridId) {
        this.gridId = gridId;
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
    @Column(name = "fee")
    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }


    @Basic
    @Column(name = "end_date")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date end) {
        this.endDate = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RentRecords rentRecords = (RentRecords) o;

        if (id != null ? !id.equals(rentRecords.id) : rentRecords.id != null) return false;
        if (date != null ? !date.equals(rentRecords.date) : rentRecords.date != null) return false;
        if (gridId != null ? !gridId.equals(rentRecords.gridId) : rentRecords.gridId != null)
            return false;
        if (tenantId != null ? !tenantId.equals(rentRecords.tenantId) : rentRecords.tenantId != null)
            return false;
        if (fee != null ? !fee.equals(rentRecords.fee) : rentRecords.fee != null) return false;
        if (endDate != null ? !endDate.equals(rentRecords.endDate) : rentRecords.endDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (gridId != null ? gridId.hashCode() : 0);
        result = 31 * result + (tenantId != null ? tenantId.hashCode() : 0);
        result = 31 * result + (fee != null ? fee.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "grid_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public Grid getGridByGridId() {
        return gridByGridId;
    }

    public void setGridByGridId(Grid gridByGridId) {
        this.gridByGridId = gridByGridId;
    }

    @ManyToOne
    @JoinColumn(name = "tenant_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public Tenant getTenantByTenantId() {
        return tenantByTenantId;
    }

    public void setTenantByTenantId(Tenant tenantByTenantId) {
        this.tenantByTenantId = tenantByTenantId;
    }

    @Basic
    @Column(name = "status")
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
