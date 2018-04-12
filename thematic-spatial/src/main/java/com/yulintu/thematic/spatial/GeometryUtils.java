package com.yulintu.thematic.spatial;

import org.geolatte.geom.jts.JTS;

public class GeometryUtils {

    public  static com.vividsolutions.jts.geom.Geometry toJTSGeometry(org.geolatte.geom.Geometry geo){
        return  JTS.to(geo);
    }

    public  static org.geolatte.geom.Geometry toLatteGeometry(com.vividsolutions.jts.geom.Geometry geo){
        return JTS.from(geo);
    }
}
