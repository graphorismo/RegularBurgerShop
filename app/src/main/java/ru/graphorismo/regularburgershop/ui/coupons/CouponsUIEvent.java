package ru.graphorismo.regularburgershop.ui.coupons;

import ru.graphorismo.regularburgershop.data.Coupon;

public interface CouponsUIEvent {

    class CouponChosen implements CouponsUIEvent{
        private final Coupon coupon;

        public CouponChosen(Coupon coupon) {
            this.coupon = coupon;
        }

        public Coupon getCoupon() {
            return coupon;
        }
    }


}
