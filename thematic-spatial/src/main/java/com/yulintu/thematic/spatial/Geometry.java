package com.yulintu.thematic.spatial;

import com.esri.core.geometry.*;
import com.google.common.base.Strings;
import com.yulintu.thematic.AssertUtils;

import java.nio.ByteBuffer;

public class Geometry {

    //region wkt
    private volatile String wkt;

    public String getWkt() {

        if (Strings.isNullOrEmpty(wkt)) {
            AssertUtils.notNull(instance, "instance");
            wkt = OperatorExportToWkt.local().execute(
                    WktExportFlags.wktExportDefaults, instance, null);
        }

        return wkt;
    }

    public synchronized void setWkt(String wkt) {

        AssertUtils.notNull(wkt, "wkt");
        this.wkt = wkt;

        instance = OperatorImportFromWkt.local().execute(
                WktImportFlags.wktImportDefaults,
                com.esri.core.geometry.Geometry.Type.Unknown, wkt, null);

    }
    //endregion

    //region spatialReference
    private SpatialReference spatialReference;

    public SpatialReference getSpatialReference() {
        return spatialReference;
    }

    public void setSpatialReference(SpatialReference spatialReference) {
        this.spatialReference = spatialReference;
    }
    //endregion

    //region instance
    private com.esri.core.geometry.Geometry instance;

    public com.esri.core.geometry.Geometry getInstance() {
        return instance;
    }
    //endregion

    //region ctor
    public Geometry() {

    }
    //endregion

    //region methods
    //region methods - static
    public static Geometry from(com.esri.core.geometry.Geometry instance) {
        Geometry geo = new Geometry();
        geo.instance = instance;
        return geo;
    }

    public static Geometry fromWkt(String wkt) {
        Geometry geometry = new Geometry();
        geometry.setWkt(wkt);
        return geometry;
    }

    public static Geometry fromWkb(byte[] wkb) {

        Geometry geometry = new Geometry();
        geometry.instance = OperatorImportFromWkb.local().execute(
                WkbImportFlags.wkbImportDefaults,
                com.esri.core.geometry.Geometry.Type.Unknown,
                ByteBuffer.wrap(wkb), null);

        return geometry;
    }
    //endregion

    //region public
    public String asWkt() {
        return getWkt();
    }

    public byte[] asWkb() {
        ByteBuffer buffer = OperatorExportToWkb.local().execute(
                WkbExportFlags.wkbExportDefaults, instance, null);

        return buffer.array();
    }
    //endregion

    //endregion
}
