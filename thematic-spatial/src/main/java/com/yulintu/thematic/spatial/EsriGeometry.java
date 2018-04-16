//package com.yulintu.thematic.spatial;
//
//import com.esri.core.geometry.*;
//import com.google.common.base.Strings;
//import com.yulintu.thematic.AssertUtils;
//
//import java.nio.ByteBuffer;
//
//public class EsriGeometry {
//
//    //region wkt
//    private volatile String wkt;
//
//    public String getWkt() {
//
//        if (Strings.isNullOrEmpty(wkt)) {
//            AssertUtils.notNull(instance, "instance");
//            wkt = OperatorExportToWkt.local().execute(
//                    WktExportFlags.wktExportDefaults, instance, null);
//        }
//
//        return wkt;
//    }
//
//    public synchronized void setWkt(String wkt) {
//
//        AssertUtils.notNull(wkt, "wkt");
//        this.wkt = wkt;
//
//        instance = OperatorImportFromWkt.local().execute(
//                WktImportFlags.wktImportDefaults,
//                com.esri.core.geometry.Geometry.Type.Unknown, wkt, null);
//
//    }
//    //endregion
//
//    //region spatialReference
//    private SpatialReference spatialReference;
//
//    public SpatialReference getSpatialReference() {
//        return spatialReference;
//    }
//
//    public void setSpatialReference(SpatialReference spatialReference) {
//        this.spatialReference = spatialReference;
//    }
//    //endregion
//
//    //region instance
//    private com.esri.core.geometry.Geometry instance;
//
//    public com.esri.core.geometry.Geometry getInstance() {
//        return instance;
//    }
//    //endregion
//
//    //region ctor
//    public EsriGeometry() {
//
//    }
//    //endregion
//
//    //region methods
//    //region methods - static
//    public static EsriGeometry from(com.esri.core.geometry.Geometry instance) {
//        EsriGeometry geo = new EsriGeometry();
//        geo.instance = instance;
//        return geo;
//    }
//
//    public static EsriGeometry fromWkt(String wkt) {
//        EsriGeometry esriGeometry = new EsriGeometry();
//        esriGeometry.setWkt(wkt);
//        return esriGeometry;
//    }
//
//    public static EsriGeometry fromWkb(byte[] wkb) {
//
//        EsriGeometry esriGeometry = new EsriGeometry();
//        esriGeometry.instance = OperatorImportFromWkb.local().execute(
//                WkbImportFlags.wkbImportDefaults,
//                com.esri.core.geometry.Geometry.Type.Unknown,
//                ByteBuffer.wrap(wkb), null);
//
//        return esriGeometry;
//    }
//    //endregion
//
//    //region public
//    public String asWkt() {
//        return getWkt();
//    }
//
//    public byte[] asWkb() {
//        ByteBuffer buffer = OperatorExportToWkb.local().execute(
//                WkbExportFlags.wkbExportDefaults, instance, null);
//
//        return buffer.array();
//    }
//    //endregion
//
//    //endregion
//}
