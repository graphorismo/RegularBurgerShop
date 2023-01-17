package ru.graphorismo.regularburgershop.ui.coupons;

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

    private final BehaviorSubject<Coupon> chosenCouponBehaviorSubject =
            BehaviorSubject.create();
    private final BehaviorSubject<List<Coupon>> showedCouponsBehaviorSubject =
            BehaviorSubject.create();

    private final ILocalDataRepository localDataRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    private static final String TAG = "CouponsViewModel";

    @Inject
    public CouponsViewModel(ILocalDataRepository localDataRepository) {
        this.localDataRepository = localDataRepository;
        EventBus.getDefault().register(this);
        loadCoupons();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reactOnCouponChosenEvent(CouponsUIEvent.CouponChosen event){
        chosenCouponBehaviorSubject
                .onNext(event.getCoupon());
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
                        .subscribe(showedCouponsBehaviorSubject::onNext)

        );
    }

    public BehaviorSubject<Coupon> getChosenCouponBehaviorSubject() {
        return chosenCouponBehaviorSubject;
    }

    public BehaviorSubject<List<Coupon>> getShowedCouponsBehaviorSubject() {
        return showedCouponsBehaviorSubject;
    }
}