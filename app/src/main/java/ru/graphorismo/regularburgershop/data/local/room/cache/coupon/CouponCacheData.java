package ru.graphorismo.regularburgershop.data.local.room.cache.coupon;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity
public class CouponCacheData {
    @PrimaryKey
    @NonNull
    private UUID uuid;
    @NonNull
    private  String couponName;
    @NonNull
    private  String productName;
    @NonNull
    private  Integer discountPercents;

    public CouponCacheData(@NonNull String couponName, @NonNull String productName, @NonNull Integer discountPercents) {
        this.uuid = UUID.randomUUID();
        this.couponName = couponName;
        this.productName = productName;
        this.discountPercents = discountPercents;
    }

    @NonNull
    public UUID getUuid() {
        return uuid;
    }

    @NonNull
    public String getCouponName() {
        return couponName;
    }

    @NonNull
    public String getProductName() {
        return productName;
    }

    @NonNull
    public Integer getDiscountPercents() {
        return discountPercents;
    }

    public void setUuid(@NonNull UUID uuid) {
        this.uuid = uuid;
    }

    public void setCouponName(@NonNull String couponName) {
        this.couponName = couponName;
    }

    public void setProductName(@NonNull String productName) {
        this.productName = productName;
    }

    public void setDiscountPercents(@NonNull Integer discountPercents) {
        this.discountPercents = discountPercents;
    }
}
