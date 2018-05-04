package com.yulintu.thematic.data.sharding.test.sentities;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.ComparablePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.yulintu.thematic.data.sharding.test.entities.MZDW;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


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

