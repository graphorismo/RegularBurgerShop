package ru.graphorismo.regularburgershop.ui.coupons;

import android.util.Log;

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
import ru.graphorismo.regularburgershop.data.local.room.cache.coupon.ConverterBetweenCouponAndCacheCouponData;
import ru.graphorismo.regularburgershop.data.local.room.cart.coupon.ConverterBetweenCouponAndCartCouponData;

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
        loadChosenCouponFromCart();
        EventBus.getDefault().register(this);
        loadCoupons();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
        EventBus.getDefault().unregister(this);
    }

    private void loadChosenCouponFromCart() {
        disposables.add(
                Observable.fromSingle(localDataRepository.getCartCoupons())
                        .subscribeOn(Schedulers.io())
                        .flatMap(Observable::fromIterable)
                        .flatMap(cartCouponData ->
                                Observable.fromSingle(ConverterBetweenCouponAndCartCouponData
                                        .convertFromCartCouponDataToCoupon(cartCouponData,
                                                localDataRepository)))
                        .toList()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(coupons -> {
                            if(coupons.size() > 0) {
                                Log.d(TAG, "loadChosenCouponFromCart: chosenCouponBehaviorSubject.onNext" + coupons.get(0).getCouponName());
                                chosenCouponBehaviorSubject.onNext(coupons.get(0));
                            }
                        })
        );
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reactOnCouponChosenEvent(CouponsUIEvent.CouponChosen event){
        chosenCouponBehaviorSubject
                .onNext(event.getCoupon());
        saveASingleChosenCoupon(event.getCoupon());

    }

    private void saveASingleChosenCoupon(Coupon coupon){
        disposables.add(
                Observable.fromAction(()->{
                    localDataRepository.clearSavedCartCoupons();
                    localDataRepository.saveCouponIntoCart(coupon);
                })
                        .subscribeOn(Schedulers.io())
                        .subscribe()
        );
    }

    void loadCoupons(){
        disposables.add(
                Observable.fromSingle(localDataRepository.getCacheCoupons())
                        .subscribeOn(Schedulers.io())
                        .flatMap(Observable::fromIterable)
                        .flatMap(couponCacheData ->
                                Observable.fromSingle(ConverterBetweenCouponAndCacheCouponData
                                        .convertFromCacheCouponDataToCoupon(couponCacheData,
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