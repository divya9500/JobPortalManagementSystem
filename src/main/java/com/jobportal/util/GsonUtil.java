package com.jobportal.util;



import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

public class GsonUtil {

    public static Gson createGson() {

        return new GsonBuilder()
            .registerTypeAdapter(
                LocalDateTime.class,
                (JsonSerializer<LocalDateTime>)
                    (src, type, ctx) -> ctx.serialize(src.toString())
            )
            .registerTypeAdapter(
                LocalDateTime.class,
                (JsonDeserializer<LocalDateTime>)
                    (json, type, ctx) -> LocalDateTime.parse(json.getAsString())
            )
            .create();
    }
}

