package com.yulintu.thematic.spatial.test;


import com.yulintu.thematic.spatial.Geometry;
import com.yulintu.thematic.spatial.GeometryUtils;
import com.yulintu.thematic.spatial.SpatialReference;
import org.junit.Test;

public class TestGeometry {

    @Test
    public void testWkt() {

        String wktString = "MULTIPOLYGON (((0.1 0.7, 0.1 0.4, 0.3 0.4, 0.3 0.7, 0.1 0.7)),"
                + "((0 0, 0.5 0, 1 0.5, 0.5 1, 0 1, -0.5 0.5, 0 0),"
                + "(0.5 0.2, 0.2 0.3, 0.1 0.2, -0.2 0.5, 0.2 0.9, 0.6 0.5, 0.5 0.2)))";

        Geometry geometry = Geometry.fromWkt(wktString);
        byte[] wkb = geometry.asWkb();
        Geometry geo2 = Geometry.fromWkb(wkb);
        geometry.setSpatialReference(new SpatialReference(3857,null));

        com.esri.core.geometry.Geometry geometry2 = GeometryUtils.toEsriGeometry(geometry.getInstance());
        org.geolatte.geom.Geometry geometry1 = GeometryUtils.toLatteGeometry(geometry.getInstance());

        String json = GeometryUtils.toGeoJson(geometry2);

    }
}
