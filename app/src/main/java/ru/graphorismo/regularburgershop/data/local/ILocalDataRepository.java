package ru.graphorismo.regularburgershop.data.local;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import ru.graphorismo.regularburgershop.data.Product;
import ru.graphorismo.regularburgershop.data.local.room.ProductCartData;

public interface ILocalDataRepository {

    public Completable saveProduct(Product product);
    public Completable clearSavedProducts();
    public Observable<List<ProductCartData>> getSavedProducts();

}
