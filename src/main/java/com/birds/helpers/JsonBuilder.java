package com.birds.helpers;

import com.birds.model.Bird;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;

public class JsonBuilder {

    private final JsonObject jsonObject;
    private static final Gson gson = new Gson();

    public JsonBuilder() {
        jsonObject = new JsonObject();
    }

    public JsonBuilder(Object o) {
        jsonObject = gson.toJsonTree(o).getAsJsonObject();
    }

    public void addProperty(String key, String value) {
        jsonObject.addProperty(key, value);
    }

    public void addProperty(String key, Object value) {
        jsonObject.add(key, gson.toJsonTree(value));
    }

    public String build() {
        final String s = gson.toJson(jsonObject);
        System.out.println("value is" + s);
        return s;
    }

    public static class JsonBuilderFactory {
        public JsonBuilder newBuilder(Object object) {
            return new JsonBuilder(object);
        }

        public JsonBuilder newBuilder() {
            return new JsonBuilder();
        }
        public static JsonBuilderFactory jsonBuilderFactory(){
            return new JsonBuilderFactory();
        }
    }
}
