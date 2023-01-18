package ru.graphorismo.regularburgershop.data.local.room.cart.coupon;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.graphorismo.regularburgershop.data.Coupon;
import ru.graphorismo.regularburgershop.data.local.ILocalDataRepository;
import ru.graphorismo.regularburgershop.data.local.room.cache.product.CacheProductData;
import ru.graphorismo.regularburgershop.data.local.room.cache.product.ConverterBetweenProductAndCacheProductData;

public class ConverterBetweenCouponAndCartCouponData {

    public static CartCouponData convertFromCouponToCartCouponData(Coupon coupon){
        return new CartCouponData(coupon.getCouponName(), coupon.getProduct().getName(),
                coupon.getDiscountPercents());
    }

    public static Single<Coupon> convertFromCartCouponDataToCoupon(CartCouponData cartCouponData, ILocalDataRepository localDataRepository){
        String productName = cartCouponData.getProductName();
        Single<List<CacheProductData>> productCacheDataListObservable = localDataRepository.getCacheProductUnderName(productName);
        return productCacheDataListObservable
                .subscribeOn(Schedulers.computation())
                .map(productCacheDataList -> {
                    return productCacheDataList.get(0);
                })
                .map(ConverterBetweenProductAndCacheProductData::convertFromCacheProductDataToProduct)
                .map(product -> {
                    return new Coupon(cartCouponData.getCouponName(), product, cartCouponData.getDiscountPercents());
                });
    }
}
