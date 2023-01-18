package ru.graphorismo.regularburgershop.data.local.room.cart.coupon;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface CartCouponDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCartCoupon(CartCouponData couponCacheData);

    @Query("DELETE FROM CartCouponData")
    void deleteAllCartCoupons();

    @Query("SELECT * FROM CartCouponData")
    Single<List<CartCouponData>> getAllCartCoupons();
}
