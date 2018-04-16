package com.yulintu.thematic.data.hibernate.test.sentities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.querydsl.core.types.Path;
import com.vividsolutions.jts.geom.Geometry;
import com.yulintu.thematic.data.hibernate.test.entities.MZDW;
import com.yulintu.thematic.data.hibernate.test.entities.Mzdw;
import lombok.Getter;
import lombok.Setter;


/**
 * QMZDW is a Querydsl query type for MZDW
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMZDW extends EntityPathBase<MZDW> {

    private static final long serialVersionUID = -1400499475L;

    public static final QMZDW mZDW = new QMZDW("mZDW");

    public final NumberPath<Integer> bsm = createNumber("bsm", Integer.class);

    public final StringPath mc = createString("mc");

    public final NumberPath<Double> mj = createNumber("mj", Double.class);

    public final ComparablePath<com.vividsolutions.jts.geom.Geometry> shape = createComparable("shape", com.vividsolutions.jts.geom.Geometry.class);

    public QMZDW(String variable) {
        super(MZDW.class, forVariable(variable));
    }

    public QMZDW(Path<? extends MZDW> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMZDW(PathMetadata metadata) {
        super(MZDW.class, metadata);
    }


}

