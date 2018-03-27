package com.yulintu.thematic.spatial.test;

import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.OperatorImportFromWkt;
import com.esri.core.geometry.WktImportFlags;
import org.junit.Test;

public class TestEsriGeometry {

    @Test
    public void testWKT() {

        String wktString = "MULTIPOLYGON (((0.1 0.7, 0.1 0.4, 0.3 0.4, 0.3 0.7, 0.1 0.7)),"
                + "((0 0, 0.5 0, 1 0.5, 0.5 1, 0 1, -0.5 0.5, 0 0),"
                + "(0.5 0.2, 0.2 0.3, 0.1 0.2, -0.2 0.5, 0.2 0.9, 0.6 0.5, 0.5 0.2)))";

        Geometry geom = OperatorImportFromWkt.local().execute(
                WktImportFlags.wktImportDefaults, Geometry.Type.Polygon, wktString, null);
        Geometry geom1 = OperatorImportFromWkt.local().execute(
                WktImportFlags.wktImportDefaults, Geometry.Type.Unknown, wktString, null);
    }
}
