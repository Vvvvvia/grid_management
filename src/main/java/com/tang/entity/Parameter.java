package com.tang.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Parameter {
    private Double inRate;
    private Double outRate;
    private Integer id;

    @Basic
    @Column(name = "in_rate")
    public Double getInRate() {
        return inRate;
    }

    public void setInRate(Double inRate) {
        this.inRate = inRate;
    }

    @Basic
    @Column(name = "out_rate")
    public Double getOutRate() {
        return outRate;
    }

    public void setOutRate(Double outRate) {
        this.outRate = outRate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parameter parameter = (Parameter) o;

        if (inRate != null ? !inRate.equals(parameter.inRate) : parameter.inRate != null)
            return false;
        if (outRate != null ? !outRate.equals(parameter.outRate) : parameter.outRate != null)
            return false;
        if (id != null ? !id.equals(parameter.id) : parameter.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = inRate != null ? inRate.hashCode() : 0;
        result = 31 * result + (outRate != null ? outRate.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
