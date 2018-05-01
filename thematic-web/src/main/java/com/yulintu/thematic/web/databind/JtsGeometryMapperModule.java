package com.yulintu.thematic.web.databind;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.vividsolutions.jts.geom.Geometry;
import com.yulintu.thematic.spatial.databind.JtsGeometryJsonDeserializer;
import com.yulintu.thematic.spatial.databind.JtsGeometryJsonSerializer;

public class JtsGeometryMapperModule extends SimpleModule {

    public JtsGeometryMapperModule() {
        super();

        addSerializer(Geometry.class, new JtsGeometryJsonSerializer());
        addDeserializer(Geometry.class, new JtsGeometryJsonDeserializer());
    }
}
