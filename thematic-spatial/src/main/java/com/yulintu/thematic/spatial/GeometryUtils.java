package com.yulintu.thematic.spatial;

import com.esri.core.geometry.*;
import com.esri.core.geometry.SpatialReference;
import com.vividsolutions.jts.io.WKBWriter;
import com.vividsolutions.jts.io.WKTWriter;
import org.geolatte.geom.jts.JTS;

import java.nio.ByteBuffer;

public class GeometryUtils {

    //region methods - toGeometry
    public static com.vividsolutions.jts.geom.Geometry toJTSGeometry(org.geolatte.geom.Geometry geo) {
        return geo == null ? null : JTS.to(geo);

    }

    public static com.vividsolutions.jts.geom.Geometry toJTSGeometry(com.esri.core.geometry.MapGeometry geo) {
        return geo == null ? null : toGeometry(geo).getInstance();
    }

    public static Geometry toGeometry(com.esri.core.geometry.MapGeometry geo) {
        if (geo == null)
            return null;

        ByteBuffer buffer = OperatorExportToWkb.local().execute(
                WkbExportFlags.wkbExportDefaults, geo.getGeometry(), null);

        byte[] wkb = buffer.array();
        Geometry newGeometry = Geometry.fromWkb(wkb);

        SpatialReference sr = geo.getSpatialReference();
        if (sr != null)
            newGeometry.setSpatialReference(
                    new com.yulintu.thematic.spatial.SpatialReference(
                            sr.getID(), sr.getText()));

        return newGeometry;
    }

    public static org.geolatte.geom.Geometry toLatteGeometry(com.vividsolutions.jts.geom.Geometry geo) {
        return geo == null ? null : JTS.from(geo);
    }

    public static com.esri.core.geometry.MapGeometry toEsriGeometry(com.vividsolutions.jts.geom.Geometry geo) {
        if (geo == null)
            return null;

        byte[] wkb = new WKBWriter().write(geo);
        com.esri.core.geometry.Geometry geometry = OperatorImportFromWkb.local().execute(
                WkbImportFlags.wkbImportDefaults,
                com.esri.core.geometry.Geometry.Type.Unknown,
                ByteBuffer.wrap(wkb), null);

        return new MapGeometry(geometry, SpatialReference.create(geo.getSRID()));
    }

    public static com.esri.core.geometry.MapGeometry toEsriGeometry(org.geolatte.geom.Geometry geo) {
        if (geo == null)
            return null;

        byte[] wkb = new WKBWriter().write(toJTSGeometry(geo));
        com.esri.core.geometry.Geometry geometry = OperatorImportFromWkb.local().execute(
                WkbImportFlags.wkbImportDefaults,
                com.esri.core.geometry.Geometry.Type.Unknown,
                ByteBuffer.wrap(wkb), null);

        return new MapGeometry(geometry, SpatialReference.create(geo.getSRID()));
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

    public static String toGeoJson(com.esri.core.geometry.MapGeometry geo) {
        return OperatorExportToGeoJson.local().execute(geo.getSpatialReference(), geo.getGeometry());
    }

    public static Geometry fromGeoJson(String json) {

        MapGeometry mg = OperatorImportFromGeoJson.local().execute(
                GeoJsonImportFlags.geoJsonImportDefaults,
                com.esri.core.geometry.Geometry.Type.Unknown,
                json,
                null);

        return GeometryUtils.toGeometry(mg);
    }
    //endregion
}
