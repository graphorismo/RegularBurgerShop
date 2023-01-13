package ru.graphorismo.regularburgershop.data.local.room.cache;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import ru.graphorismo.regularburgershop.data.local.room.cache.coupon.CouponCacheData;
import ru.graphorismo.regularburgershop.data.local.room.cache.product.ProductCacheData;

@Dao
public interface CacheDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertProduct(ProductCacheData productCacheData);

    @Query("DELETE FROM ProductCacheData")
    void deleteAllProducts();

    @Query("SELECT * FROM ProductCacheData")
    Single<List<ProductCacheData>> getAllProducts();

    @Query("SELECT * FROM ProductCacheData WHERE name = :productName")
    Single<List<ProductCacheData>> getProductsUnderName(String productName);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCoupon(CouponCacheData couponCacheData);

    @Query("DELETE FROM CouponCacheData")
    void deleteAllCoupons();

    @Query("SELECT * FROM CouponCacheData")
    Single<List<CouponCacheData>> getAllCoupons();

    @Query("SELECT * FROM CouponCacheData WHERE couponName = :nameOfCoupon")
    Observable<List<CouponCacheData>> getCouponsUnderName(String nameOfCoupon);


}
