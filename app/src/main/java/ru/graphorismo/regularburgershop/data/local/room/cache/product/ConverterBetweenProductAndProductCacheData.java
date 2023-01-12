package ru.graphorismo.regularburgershop.data.local.room.cache.product;

import ru.graphorismo.regularburgershop.data.Product;

public abstract class ConverterBetweenProductAndProductCacheData {

    public static ProductCacheData convertFromProductToProductCacheData(Product product){
        return new ProductCacheData(product.getTitle(), product.getName(),
                product.getPrice(), product.getPictureUrl());
    }

    public static Product convertFromProductCacheDataToProduct(ProductCacheData productCacheData){
        return new Product(productCacheData.getTitle(), productCacheData.getName(), productCacheData.getPrice(), productCacheData.getPictureUrl());
    }


}
