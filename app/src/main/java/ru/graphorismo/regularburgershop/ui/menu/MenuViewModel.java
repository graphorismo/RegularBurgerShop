package ru.graphorismo.regularburgershop.ui.menu;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.Subject;
import ru.graphorismo.regularburgershop.data.Product;
import ru.graphorismo.regularburgershop.data.local.ILocalDataRepository;
import ru.graphorismo.regularburgershop.data.local.room.cache.product.ConverterBetweenProductAndProductCacheData;
import ru.graphorismo.regularburgershop.data.remote.IRemoteDataRepository;
import ru.graphorismo.regularburgershop.data.remote.retrofit.ConverterProductResponseToProduct;
import ru.graphorismo.regularburgershop.data.remote.retrofit.exceptions.EmptyResponseException;
import ru.graphorismo.regularburgershop.data.remote.retrofit.exceptions.NullNetworkResponseException;
import ru.graphorismo.regularburgershop.data.remote.retrofit.exceptions.UnsuccessfulResponseException;


@HiltViewModel
public class MenuViewModel extends ViewModel {

    private static final String TAG = "MenuViewModel";

    private final BehaviorSubject<List<Product>> productsBehaviorSubject = BehaviorSubject.create();

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final IRemoteDataRepository remoteDataRepository;
    private final ILocalDataRepository localDataRepository;

    @Inject
    public MenuViewModel(IRemoteDataRepository remoteDataRepository,
                         ILocalDataRepository localDataRepository) {
        this.remoteDataRepository = remoteDataRepository;
        this.localDataRepository = localDataRepository;
        productsBehaviorSubject.doOnSubscribe((x)->{
            Log.d(TAG, "MenuViewModel: productsBehaviorSubject  subscribed");
        });
        EventBus.getDefault().register(this);
        loadProductsUnderTitleFromCache("combo");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reactOnAddProductToCartEvent(MenuUiEvent.AddProductToCart event){
        addProductToCart(event.getProduct());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reactOnChangeOfShowedProductsTitleEvent(
            MenuUiEvent.ChangeOfShowedProductsTitle event){
        loadProductsUnderTitleFromCache(event.getTitle());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        EventBus.getDefault().unregister(this);
        disposables.clear();
    }

    private void loadProductsUnderTitleFromCache(String title){
        disposables.add(
                Observable.fromSingle(localDataRepository.getCacheProductUnderTitle(title))
                        .subscribeOn(Schedulers.io())
                        .flatMap(Observable::fromIterable)
                        .map(ConverterBetweenProductAndProductCacheData::convertFromProductCacheDataToProduct)
                        .toList()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(productsBehaviorSubject::onNext,
                                throwable -> {
                                    Log.e(TAG,"loadProductsUnderTitleFromCache Error:"+throwable.getMessage());
                        })
        );

    }

    private void addProductToCart(Product product){
        disposables.add(
                Observable.fromAction(()->{localDataRepository.saveProductIntoCart(product);})
                        .subscribeOn(Schedulers.io())
                        .subscribe()
        );
    }

    public BehaviorSubject<List<Product>> getProductsBehaviorSubject() {
        return productsBehaviorSubject;
    }
}