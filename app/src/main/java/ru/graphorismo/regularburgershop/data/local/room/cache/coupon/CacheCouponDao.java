package ru.graphorismo.regularburgershop.data.local.room.cache.coupon;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface CacheCouponDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCacheCoupon(CacheCouponData couponCacheData);

    @Query("DELETE FROM CacheCouponData")
    void deleteAllCacheCoupons();

    @Query("SELECT * FROM CacheCouponData")
    Single<List<CacheCouponData>> getAllCacheCoupons();

    @Query("SELECT * FROM CacheCouponData WHERE couponName = :nameOfCoupon")
    Observable<List<CacheCouponData>> getCacheCouponsUnderName(String nameOfCoupon);


}
