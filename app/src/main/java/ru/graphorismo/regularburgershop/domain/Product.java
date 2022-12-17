package ru.graphorismo.regularburgershop.domain;

import androidx.annotation.Nullable;

import java.util.Objects;

public class Product {

    private String name;
    private Integer price;

    public Product(String name, Integer price) {
        if(price < 0) throw new RuntimeException("Price cant be negative");
        if(name.length() == 0) throw new RuntimeException("Name cant be empty");
        this.name = name;
        this.price = price;
    }

    public String getName() {return name;}
    public Integer getPrice() {return price;}

    @Override
    public int hashCode() {
        return name.hashCode() + price;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        else return (Objects.equals(name, ((Product) obj).name)
                && Objects.equals(price, ((Product) obj).price));
    }
}
