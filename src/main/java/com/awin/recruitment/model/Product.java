package com.awin.recruitment.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private String name;
    private BigDecimal amount;

    public Product(String name, BigDecimal amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) &&
                Objects.equals(amount, product.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, amount);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }
}