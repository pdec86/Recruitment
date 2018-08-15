package com.awin.recruitment.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private String ID;
    private String saleDate;
    private List<Product> productList = new ArrayList<>();
    private BigDecimal amountSum = new BigDecimal(0);

    public Transaction(String ID, String saleDate, List<Product> productList) {
        this.ID = ID;
        this.saleDate = saleDate;
        this.productList = productList;
        for (Product product : this.productList) {
            amountSum = amountSum.add(product.getAmount());
        }
    }

    public String getID() {
        return ID;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public List<Product> getProductList() {
        return productList;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "ID='" + ID + '\'' +
                ", saleDate=" + saleDate +
                ", productList=" + productList +
                ", amountSum=" + amountSum +
                '}';
    }
}