package ru.graphorismo.regularburgershop.ui.coupons;

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
import ru.graphorismo.regularburgershop.data.Coupon;
import ru.graphorismo.regularburgershop.data.local.ILocalDataRepository;
import ru.graphorismo.regularburgershop.data.local.room.cache.coupon.ConverterBetweenCouponAndCouponCacheData;
import ru.graphorismo.regularburgershop.data.local.room.cache.product.ConverterBetweenProductAndProductCacheData;
import ru.graphorismo.regularburgershop.data.remote.IRemoteDataRepository;
import ru.graphorismo.regularburgershop.data.remote.retrofit.ConverterBetweenCouponAndCouponResponse;
import ru.graphorismo.regularburgershop.data.remote.retrofit.exceptions.EmptyResponseException;
import ru.graphorismo.regularburgershop.data.remote.retrofit.exceptions.NullNetworkResponseException;
import ru.graphorismo.regularburgershop.data.remote.retrofit.exceptions.UnsuccessfulResponseException;

@HiltViewModel
public class CouponsViewModel extends ViewModel {

    private final MutableLiveData<Coupon> chosenCouponLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Coupon>> couponsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Throwable> exceptionLiveData = new MutableLiveData<>();
    private final IRemoteDataRepository remoteDataRepository;
    private final ILocalDataRepository localDataRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    private static final String TAG = "CouponsViewModel";

    @Inject
    public CouponsViewModel(IRemoteDataRepository remoteDataRepository,
                            ILocalDataRepository localDataRepository) {
        this.remoteDataRepository = remoteDataRepository;
        this.localDataRepository = localDataRepository;
        loadCoupons();
    }

    public void onEvent(CouponsUIEvent event){
        if(event instanceof CouponsUIEvent.CouponChosen){
            chosenCouponLiveData.setValue(((CouponsUIEvent.CouponChosen)event).getCoupon());
        }
    }

    void loadCoupons(){
        disposables.add(
                Observable.fromSingle(localDataRepository.getCacheCoupons())
                        .subscribeOn(Schedulers.io())
                        .flatMap(Observable::fromIterable)
                        .flatMap(couponCacheData ->
                                Observable.fromSingle(ConverterBetweenCouponAndCouponCacheData
                                        .convertFromCouponCacheDataToCoupon(couponCacheData,
                                                localDataRepository)))
                        .toList()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(coupons -> {
                            couponsLiveData.setValue(coupons);
                        })

        );
    }

    public LiveData<Coupon> getChosenCouponLiveData() {
        return chosenCouponLiveData;
    }

    public LiveData<List<Coupon>> getCouponsLiveData() {
        return couponsLiveData;
    }
}