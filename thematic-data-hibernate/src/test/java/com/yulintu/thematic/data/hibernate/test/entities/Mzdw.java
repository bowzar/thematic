package com.yulintu.thematic.data.hibernate.test.entities;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Generated;
import javax.persistence.*;


/**
 * Mzdw is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
@Entity
public class Mzdw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bsm", unique = true, nullable = false)
    private Integer bsm;

    private String dwmc;

    private String id;

    private Double mj;

    private org.geolatte.geom.Geometry shape;

    public Integer getBsm() {
        return bsm;
    }

    public void setBsm(Integer bsm) {
        this.bsm = bsm;
    }

    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getMj() {
        return mj;
    }

    public void setMj(Double mj) {
        this.mj = mj;
    }

    public org.geolatte.geom.Geometry getShape() {
        return shape;
    }

    public void setShape(org.geolatte.geom.Geometry shape) {
        this.shape = shape;
    }

    @Override
    public String toString() {
        return "bsm = " + bsm + ", dwmc = " + dwmc + ", id = " + id + ", mj = " + mj + ", shape = " + shape;
    }

    @Entity
    @Getter
    @Setter
    public static class MZDW {

        @Id
        private int bsm;

        @Column(name = "DWMC")
        private String mc;

        private double mj;

        @Column(columnDefinition = "MDSYS.SDO_GEOMETRY")
        private org.geolatte.geom.Geometry shape;
    }
}

