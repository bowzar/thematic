package com.yulintu.thematic.data.hibernate.test.sentities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.yulintu.thematic.data.hibernate.test.entities.SJZD;


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

