package ru.graphorismo.regularburgershop.data.remote.retrofit;

public class ProductResponse{

    private final String title;
    private final String name;
    private final Integer price;
    private final String pictureUrl;

    public ProductResponse(String title, String name, Integer price, String pictureUrl) {
        this.title = title;
        this.name = name;
        this.price = price;
        this.pictureUrl = pictureUrl;
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
}
