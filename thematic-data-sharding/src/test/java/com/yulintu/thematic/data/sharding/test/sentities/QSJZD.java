package com.yulintu.thematic.data.sharding.test.sentities;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.StringPath;
import com.yulintu.thematic.data.sharding.test.entities.SJZD;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QSJZD is a Querydsl query type for SJZD
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSJZD extends EntityPathBase<SJZD> {

    private static final long serialVersionUID = -1400335442L;

    public static final QSJZD sJZD = new QSJZD("sJZD");

    public final StringPath bm = createString("bm");

    public final StringPath fzm = createString("fzm");

    public final StringPath id = createString("id");

    public final StringPath mc = createString("mc");

    public QSJZD(String variable) {
        super(SJZD.class, forVariable(variable));
    }

    public QSJZD(Path<? extends SJZD> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSJZD(PathMetadata metadata) {
        super(SJZD.class, metadata);
    }

}

