package com.jobportal.util;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

import com.google.gson.*;

public class LocalDateTimeAdapter 
        implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }

    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT,
                                     JsonDeserializationContext context) {
        return LocalDateTime.parse(json.getAsString());
    }
}
