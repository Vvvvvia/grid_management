package com.tang.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Grid {
    private Integer id;
    private String name;
    private Integer size;
    private Integer shopId;
    private Integer rentId;
    private Shop shopByShopId;
    private Double totalIncome;
    private Boolean status;


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
    @Column(name = "total_income")
    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "size")
    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Basic
    @Column(name = "shop_id")
    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    @Basic
    @Column(name = "rent_id")
    public Integer getRentId() {
        return rentId;
    }

    public void setRentId(Integer rentId) {
        this.rentId = rentId;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Grid grid = (Grid) o;

        if (id != null ? !id.equals(grid.id) : grid.id != null) return false;
        if (name != null ? !name.equals(grid.name) : grid.name != null) return false;
        if (size != null ? !size.equals(grid.size) : grid.size != null) return false;
        if (shopId != null ? !shopId.equals(grid.shopId) : grid.shopId != null) return false;
        if (rentId != null ? !rentId.equals(grid.rentId) : grid.rentId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (shopId != null ? shopId.hashCode() : 0);
        result = 31 * result + (rentId != null ? rentId.hashCode() : 0);
        return result;
    }



    @ManyToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "id", nullable = false,insertable = false,updatable = false)
    public Shop getShopByShopId() {
        return shopByShopId;
    }
    public void setShopByShopId(Shop shopByShopId) {
        this.shopByShopId = shopByShopId;
    }

    @Override
    public String toString() {
        return "Grid{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", shopId=" + shopId +
                ", rentId=" + rentId +
                ", shopByShopId=" + shopByShopId +
                ", totalIncome=" + totalIncome +
                ", status=" + status +
                '}';
    }
}
