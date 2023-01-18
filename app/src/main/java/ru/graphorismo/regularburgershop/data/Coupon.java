package ru.graphorismo.regularburgershop.data;

import java.util.Objects;

public class Coupon {
    private final String couponName;
    private final Product product;
    private final Integer discountPercents;

    public Coupon() {
        couponName="Dummy coupon";
        product = new Product();
        discountPercents = 0;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coupon coupon = (Coupon) o;
        return couponName.equals(coupon.couponName) && product.equals(coupon.product) && discountPercents.equals(coupon.discountPercents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(couponName, product, discountPercents);
    }
}
