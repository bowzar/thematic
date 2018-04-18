package com.yulintu.thematic.web.databind;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.yulintu.thematic.spatial.databind.GeometryJsonDeserializer;
import com.yulintu.thematic.spatial.databind.GeometryJsonSerializer;
import org.geolatte.geom.Geometry;

public class GeometryMapperModule extends SimpleModule {

    public GeometryMapperModule() {
        super();

        addSerializer(Geometry.class, new GeometryJsonSerializer());
        addDeserializer(Geometry.class, new GeometryJsonDeserializer());
    }
}
