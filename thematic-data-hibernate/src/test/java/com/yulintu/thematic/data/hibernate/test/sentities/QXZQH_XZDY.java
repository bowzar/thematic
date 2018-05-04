package com.yulintu.thematic.data.hibernate.test.sentities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.yulintu.thematic.data.hibernate.test.entities.XZQH_XZDY;


/**
 * QXZQH_XZDY is a Querydsl query type for XZQH_XZDY
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QXZQH_XZDY extends EntityPathBase<XZQH_XZDY> {

    private static final long serialVersionUID = 2102321968L;

    public static final QXZQH_XZDY xZQH_XZDY = new QXZQH_XZDY("xZQH_XZDY");

    public final StringPath bm = createString("bm");

    public final StringPath id = createString("id");

    public final StringPath mc = createString("mc");

    public final ComparablePath<com.vividsolutions.jts.geom.Geometry> shape = createComparable("shape", com.vividsolutions.jts.geom.Geometry.class);

    public QXZQH_XZDY(String variable) {
        super(XZQH_XZDY.class, forVariable(variable));
    }

    public QXZQH_XZDY(Path<? extends XZQH_XZDY> path) {
        super(path.getType(), path.getMetadata());
    }

    public QXZQH_XZDY(PathMetadata metadata) {
        super(XZQH_XZDY.class, metadata);
    }

}

