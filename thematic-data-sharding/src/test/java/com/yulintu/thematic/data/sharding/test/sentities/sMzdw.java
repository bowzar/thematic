package com.yulintu.thematic.data.sharding.test.sentities;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.spatial.GeometryPath;
import com.querydsl.sql.ColumnMetadata;
import com.querydsl.sql.spatial.RelationalPathSpatial;
import com.yulintu.thematic.data.sharding.test.entities.Mzdw;

import javax.annotation.Generated;
import java.sql.Types;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * sMzdw is a Querydsl query type for Mzdw
 */
@Generated("com.querydsl.sql.codegen.MetaDataSerializer")
public class sMzdw extends RelationalPathSpatial<Mzdw> {

    private static final long serialVersionUID = -2022627599;

    public static final sMzdw mzdw = new sMzdw("mzdw");

    public final NumberPath<Integer> bsm = createNumber("bsm", Integer.class);

    public final StringPath dwmc = createString("dwmc");

    public final StringPath id = createString("id");

    public final NumberPath<Double> mj = createNumber("mj", Double.class);

    public final GeometryPath<org.geolatte.geom.Geometry> shape = createGeometry("shape", org.geolatte.geom.Geometry.class);

    public sMzdw(String variable) {
        super(Mzdw.class, forVariable(variable), "public", "mzdw");
        addMetadata();
    }

    public sMzdw(String variable, String schema, String table) {
        super(Mzdw.class, forVariable(variable), schema, table);
        addMetadata();
    }

    public sMzdw(String variable, String schema) {
        super(Mzdw.class, forVariable(variable), schema, "mzdw");
        addMetadata();
    }

    public sMzdw(Path<? extends Mzdw> path) {
        super(path.getType(), path.getMetadata(), "public", "mzdw");
        addMetadata();
    }

    public sMzdw(PathMetadata metadata) {
        super(Mzdw.class, metadata, "public", "mzdw");
        addMetadata();
    }

    public void addMetadata() {
        addMetadata(bsm, ColumnMetadata.named("bsm").withIndex(1).ofType(Types.INTEGER).withSize(10).notNull());
        addMetadata(dwmc, ColumnMetadata.named("dwmc").withIndex(4).ofType(Types.VARCHAR).withSize(14));
        addMetadata(id, ColumnMetadata.named("id").withIndex(2).ofType(Types.VARCHAR).withSize(38));
        addMetadata(mj, ColumnMetadata.named("mj").withIndex(3).ofType(Types.DOUBLE).withSize(17).withDigits(17));
        addMetadata(shape, ColumnMetadata.named("shape").withIndex(5).ofType(Types.OTHER).withSize(2147483647));
    }

}

