package ru.graphorismo.regularburgershop.data.local.room.cart;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ProductCartData.class}, version = 2)
public abstract class ProductCartRoomDatabase extends RoomDatabase {
    public abstract ProductCartDao productCartDao();
}
