package com.yulintu.thematic.data.hibernate.test.entities;

import com.vividsolutions.jts.geom.Geometry;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class MZDW {

    @Id
    private int bsm;

    @Column(name = "DWMC")
    private String mc;

    private double mj;

    private Geometry shape;
}