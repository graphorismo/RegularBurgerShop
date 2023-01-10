package ru.graphorismo.regularburgershop.data.remote.retrofit;

import ru.graphorismo.regularburgershop.data.Product;

public abstract class ConverterProductResponseToProduct {

    static public Product convert(ProductResponse productResponse){
        return new Product(productResponse.getTitle(),
                productResponse.getName(),
                productResponse.getPrice(),
                productResponse.getPictureUrl());
    }
}
