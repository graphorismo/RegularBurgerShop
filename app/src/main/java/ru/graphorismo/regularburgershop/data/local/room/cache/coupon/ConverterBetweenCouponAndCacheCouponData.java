package ru.graphorismo.regularburgershop.data.local.room.cache.coupon;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.graphorismo.regularburgershop.data.Coupon;
import ru.graphorismo.regularburgershop.data.local.ILocalDataRepository;
import ru.graphorismo.regularburgershop.data.local.room.cache.product.CacheProductData;
import ru.graphorismo.regularburgershop.data.local.room.cache.product.ConverterBetweenProductAndCacheProductData;

public abstract class ConverterBetweenCouponAndCacheCouponData {

    public static CacheCouponData convertFromCouponToCacheCouponData(Coupon coupon){
        return new CacheCouponData(coupon.getCouponName(), coupon.getProduct().getName(),
                coupon.getDiscountPercents());
    }

    public static Single<Coupon> convertFromCacheCouponDataToCoupon(CacheCouponData cacheCouponData, ILocalDataRepository localDataRepository){
        String productName = cacheCouponData.getProductName();
        Single<List<CacheProductData>> productCacheDataListObservable = localDataRepository.getCacheProductUnderName(productName);
        return productCacheDataListObservable
                .subscribeOn(Schedulers.computation())
                .map(productCacheDataList -> {
                    return productCacheDataList.get(0);
                })
                .map(ConverterBetweenProductAndCacheProductData::convertFromCacheProductDataToProduct)
                .map(product -> {
                    return new Coupon(cacheCouponData.getCouponName(), product, cacheCouponData.getDiscountPercents());
                });
    }


}
