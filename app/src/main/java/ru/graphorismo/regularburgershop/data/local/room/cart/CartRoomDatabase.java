package ru.graphorismo.regularburgershop.data.local.room.cart;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.graphorismo.regularburgershop.data.local.room.cart.coupon.CartCouponDao;
import ru.graphorismo.regularburgershop.data.local.room.cart.coupon.CartCouponData;
import ru.graphorismo.regularburgershop.data.local.room.cart.product.CartProductDao;
import ru.graphorismo.regularburgershop.data.local.room.cart.product.CartProductData;

@Database(entities = {CartProductData.class, CartCouponData.class}, version = 5)
public abstract class CartRoomDatabase extends RoomDatabase {
    public abstract CartProductDao getCartProductDao();
    public abstract CartCouponDao getCartCouponDao();
}
