package com.yulintu.thematic.spatial;

import com.esri.core.geometry.MapGeometry;
import com.google.common.base.Strings;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;
import com.vividsolutions.jts.io.WKBWriter;
import com.vividsolutions.jts.io.WKTReader;
import com.yulintu.thematic.AssertUtils;

public class Geometry {

    //region wkt
    private volatile String wkt;

    public String getWkt() {

        if (Strings.isNullOrEmpty(wkt)) {
            AssertUtils.notNull(instance, "instance");
            wkt = instance.toText();
        }

        return wkt;
    }

    public synchronized void setWkt(String wkt) {

        AssertUtils.notNull(wkt, "wkt");
        this.wkt = wkt;

        try {
            instance = new WKTReader().read(wkt);
            instance.setSRID(spatialReference.getWkid());

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    //endregion

    //region spatialReference
    private SpatialReference spatialReference = new SpatialReference();

    public SpatialReference getSpatialReference() {
        return spatialReference;
    }

    public void setSpatialReference(SpatialReference spatialReference) {
        if (spatialReference == null)
            spatialReference = new SpatialReference();

        this.spatialReference = spatialReference;
        instance.setSRID(spatialReference.getWkid());
    }
    //endregion

    //region instance
    private com.vividsolutions.jts.geom.Geometry instance;

    public com.vividsolutions.jts.geom.Geometry getInstance() {
        return instance;
    }
    //endregion

    //region ctor
    public Geometry() {

    }
    //endregion

    //region methods
    //region methods - static
    public static Geometry from(com.vividsolutions.jts.geom.Geometry instance) {
        Geometry geo = new Geometry();
        geo.instance = instance;
        geo.spatialReference = new SpatialReference(instance.getSRID());
        return geo;
    }

    public static Geometry fromWkt(String wkt) {
        Geometry geometry = new Geometry();
        geometry.setWkt(wkt);
        return geometry;
    }

    public static Geometry fromWkb(byte[] wkb) {

        Geometry geometry = new Geometry();
        try {
            geometry.instance = new WKBReader().read(wkb);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return geometry;
    }

    public static Geometry fromGeoJson(String json) {
        return GeometryUtils.fromGeoJson(json);
    }
    //endregion

    //region public
    public String asWkt() {
        return getWkt();
    }

    public byte[] asWkb() {
        return new WKBWriter().write(instance);
    }

    public String asGeoJson() {
        return GeometryUtils.toGeoJson(GeometryUtils.toEsriGeometry(instance));
    }
    //endregion

    //endregion
}
