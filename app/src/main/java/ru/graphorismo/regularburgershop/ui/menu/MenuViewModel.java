package ru.graphorismo.regularburgershop.ui.menu;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.graphorismo.regularburgershop.data.Product;
import ru.graphorismo.regularburgershop.data.local.ILocalDataRepository;
import ru.graphorismo.regularburgershop.data.remote.IRemoteDataRepository;
import ru.graphorismo.regularburgershop.data.remote.retrofit.ConverterProductResponseToProduct;
import ru.graphorismo.regularburgershop.data.remote.retrofit.exceptions.EmptyResponseException;
import ru.graphorismo.regularburgershop.data.remote.retrofit.exceptions.NullNetworkResponseException;
import ru.graphorismo.regularburgershop.data.remote.retrofit.exceptions.UnsuccessfulResponseException;


@HiltViewModel
public class MenuViewModel extends ViewModel {

    private static final String TAG = "MenuViewModel";

    private final MutableLiveData<List<Product>> productsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Throwable> exceptionLiveData = new MutableLiveData<>();

    private final CompositeDisposable disposables = new CompositeDisposable();

    private final IRemoteDataRepository remoteDataRepository;
    private final ILocalDataRepository localDataRepository;

    @Inject
    public MenuViewModel(IRemoteDataRepository remoteDataRepository,
                         ILocalDataRepository localDataRepository) {
        this.remoteDataRepository = remoteDataRepository;
        this.localDataRepository = localDataRepository;
        loadProducts();
    }

    public void onEvent(MenuUiEvent event){
        if(event instanceof MenuUiEvent.Refresh){
            loadProducts();
        }
        else if (event instanceof MenuUiEvent.AddProductToCart){
            addProductToCart(((MenuUiEvent.AddProductToCart) event).getProduct());
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }

    private void loadProducts(){
        disposables.add(
                remoteDataRepository.getProductsIds()
                        .subscribeOn(Schedulers.io())
                        .map((response)->{
                            if( response == null) throw new NullNetworkResponseException("");
                            if( ! response.isSuccessful()) throw new UnsuccessfulResponseException("");
                            if( response.body() == null ) throw new EmptyResponseException("");
                            return response.body();
                        })
                        .doOnNext(integers -> {localDataRepository.clearSavedCacheProducts();})
                        .flatMap(Observable::fromIterable)
                        .flatMap(remoteDataRepository::getProductUnderId)
                        .map((response)->{
                            if( response == null) throw new NullNetworkResponseException("");
                            if( ! response.isSuccessful()) throw new UnsuccessfulResponseException("");
                            if( response.body() == null ) throw new EmptyResponseException("");
                            return response.body();
                        })
                        .flatMap(Observable::fromIterable)
                        .map(ConverterProductResponseToProduct::convert)
                        .doOnNext(localDataRepository::saveProductIntoCache)
                        .toList()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(productsLiveData::setValue,
                                (throwable)->{
                                    exceptionLiveData.setValue(throwable);
                                    Log.e(TAG, "loadProducts: "+throwable.getMessage() );
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

    public LiveData<List<Product>> getProductsLiveData() {
        return productsLiveData;
    }

    public LiveData<Throwable> getExceptionLiveData() {
        return exceptionLiveData;
    }
}