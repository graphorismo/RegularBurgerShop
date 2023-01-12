package ru.graphorismo.regularburgershop.data;

public class Coupon {
    private final String couponName;
    private final Product product;
    private final Integer discountPercents;

    public Coupon(String couponName, Product product, Integer discountPercents) {
        this.couponName = couponName;
        this.product = product;
        this.discountPercents = discountPercents;
    }

    public String getCouponName() {
        return couponName;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getDiscountPercents() {
        return discountPercents;
    }
}
