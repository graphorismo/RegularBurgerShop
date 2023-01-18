package ru.graphorismo.regularburgershop.data.local;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import ru.graphorismo.regularburgershop.data.Coupon;
import ru.graphorismo.regularburgershop.data.Product;
import ru.graphorismo.regularburgershop.data.local.room.cache.coupon.CacheCouponData;
import ru.graphorismo.regularburgershop.data.local.room.cache.product.CacheProductData;
import ru.graphorismo.regularburgershop.data.local.room.cart.coupon.CartCouponData;
import ru.graphorismo.regularburgershop.data.local.room.cart.product.CartProductData;

public interface ILocalDataRepository {

    public void saveProductIntoCart(Product product);
    public void clearSavedCartProducts();
    public Observable<List<CartProductData>> getSavedCartProducts();

    public void saveProductIntoCache(Product product);
    public void clearSavedCacheProducts();
    public Single<List<CacheProductData>> getCacheProducts();
    Single<List<CacheProductData>> getCacheProductUnderName(String productName);
    Single<List<CacheProductData>> getCacheProductUnderTitle(String title);

    public void saveCouponIntoCache(Coupon coupon);
    public void clearSavedCacheCoupons();
    public Single<List<CacheCouponData>> getCacheCoupons();

    void saveCouponIntoCart(Coupon coupon);

    void clearSavedCartCoupons();

    Single<List<CartCouponData>> getCartCoupons();
}
