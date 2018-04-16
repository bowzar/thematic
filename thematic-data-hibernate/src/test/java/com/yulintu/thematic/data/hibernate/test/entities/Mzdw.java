package com.yulintu.thematic.data.hibernate.test.entities;

import lombok.Getter;
import lombok.Setter;
import org.geolatte.geom.Geometry;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Mzdw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer bsm;

    private String id;

    @Column(name = "DWMC")
    private String dwmc;

    private double mj;

    private Geometry shape;
}