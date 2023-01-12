package ru.graphorismo.regularburgershop.data.local.room.cart;

import ru.graphorismo.regularburgershop.data.Product;

public abstract class ConverterBetweenProductAndProductCartData {

    public static ProductCartData convertProductToProductCartData(Product product){
        return new ProductCartData(
                product.getTitle(),
                product.getName(),
                product.getPrice(),
                product.getPictureUrl());
    }

    public static Product convertProductCartDataToProduct(ProductCartData productCartData){
        return new Product(
                productCartData.getTitle(),
                productCartData.getName(),
                productCartData.getPrice(),
                productCartData.getPictureUrl());
    }
}
