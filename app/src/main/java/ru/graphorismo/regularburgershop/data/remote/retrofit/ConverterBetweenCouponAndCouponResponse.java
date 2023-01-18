package ru.graphorismo.regularburgershop.data.remote.retrofit;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.graphorismo.regularburgershop.data.Coupon;
import ru.graphorismo.regularburgershop.data.local.ILocalDataRepository;
import ru.graphorismo.regularburgershop.data.local.room.cache.product.ConverterBetweenProductAndCacheProductData;
import ru.graphorismo.regularburgershop.data.local.room.cache.product.CacheProductData;

public class ConverterBetweenCouponAndCouponResponse {

    public static Single<Coupon> convertCouponResponseToCoupon(CouponResponse couponResponse, ILocalDataRepository localDataRepository){
        String productName = couponResponse.getProductName();
        Single<List<CacheProductData>> productCacheDataListObservable =
                localDataRepository.getCacheProductUnderName(productName);
        return productCacheDataListObservable
                .subscribeOn(Schedulers.computation())
                .map((productCacheDataList)->{
                    return productCacheDataList.get(0);
                })
                .map(ConverterBetweenProductAndCacheProductData::convertFromCacheProductDataToProduct)
                .map(product -> {
                    return new Coupon(couponResponse.getCouponName(), product, couponResponse.getDiscountPercents());
                });
    }
}
