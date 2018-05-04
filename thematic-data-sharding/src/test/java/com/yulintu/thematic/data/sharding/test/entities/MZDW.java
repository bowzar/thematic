package com.yulintu.thematic.data.sharding.test.entities;

import com.vividsolutions.jts.geom.Geometry;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class MZDW {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private int bsm;

    @Column(name = "DWMC")
    private String mc;

    private double mj;

    private Geometry shape;
}