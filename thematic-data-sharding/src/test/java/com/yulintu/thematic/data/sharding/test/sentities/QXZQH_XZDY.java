package com.yulintu.thematic.data.sharding.test.sentities;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.ComparablePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.StringPath;
import com.yulintu.thematic.data.sharding.test.entities.XZQH_XZDY;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


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

