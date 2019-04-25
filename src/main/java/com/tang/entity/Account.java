package com.tang.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account {
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date date;
    private Double income;
    private Double expense;
    private Double profit;

    private Integer id;
    public Account() {
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
    @Column(name = "profit")
    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }
    @Basic
    @Column(name = "income")
    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    @Basic
    @Column(name = "expense")
    public Double getExpense() {
        return expense;
    }

    public void setExpense(Double expense) {
        this.expense = expense;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (date != null ? !date.equals(account.date) : account.date != null) return false;
        if (income != null ? !income.equals(account.income) : account.income != null) return false;
        if (expense != null ? !expense.equals(account.expense) : account.expense != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (income != null ? income.hashCode() : 0);
        result = 31 * result + (expense != null ? expense.hashCode() : 0);
        return result;
    }

    public Account(Integer id,Date date, Double income, Double expense, Double profit) {
        this.id  = id;
        this.date = date;
        this.income = income;
        this.expense = expense;
        this.profit = profit;
    }

}
