package ru.graphorismo.regularburgershop.data.local.room.cart.product;

import ru.graphorismo.regularburgershop.data.Product;

public abstract class ConverterBetweenProductAndCartProductData {

    public static CartProductData convertProductToCartProductData(Product product){
        return new CartProductData(
                product.getTitle(),
                product.getName(),

                product.getPrice(),
                product.getPictureUrl());
    }

    public static Product convertCartProductDataToProduct(CartProductData productCartData){
        return new Product(
                productCartData.getTitle(),
                productCartData.getName(),
                productCartData.getPrice(),
                productCartData.getPictureUrl());
    }
}
