package com.yulintu.thematic.data.sharding.test.entities;

import com.vividsolutions.jts.geom.Geometry;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class XZQH_XZDY {

    @Id
    private String id;

    private String mc;

    private String bm;

    private Geometry shape;
}