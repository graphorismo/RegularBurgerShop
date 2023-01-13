package ru.graphorismo.regularburgershop.data.local.room.cache.coupon;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.graphorismo.regularburgershop.data.Coupon;
import ru.graphorismo.regularburgershop.data.local.ILocalDataRepository;
import ru.graphorismo.regularburgershop.data.local.room.cache.product.ConverterBetweenProductAndProductCacheData;
import ru.graphorismo.regularburgershop.data.local.room.cache.product.ProductCacheData;

public abstract class ConverterBetweenCouponAndCouponCacheData {

    public static CouponCacheData convertFromCouponToCouponCacheData(Coupon coupon){
        return new CouponCacheData(coupon.getCouponName(), coupon.getProduct().getName(),
                coupon.getDiscountPercents());
    }

    public static Single<Coupon> convertFromCouponCacheDataToCoupon(CouponCacheData couponCacheData, ILocalDataRepository localDataRepository){
        String productName = couponCacheData.getProductName();
        Single<List<ProductCacheData>> productCacheDataListObservable = localDataRepository.getCacheProductUnderName(productName);
        return productCacheDataListObservable
                .subscribeOn(Schedulers.computation())
                .map(productCacheDataList -> {
                    return productCacheDataList.get(0);
                })
                .map(ConverterBetweenProductAndProductCacheData::convertFromProductCacheDataToProduct)
                .map(product -> {
                    return new Coupon(couponCacheData.getCouponName(), product, couponCacheData.getDiscountPercents());
                });
    }


}
