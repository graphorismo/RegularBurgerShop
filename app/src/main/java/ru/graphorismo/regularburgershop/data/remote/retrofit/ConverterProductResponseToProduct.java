package ru.graphorismo.regularburgershop.data.remote.retrofit;

import ru.graphorismo.regularburgershop.data.Product;

public class ConverterProductResponseToProduct {

    public ConverterProductResponseToProduct() {
    }

    public Product convertProductResponseToProduct(ProductResponse productResponse){
        return new Product(productResponse.getTitle(),
                productResponse.getName(),
                productResponse.getPrice(),
                productResponse.getPictureUrl());
    }
}
