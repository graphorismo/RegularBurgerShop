package ru.graphorismo.regularburgershop.data.local.room.cart;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

@Dao
public interface ProductCartDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ProductCartData productCartData);

    @Query("DELETE FROM ProductCartData")
    void deleteAll();

    @Query("SELECT * FROM ProductCartData")
    Observable<List<ProductCartData>> getAll();
}
