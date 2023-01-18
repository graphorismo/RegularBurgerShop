package ru.graphorismo.regularburgershop.data.local;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import ru.graphorismo.regularburgershop.data.Coupon;
import ru.graphorismo.regularburgershop.data.Product;
import ru.graphorismo.regularburgershop.data.local.room.cache.CacheRoomDatabase;
import ru.graphorismo.regularburgershop.data.local.room.cache.coupon.ConverterBetweenCouponAndCacheCouponData;
import ru.graphorismo.regularburgershop.data.local.room.cache.coupon.CacheCouponData;
import ru.graphorismo.regularburgershop.data.local.room.cache.product.CacheProductData;
import ru.graphorismo.regularburgershop.data.local.room.cart.CartRoomDatabase;
import ru.graphorismo.regularburgershop.data.local.room.cart.coupon.CartCouponData;
import ru.graphorismo.regularburgershop.data.local.room.cart.coupon.ConverterBetweenCouponAndCartCouponData;
import ru.graphorismo.regularburgershop.data.local.room.cart.product.CartProductData;
import ru.graphorismo.regularburgershop.data.local.room.cart.product.ConverterBetweenProductAndCartProductData;
import ru.graphorismo.regularburgershop.data.local.room.cache.product.ConverterBetweenProductAndCacheProductData;

public class LocalDataRepository implements ILocalDataRepository {
    private final CartRoomDatabase cartRoomDatabase;
    private final CacheRoomDatabase cacheRoomDatabase;

    @Inject
    public LocalDataRepository(
            CartRoomDatabase cartRoomDatabase,
            CacheRoomDatabase cacheRoomDatabase
    ) {
        this.cartRoomDatabase = cartRoomDatabase;
        this.cacheRoomDatabase = cacheRoomDatabase;
    }

    @Override
    public void saveProductIntoCart(Product product) {
        cartRoomDatabase.getCartProductDao()
                .insertCartProduct(ConverterBetweenProductAndCartProductData
                        .convertProductToCartProductData(product));
    }

    @Override
    public void clearSavedCartProducts() {
        cartRoomDatabase.getCartProductDao().deleteAllCartProducts();
    }

    @Override
    public Observable<List<CartProductData>> getSavedCartProducts() {
        return cartRoomDatabase.getCartProductDao().getAllCartProducts();
    }

    @Override
    public void saveProductIntoCache(Product product) {
        cacheRoomDatabase.getCacheProductDao()
                .insertCacheProduct( ConverterBetweenProductAndCacheProductData
                        .convertFromProductToCacheProductData(product) );
    }

    @Override
    public void clearSavedCacheProducts() {
        cacheRoomDatabase.getCacheProductDao().deleteAllCacheProducts();
    }

    @Override
    public Single<List<CacheProductData>> getCacheProducts() {
        return cacheRoomDatabase.getCacheProductDao().getAllCacheProducts();
    }

    @Override
    public Single<List<CacheProductData>> getCacheProductUnderName(String productName) {
        return cacheRoomDatabase.getCacheProductDao().getCacheProductsUnderName(productName);
    }

    @Override
    public Single<List<CacheProductData>> getCacheProductUnderTitle(String title) {
        return cacheRoomDatabase.getCacheProductDao().getCacheProductsUnderTitle(title);
    }

    @Override
    public void saveCouponIntoCache(Coupon coupon) {
        cacheRoomDatabase.getCacheCouponDao()
                .insertCacheCoupon(ConverterBetweenCouponAndCacheCouponData
                        .convertFromCouponToCacheCouponData(coupon));
    }

    @Override
    public void clearSavedCacheCoupons() {
        cacheRoomDatabase.getCacheCouponDao().deleteAllCacheCoupons();
    }

    @Override
    public Single<List<CacheCouponData>> getCacheCoupons() {
        return cacheRoomDatabase.getCacheCouponDao().getAllCacheCoupons();
    }

    @Override
    public void saveCouponIntoCart(Coupon coupon) {
        cartRoomDatabase.getCartCouponDao()
                .insertCartCoupon(ConverterBetweenCouponAndCartCouponData
                        .convertFromCouponToCartCouponData(coupon));
    }

    @Override
    public void clearSavedCartCoupons() {
        cartRoomDatabase.getCartCouponDao().deleteAllCartCoupons();
    }

    @Override
    public Single<List<CartCouponData>> getCartCoupons() {
        return cartRoomDatabase.getCartCouponDao().getAllCartCoupons();
    }
}
