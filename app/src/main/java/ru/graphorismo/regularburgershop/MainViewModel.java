package ru.graphorismo.regularburgershop;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.graphorismo.regularburgershop.data.Coupon;
import ru.graphorismo.regularburgershop.data.Product;
import ru.graphorismo.regularburgershop.data.local.ILocalDataRepository;
import ru.graphorismo.regularburgershop.data.remote.IRemoteDataRepository;
import ru.graphorismo.regularburgershop.data.remote.retrofit.ConverterBetweenCouponAndCouponResponse;
import ru.graphorismo.regularburgershop.data.remote.retrofit.ConverterProductResponseToProduct;
import ru.graphorismo.regularburgershop.data.remote.retrofit.exceptions.EmptyResponseException;
import ru.graphorismo.regularburgershop.data.remote.retrofit.exceptions.NullNetworkResponseException;
import ru.graphorismo.regularburgershop.data.remote.retrofit.exceptions.UnsuccessfulResponseException;
import ru.graphorismo.regularburgershop.ui.MainUiEvent;

@HiltViewModel
public class MainViewModel extends ViewModel {

    private IRemoteDataRepository remoteDataRepository;
    private ILocalDataRepository localDataRepository;
    private CompositeDisposable disposables = new CompositeDisposable();
    private MutableLiveData<Throwable> exceptionLiveData = new MutableLiveData<>();

    private static final String TAG = "MainViewModel";

    @Inject
    public MainViewModel(IRemoteDataRepository remoteDataRepository,
                         ILocalDataRepository localDataRepository) {
        this.remoteDataRepository = remoteDataRepository;
        this.localDataRepository = localDataRepository;
        loadProductsAndCouponsIntoCache();
    }

    private void loadProductsAndCouponsIntoCache(){
        disposables.add(
                prepareToLoadProductsIntoCache()
                        .doOnComplete(()->{
                            disposables.add(
                                    prepareToLoadCouponsIntoCache()
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe((coupon)->{},
                                                    exceptionLiveData::setValue)
                            );
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((product)->{},
                                exceptionLiveData::setValue)
        );
    }

    private Observable<Product> prepareToLoadProductsIntoCache() {
        return remoteDataRepository.getProductsIds()
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
                .doOnNext(product -> {
                    localDataRepository.saveProductIntoCache(product);
                    Log.d(TAG, "loadProductsIntoCache: Caching product "+product.getName());
                });
    }

    private Observable<Coupon> prepareToLoadCouponsIntoCache() {
        return remoteDataRepository.getCouponsIds()
                .subscribeOn(Schedulers.io())
                .map((response)->{
                    if( response == null) throw new NullNetworkResponseException("");
                    if( ! response.isSuccessful()) throw new UnsuccessfulResponseException("");
                    if( response.body() == null ) throw new EmptyResponseException("");
                    return response.body();
                })
                .doOnNext(integers -> {localDataRepository.clearSavedCacheCoupons();})
                .flatMap(Observable::fromIterable)
                .flatMap(remoteDataRepository::getCouponUnderId)
                .map((response)->{
                    if( response == null) throw new NullNetworkResponseException("");
                    if( ! response.isSuccessful()) throw new UnsuccessfulResponseException("");
                    if( response.body() == null ) throw new EmptyResponseException("");
                    return response.body();
                })
                .flatMap(Observable::fromIterable)
                .flatMap(couponResponse -> Observable.fromSingle(
                        ConverterBetweenCouponAndCouponResponse
                                .convertCouponResponseToCoupon(couponResponse,
                                        localDataRepository)))
                .doOnNext(coupon -> {
                    localDataRepository.saveCouponIntoCache(coupon);
                    Log.d(TAG, "loadCouponsIntoCache: Caching coupon "+coupon.getCouponName());
                });
    }

    public void onEvent(MainUiEvent event){
        if (event instanceof MainUiEvent.RefreshCache){
            loadProductsAndCouponsIntoCache();
        }
    }

    public LiveData<Throwable> getExceptionLiveData() {
        return exceptionLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}

