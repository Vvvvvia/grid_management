package com.tang.entity;

import com.fasterxml.jackson.annotation.JsonFormat;


import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "goods_records", schema = "gzp", catalog = "")
public class GoodsRecords {
    private Integer id;
    private Integer goodsId;
    private Byte type;
    private Integer num;
    private Integer tenantId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Timestamp createTime;
    private Tenant tenantByTenantId;
    private Goods goodsByGoodsId;

    public GoodsRecords() {
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
    @Column(name = "goods_id")
    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    @Basic
    @Column(name = "type")
    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    @Basic
    @Column(name = "num")
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
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
    @Column(name = "Create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoodsRecords that = (GoodsRecords) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (goodsId != null ? !goodsId.equals(that.goodsId) : that.goodsId != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (num != null ? !num.equals(that.num) : that.num != null) return false;
        if (tenantId != null ? !tenantId.equals(that.tenantId) : that.tenantId != null)
            return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (goodsId != null ? goodsId.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (num != null ? num.hashCode() : 0);
        result = 31 * result + (tenantId != null ? tenantId.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "tenant_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public Tenant getTenantByTenantId() {
        return tenantByTenantId;
    }

    public void setTenantByTenantId(Tenant tenantByTenantId) {
        this.tenantByTenantId = tenantByTenantId;
    }

    @ManyToOne
    @JoinColumn(name = "goods_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public Goods getGoodsByGoodsId() {
        return goodsByGoodsId;
    }

    public void setGoodsByGoodsId(Goods goodsByGoodsId) {
        this.goodsByGoodsId = goodsByGoodsId;
    }

    public GoodsRecords(Integer id, Integer goodsId, Byte type, Integer num, Integer tenantId, Timestamp createTime) {
        this.id = id;
        this.goodsId = goodsId;
        this.type = type;
        this.num = num;
        this.tenantId = tenantId;
        this.createTime = createTime;
    }
}
