package ru.graphorismo.regularburgershop.data.local.room.cache.product;

import ru.graphorismo.regularburgershop.data.Product;

public abstract class ConverterBetweenProductAndCacheProductData {

    public static CacheProductData convertFromProductToCacheProductData(Product product){
        return new CacheProductData(product.getTitle(), product.getName(),
                product.getPrice(), product.getPictureUrl());
    }

    public static Product convertFromCacheProductDataToProduct(CacheProductData cacheProductData){
        return new Product(cacheProductData.getTitle(), cacheProductData.getName(), cacheProductData.getPrice(), cacheProductData.getPictureUrl());
    }


}
