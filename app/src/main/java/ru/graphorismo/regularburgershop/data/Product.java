package ru.graphorismo.regularburgershop.data;

import androidx.annotation.Nullable;

import java.util.Objects;

public class Product {

    private final String title;
    private final String name;
    private final Integer price;
    private final String pictureUrl;

    public Product() {
        title = "Dummy title";
        name = "Dummy name";
        price = 0;
        pictureUrl= "";
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return title.equals(product.title) && name.equals(product.name) && price.equals(product.price) && pictureUrl.equals(product.pictureUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, name, price, pictureUrl);
    }
}
