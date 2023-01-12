package ru.graphorismo.regularburgershop.data.local;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import ru.graphorismo.regularburgershop.data.Coupon;
import ru.graphorismo.regularburgershop.data.Product;
import ru.graphorismo.regularburgershop.data.local.room.cache.CacheRoomDatabase;
import ru.graphorismo.regularburgershop.data.local.room.cache.coupon.ConverterBetweenCouponAndCouponCacheData;
import ru.graphorismo.regularburgershop.data.local.room.cart.ConverterBetweenProductAndProductCartData;
import ru.graphorismo.regularburgershop.data.local.room.cart.ProductCartData;
import ru.graphorismo.regularburgershop.data.local.room.cart.ProductCartRoomDatabase;
import ru.graphorismo.regularburgershop.data.local.room.cache.product.ConverterBetweenProductAndProductCacheData;
import ru.graphorismo.regularburgershop.data.local.room.cache.CacheDao;
import ru.graphorismo.regularburgershop.data.local.room.cache.product.ProductCacheData;

public class LocalDataRepository implements ILocalDataRepository {
    private final ProductCartRoomDatabase productCartRoomDatabase;
    private final CacheRoomDatabase cacheRoomDatabase;

    @Inject
    public LocalDataRepository(
            ProductCartRoomDatabase productCartRoomDatabase,
            CacheRoomDatabase cacheRoomDatabase
    ) {
        this.productCartRoomDatabase = productCartRoomDatabase;
        this.cacheRoomDatabase = cacheRoomDatabase;
    }

    @Override
    public void saveProductIntoCart(Product product) {
        productCartRoomDatabase.productCartDao().insert(ConverterBetweenProductAndProductCartData.convertProductToProductCartData(product));
    }

    @Override
    public void clearSavedCartProducts() {
        productCartRoomDatabase.productCartDao().deleteAll();
    }

    @Override
    public Observable<List<ProductCartData>> getSavedCartProducts() {
        return productCartRoomDatabase.productCartDao().getAll();
    }

    @Override
    public void saveProductIntoCache(Product product) {
        CacheDao dao = cacheRoomDatabase.getCacheDao();
        dao.insertProduct(ConverterBetweenProductAndProductCacheData.convertFromProductToProductCacheData(product));
    }

    @Override
    public void clearSavedCacheProducts() {

    }

    @Override
    public Observable<List<ProductCacheData>> getSavedCacheProducts() {
        return null;
    }

    @Override
    public Single<List<ProductCacheData>> getProductCacheUnderName(String productName) {
        return cacheRoomDatabase.getCacheDao().getProductsUnderName(productName);
    }

    @Override
    public void saveCouponIntoCache(Coupon coupon) {
        cacheRoomDatabase.getCacheDao()
                .insertCoupon(ConverterBetweenCouponAndCouponCacheData
                        .convertFromCouponToCouponCacheData(coupon));
    }

    @Override
    public void clearSavedCacheCoupons() {
        cacheRoomDatabase.getCacheDao().deleteAllCoupons();
    }
}
