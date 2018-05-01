package com.yulintu.thematic.spatial.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.vividsolutions.jts.geom.Geometry;

import java.io.IOException;

public class JtsGeometryJsonSerializer extends JsonSerializer<Geometry> {

    @Override
    public void serialize(Geometry geometry, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeObject(com.yulintu.thematic.spatial.Geometry.from(geometry).asGeoJson());
    }
}
