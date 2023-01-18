package ru.graphorismo.regularburgershop.data.local.room.cache.product;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface CacheProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCacheProduct(CacheProductData cacheProductData);

    @Query("DELETE FROM CacheProductData")
    void deleteAllCacheProducts();

    @Query("SELECT * FROM CacheProductData")
    Single<List<CacheProductData>> getAllCacheProducts();

    @Query("SELECT * FROM CacheProductData WHERE name = :productName")
    Single<List<CacheProductData>> getCacheProductsUnderName(String productName);

    @Query("SELECT * FROM CacheProductData WHERE title = :productTitle")
    Single<List<CacheProductData>> getCacheProductsUnderTitle(String productTitle);


}
