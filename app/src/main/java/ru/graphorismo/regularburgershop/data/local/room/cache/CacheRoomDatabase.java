package ru.graphorismo.regularburgershop.data.local.room.cache;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.graphorismo.regularburgershop.data.local.room.cache.coupon.CouponCacheDao;
import ru.graphorismo.regularburgershop.data.local.room.cache.coupon.CouponCacheData;
import ru.graphorismo.regularburgershop.data.local.room.cache.product.ProductCacheData;

@Database(entities = {ProductCacheData.class, CouponCacheData.class}, version = 1)
public abstract class CacheRoomDatabase extends RoomDatabase {
    public abstract CacheDao getCacheDao();
}
