package com.yulintu.thematic.spatial;

import com.esri.core.geometry.*;
import com.vividsolutions.jts.io.WKBWriter;
import com.vividsolutions.jts.io.WKTWriter;
import org.geolatte.geom.jts.JTS;

import java.nio.ByteBuffer;

public class GeometryUtils {

    //region methods - toGeometry
    public static com.vividsolutions.jts.geom.Geometry toJTSGeometry(org.geolatte.geom.Geometry geo) {
        return JTS.to(geo);
    }

    public static org.geolatte.geom.Geometry toLatteGeometry(com.vividsolutions.jts.geom.Geometry geo) {
        return JTS.from(geo);
    }

    public static com.esri.core.geometry.Geometry toEsriGeometry(com.vividsolutions.jts.geom.Geometry geo) {

        byte[] wkb = new WKBWriter().write(geo);
        return OperatorImportFromWkb.local().execute(
                WkbImportFlags.wkbImportDefaults,
                com.esri.core.geometry.Geometry.Type.Unknown,
                ByteBuffer.wrap(wkb), null);
    }

    public static com.esri.core.geometry.Geometry toEsriGeometry(org.geolatte.geom.Geometry geo) {

        byte[] wkb = new WKBWriter().write(toJTSGeometry(geo));
        return OperatorImportFromWkb.local().execute(
                WkbImportFlags.wkbImportDefaults,
                com.esri.core.geometry.Geometry.Type.Unknown,
                ByteBuffer.wrap(wkb), null);
    }
    //endregion

    //region methods - wkt
    public static String toWkt(org.geolatte.geom.Geometry geo) {
        return new WKTWriter().write(toJTSGeometry(geo));
    }

    public static String toWkt(com.vividsolutions.jts.geom.Geometry geo) {
        return new WKTWriter().write(geo);
    }

    public static String toWkt(com.esri.core.geometry.Geometry geo) {
        return OperatorExportToWkt.local().execute(
                WktExportFlags.wktExportDefaults, geo, null);
    }
    //endregion

    //region methods - geojson
    public static String toGeoJson(org.geolatte.geom.Geometry geo) {
        return toGeoJson(toEsriGeometry(geo));
    }

    public static String toGeoJson(com.vividsolutions.jts.geom.Geometry geo) {
        return toGeoJson(toEsriGeometry(geo));
    }

    public static String toGeoJson(com.esri.core.geometry.Geometry geo) {
        return OperatorExportToGeoJson.local().execute(geo);
    }
    //endregion
}
