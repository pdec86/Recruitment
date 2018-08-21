package com.awin.recruitment.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TransactionFromStream {
    private String ID;
    private String saleDate;
    private List<Product> productList = new ArrayList<>();

    public TransactionFromStream(String ID, String saleDate, List<Product> productList) {
        this.ID = ID;
        this.saleDate = saleDate;
        this.productList = productList;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionFromStream that = (TransactionFromStream) o;
        if (!Objects.equals(ID, that.ID))
            return false;
        if (!Objects.equals(saleDate, that.saleDate))
            return false;
        if (productList.size() != that.productList.size())
            return false;
        for (int i = 0; i < productList.size(); i++) {
            if (!productList.get(i).equals(that.productList.get(i)))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, saleDate, productList);
    }

    @Override
    public String toString() {
        return "TransactionFromStream{" +
                "ID='" + ID + '\'' +
                ", saleDate='" + saleDate + '\'' +
                ", productList=" + productList +
                '}';
    }
}
