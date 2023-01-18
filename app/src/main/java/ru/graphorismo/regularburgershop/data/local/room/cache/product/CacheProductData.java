package ru.graphorismo.regularburgershop.data.local.room.cache.product;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity
public class CacheProductData {
    @PrimaryKey
    @NonNull
    private UUID uuid;
    @NonNull
    private  String title;
    @NonNull
    private  String name;
    @NonNull
    private  Integer price;
    @NonNull
    private String pictureUrl;

    public CacheProductData(@NonNull String title, @NonNull String name,
                            @NonNull Integer price, @NonNull String pictureUrl) {
        this.uuid = UUID.randomUUID();
        this.title = title;
        this.name = name;
        this.price = price;
        this.pictureUrl = pictureUrl;
    }

    @NonNull
    public UUID getUuid() {
        return uuid;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public Integer getPrice() {
        return price;
    }

    @NonNull
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
