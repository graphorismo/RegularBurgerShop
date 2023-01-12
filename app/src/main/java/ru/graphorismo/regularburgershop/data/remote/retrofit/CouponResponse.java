package ru.graphorismo.regularburgershop.data.remote.retrofit;

public class CouponResponse {
    private final String couponName;
    private final String productName;
    private final Integer discountPercents;

    public CouponResponse(String couponName, String productName, Integer discountPercents) {
        this.couponName = couponName;
        this.productName = productName;
        this.discountPercents = discountPercents;
    }

    public String getCouponName() {
        return couponName;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getDiscountPercents() {
        return discountPercents;
    }
}
