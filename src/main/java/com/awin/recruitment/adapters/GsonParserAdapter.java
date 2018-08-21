package com.awin.recruitment.adapters;

import com.awin.recruitment.library.JsonParser;
import com.awin.recruitment.model.TransactionFromStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonParserAdapter implements JsonParser {
    private Gson gson;

    public GsonParserAdapter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(TransactionFromStream.class, new TransactionFromStreamDeserializer());
        this.gson = gsonBuilder.create();
    }

    @Override
    public String toJson(Object source) {
        return gson.toJson(source);
    }

    @Override
    public <T> T fromJson(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }
}
