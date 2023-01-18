package ru.graphorismo.regularburgershop.data.local.room.cart.product;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

@Dao
public interface CartProductDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCartProduct(CartProductData productCartData);

    @Query("DELETE FROM CartProductData")
    void deleteAllCartProducts();

    @Query("SELECT * FROM CartProductData")
    Observable<List<CartProductData>> getAllCartProducts();
}
