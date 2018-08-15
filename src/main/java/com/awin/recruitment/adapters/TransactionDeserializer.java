package com.awin.recruitment.adapters;

import com.awin.recruitment.model.Product;
import com.awin.recruitment.model.Transaction;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TransactionDeserializer implements JsonDeserializer<Transaction> {
    @Override
    public Transaction deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        final JsonObject jsonObject = jsonElement.getAsJsonObject();
        final JsonElement jsonID = jsonObject.get("ID");
        final JsonElement jsonSaleDate = jsonObject.get("saleDate");
        final JsonArray jsonProductList = jsonObject.get("productList").getAsJsonArray();
        final List<Product> productList = new ArrayList<>();
        for (int i = 0; i < jsonProductList.size(); i++) {
            final JsonObject jsonProduct = jsonProductList.get(i).getAsJsonObject();
            Product product = new Product(jsonProduct.get("name").getAsString(), jsonProduct.get("amount").getAsBigDecimal());
            productList.add(product);
        }

        return new Transaction(jsonID.getAsString(), jsonSaleDate.getAsString(), productList);
    }
}
