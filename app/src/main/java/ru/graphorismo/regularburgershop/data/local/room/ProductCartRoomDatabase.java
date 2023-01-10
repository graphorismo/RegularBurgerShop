package ru.graphorismo.regularburgershop.data.local.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ProductCartData.class}, version = 1)
public abstract class ProductCartRoomDatabase extends RoomDatabase {
    public abstract ProductCartDao productCartDao();
}
