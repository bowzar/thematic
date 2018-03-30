package com.yulintu.thematic.spatial.test;


import com.yulintu.thematic.spatial.Geometry;
import org.junit.Test;

public class TestEsriGeometry {

    @Test
    public void testWkt() {

        String wktString = "MULTIPOLYGON (((0.1 0.7, 0.1 0.4, 0.3 0.4, 0.3 0.7, 0.1 0.7)),"
                + "((0 0, 0.5 0, 1 0.5, 0.5 1, 0 1, -0.5 0.5, 0 0),"
                + "(0.5 0.2, 0.2 0.3, 0.1 0.2, -0.2 0.5, 0.2 0.9, 0.6 0.5, 0.5 0.2)))";

        Geometry geometry = Geometry.fromWkt(wktString);
        byte[] wkb = geometry.asWkb();
        Geometry geo2 = geometry.fromWkb(wkb);

    }
}
