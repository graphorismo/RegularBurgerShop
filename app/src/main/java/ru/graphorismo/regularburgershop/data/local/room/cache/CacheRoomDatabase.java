package ru.graphorismo.regularburgershop.data.local.room.cache;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.graphorismo.regularburgershop.data.local.room.cache.coupon.CacheCouponDao;
import ru.graphorismo.regularburgershop.data.local.room.cache.coupon.CacheCouponData;
import ru.graphorismo.regularburgershop.data.local.room.cache.product.CacheProductDao;
import ru.graphorismo.regularburgershop.data.local.room.cache.product.CacheProductData;

@Database(entities = {CacheProductData.class, CacheCouponData.class}, version = 4)
public abstract class CacheRoomDatabase extends RoomDatabase {
    public abstract CacheCouponDao getCacheCouponDao();
    public abstract CacheProductDao getCacheProductDao();
}
