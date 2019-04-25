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
import javax.persistence.Table;

@Entity
@Table(name = "sell_records", schema = "gzp", catalog = "")
public class SellRecords {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Timestamp date;
    private Integer goodsId;
    private Integer goodsNum;
    private Double beforeAmount;
    private Double amount;
    private Integer memberId;
    private Integer gridId;
    private Integer tenantId;
    private Goods goodsByGoodsId;
    private Member memberByMemberId;
    private Integer userId;
    private SysUser userByUserId;
    private Tenant tenantByTenantId;
    private Boolean status;

    public SellRecords() {
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
    @Column(name = "goods_id")
    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    @Basic
    @Column(name = "goods_num")
    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    @Basic
    @Column(name = "amount")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
    @Basic
    @Column(name = "before_amount")
    public Double getBeforeAmount() {
        return beforeAmount;
    }

    public void setBeforeAmount(Double beforeAmount) {
        this.beforeAmount = beforeAmount;
    }

    @Basic
    @Column(name = "member_id")
    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    @Basic
    @Column(name = "grid_id")
    public Integer getGridId() {
        return gridId;
    }

    public void setGridId(Integer gridId) {
        this.gridId = gridId;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SellRecords sellRecords = (SellRecords) o;

        if (id != null ? !id.equals(sellRecords.id) : sellRecords.id != null) return false;
        if (date != null ? !date.equals(sellRecords.date) : sellRecords.date != null) return false;
        if (goodsId != null ? !goodsId.equals(sellRecords.goodsId) : sellRecords.goodsId != null)
            return false;
        if (goodsNum != null ? !goodsNum.equals(sellRecords.goodsNum) : sellRecords.goodsNum != null)
            return false;
        if (amount != null ? !amount.equals(sellRecords.amount) : sellRecords.amount != null)
            return false;
        if (memberId != null ? !memberId.equals(sellRecords.memberId) : sellRecords.memberId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (goodsId != null ? goodsId.hashCode() : 0);
        result = 31 * result + (goodsNum != null ? goodsNum.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (memberId != null ? memberId.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "goods_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public Goods getGoodsByGoodsId() {
        return goodsByGoodsId;
    }

    public void setGoodsByGoodsId(Goods goodsByGoodsId) {
        this.goodsByGoodsId = goodsByGoodsId;
    }

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id",insertable = false,updatable = false)
    public Member getMemberByMemberId() {
        return memberByMemberId;
    }

    public void setMemberByMemberId(Member memberByMemberId) {
        this.memberByMemberId = memberByMemberId;
    }

    public SellRecords(Integer id, Timestamp date, Integer goodsId, Integer goodsNum, Double amount, Integer memberId, Integer gridId, Integer tenantId, Integer userId) {
        this.id = id;
        this.date = date;
        this.goodsId = goodsId;
        this.goodsNum = goodsNum;
        this.amount = amount;
        this.memberId = memberId;
        this.gridId = gridId;
        this.tenantId = tenantId;
        this.userId = userId;
    }


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public SysUser getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(SysUser userByUserId) {
        this.userByUserId = userByUserId;
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

    @Override
    public String toString() {
        return "SellRecords{" +
                "id=" + id +
                ", date=" + date +
                ", goodsId=" + goodsId +
                ", goodsNum=" + goodsNum +
                ", beforeAmount=" + beforeAmount +
                ", amount=" + amount +
                ", memberId=" + memberId +
                ", gridId=" + gridId +
                ", tenantId=" + tenantId +
                ", goodsByGoodsId=" + goodsByGoodsId +
                ", memberByMemberId=" + memberByMemberId +
                ", userId=" + userId +
                ", userByUserId=" + userByUserId +
                ", tenantByTenantId=" + tenantByTenantId +
                ", status=" + status +
                '}';
    }
}
