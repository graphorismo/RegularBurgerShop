package ru.graphorismo.regularburgershop.data.local.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity
public class ProductCartData {
    @PrimaryKey
    @NonNull
    private  UUID uuid;
    @NonNull
    private  String title;
    @NonNull
    private  String name;
    @NonNull
    private  Integer price;
    @NonNull
    private String pictureUrl;

    public ProductCartData(String title, String name, Integer price, String pictureUrl) {
        this.uuid = UUID.randomUUID();
        this.title = title;
        this.name = name;
        this.price = price;
        this.pictureUrl = pictureUrl;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setUuid(@NonNull UUID uuid) {
        this.uuid = uuid;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setPrice(@NonNull Integer price) {
        this.price = price;
    }

    public void setPictureUrl(@NonNull String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
