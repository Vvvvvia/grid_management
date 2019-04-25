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
@Table(name = "grid_sell_records", schema = "gzp", catalog = "")
public class GridSellRecords {
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date date;
    private Integer id;
    private Double todayIncome;
    private Integer gridId;

    public GridSellRecords(Integer id,Date date, Double todayIncome, Integer gridId) {
        this.id = id;
        this.date = date;
        this.todayIncome = todayIncome;
        this.gridId = gridId;
    }

    public GridSellRecords() {
    }

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
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "today_income")
    public Double getTodayIncome() {
        return todayIncome;
    }

    public void setTodayIncome(Double todayIncome) {
        this.todayIncome = todayIncome;
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

        GridSellRecords that = (GridSellRecords) o;

        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (todayIncome != null ? !todayIncome.equals(that.todayIncome) : that.todayIncome != null)
            return false;
        if (gridId != null ? !gridId.equals(that.gridId) : that.gridId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (todayIncome != null ? todayIncome.hashCode() : 0);
        result = 31 * result + (gridId != null ? gridId.hashCode() : 0);
        return result;
    }
}
