package ru.graphorismo.regularburgershop.data.local;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import ru.graphorismo.regularburgershop.data.Coupon;
import ru.graphorismo.regularburgershop.data.Product;
import ru.graphorismo.regularburgershop.data.local.room.cart.ProductCartData;
import ru.graphorismo.regularburgershop.data.local.room.cache.product.ProductCacheData;

public interface ILocalDataRepository {

    public void saveProductIntoCart(Product product);
    public void clearSavedCartProducts();
    public Observable<List<ProductCartData>> getSavedCartProducts();

    public void saveProductIntoCache(Product product);
    public void clearSavedCacheProducts();
    public Observable<List<ProductCacheData>> getSavedCacheProducts();

    Single<List<ProductCacheData>> getProductCacheUnderName(String productName);

    public void saveCouponIntoCache(Coupon coupon);

    public void clearSavedCacheCoupons();
}
