package com.yulintu.thematic.spatial.databind;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Strings;
import com.yulintu.thematic.spatial.Geometry;

import java.io.IOException;

public class JtsGeometryJsonDeserializer extends JsonDeserializer {

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        String json = jsonParser.getText();
        if (Strings.isNullOrEmpty(json))
            return null;

        Geometry geometry = Geometry.fromGeoJson(json);
        return geometry.getInstance();
    }
}
