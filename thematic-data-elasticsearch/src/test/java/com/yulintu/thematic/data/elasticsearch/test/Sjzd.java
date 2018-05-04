package com.yulintu.thematic.data.elasticsearch.test;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.annotation.Generated;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Sjzd is a Querydsl bean type
 */
@Generated("com.querydsl.codegen.BeanSerializer")
@Entity
@Indexed(index = "com.yulintu.tworegion.entities.sjzd")
public class Sjzd implements Serializable {

    @NotBlank
    @Field
    private String bm;

    @Field
    private String bz;

    private Short bzlb;

    @NotBlank
    @Field
    private String fzm;

    @NotBlank
    @Field
    private String fzmc;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, insertable = false)
    @Min(0)
    private Integer id;

    @NotBlank
    @Field
    private String mc;

    private Short sfjy;

    @Min(0)
    @Max(1)
    private Short sfzdy;

    private String zdymc;

    public String getBm() {
        return bm;
    }

    public void setBm(String bm) {
        this.bm = bm;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Short getBzlb() {
        return bzlb;
    }

    public void setBzlb(Short bzlb) {
        this.bzlb = bzlb;
    }

    public String getFzm() {
        return fzm;
    }

    public void setFzm(String fzm) {
        this.fzm = fzm;
    }

    public String getFzmc() {
        return fzmc;
    }

    public void setFzmc(String fzmc) {
        this.fzmc = fzmc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public Short getSfjy() {
        return sfjy;
    }

    public void setSfjy(Short sfjy) {
        this.sfjy = sfjy;
    }

    public Short getSfzdy() {
        return sfzdy;
    }

    public void setSfzdy(Short sfzdy) {
        this.sfzdy = sfzdy;
    }

    public String getZdymc() {
        return zdymc;
    }

    public void setZdymc(String zdymc) {
        this.zdymc = zdymc;
    }

    @Override
    public String toString() {
        return "bm = " + bm + ", bz = " + bz + ", bzlb = " + bzlb + ", fzm = " + fzm + ", fzmc = " + fzmc + ", id = " + id + ", mc = " + mc + ", sfjy = " + sfjy + ", sfzdy = " + sfzdy + ", zdymc = " + zdymc;
    }

}

