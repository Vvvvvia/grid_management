package com.tang.entity;



import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Goods {
    private Integer id;
    private String name;
    private Integer num;
    private Double price;
    private String picture;
    private Integer gridId;
    private Boolean status;
    private String createTime;
    private String barcode;
    private Grid gridByGridId;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    @Column(name = "num")
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Basic
    @Column(name = "price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "picture")
    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
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
    @Column(name = "status")
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean strtus) {
        this.status = strtus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Goods goods = (Goods) o;

        if (id != null ? !id.equals(goods.id) : goods.id != null) return false;
        if (name != null ? !name.equals(goods.name) : goods.name != null) return false;
        if (num != null ? !num.equals(goods.num) : goods.num != null) return false;
        if (price != null ? !price.equals(goods.price) : goods.price != null) return false;
        if (picture != null ? !picture.equals(goods.picture) : goods.picture != null) return false;
        if (gridId != null ? !gridId.equals(goods.gridId) : goods.gridId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (num != null ? num.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (picture != null ? picture.hashCode() : 0);
        result = 31 * result + (gridId != null ? gridId.hashCode() : 0);
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
    @Basic
    @Column(name = "create_time")
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    @Basic
    @Column(name = "barcode")
    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
