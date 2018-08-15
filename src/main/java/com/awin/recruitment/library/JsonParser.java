package com.awin.recruitment.library;

public interface JsonParser {
    String toJson(Object source);

    <T> T fromJson(String json, Class<T> classOfT);
}
