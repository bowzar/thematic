package com.yulintu.thematic.spatial.databind;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Strings;
import com.yulintu.thematic.spatial.Geometry;
import com.yulintu.thematic.spatial.GeometryUtils;

import java.io.IOException;

public class GeometryJsonDeserializer extends JsonDeserializer {

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        String json = jsonParser.getText();
        if (Strings.isNullOrEmpty(json))
            return null;

        Geometry geometry = Geometry.fromGeoJson(json);
        return GeometryUtils.toLatteGeometry(geometry.getInstance());
    }
}
