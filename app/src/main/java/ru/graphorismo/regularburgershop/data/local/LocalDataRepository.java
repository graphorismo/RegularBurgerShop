package ru.graphorismo.regularburgershop.data.local;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import ru.graphorismo.regularburgershop.data.Product;
import ru.graphorismo.regularburgershop.data.local.room.ConverterBetweenProductAndProductCartData;
import ru.graphorismo.regularburgershop.data.local.room.ProductCartData;
import ru.graphorismo.regularburgershop.data.local.room.ProductCartRoomDatabase;

public class LocalDataRepository implements ILocalDataRepository {
    private final ProductCartRoomDatabase productCartRoomDatabase;

    @Inject
    public LocalDataRepository(ProductCartRoomDatabase productCartRoomDatabase) {
        this.productCartRoomDatabase = productCartRoomDatabase;
    }

    @Override
    public Completable saveProduct(Product product) {
        return productCartRoomDatabase.productCartDao().insert(ConverterBetweenProductAndProductCartData.convertProductToProductCartData(product));
    }

    @Override
    public Completable clearSavedProducts() {
        return productCartRoomDatabase.productCartDao().deleteAll();
    }

    @Override
    public Observable<List<ProductCartData>> getSavedProducts() {
        return productCartRoomDatabase.productCartDao().getAll();
    }
}
