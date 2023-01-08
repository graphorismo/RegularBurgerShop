package ru.graphorismo.regularburgershop.data;

import androidx.annotation.Nullable;

import java.util.Objects;

public class Product {

    private final String title;
    private final String name;
    private final Integer price;
    private final String pictureUrl;

    public Product(String title, String name, Integer price, String pictureUrl) {
        if(price < 0) throw new RuntimeException("Price cant be negative");
        if(name.length() == 0) throw new RuntimeException("Name cant be empty");

        this.title = title;
        this.name = name;
        this.price = price;
        this.pictureUrl = pictureUrl;
    }

    public String getName() {return name;}
    public Integer getPrice() {return price;}
    public String getTitle() {return title;}

    public String getPictureUrl() {
        return pictureUrl;
    }

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
