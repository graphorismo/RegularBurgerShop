package ru.graphorismo.regularburgershop.ui.cart;

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
import ru.graphorismo.regularburgershop.data.local.ILocalDataRepository;
import ru.graphorismo.regularburgershop.data.local.room.cart.product.CartProductData;

@HiltViewModel
public class CartViewModel extends ViewModel {

    private static final String TAG = "CartViewModel";
    private final BehaviorSubject<List<CartProductData>> productsBehaviorSubject =
            BehaviorSubject.create();
    private final ILocalDataRepository localDataRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public CartViewModel(ILocalDataRepository localDataRepository) {
        this.localDataRepository = localDataRepository;
        EventBus.getDefault().register(this);
        loadProducts();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        EventBus.getDefault().unregister(this);
        disposables.clear();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reactOnClearCartEvent(CartUiEvent.ClearCart event){
        clearCart();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reactOnBuyCartEvent(CartUiEvent.BuyCart event){
        clearCart();
    }

    private void clearCart(){
        disposables.add(
                Observable.fromAction(localDataRepository::clearSavedCartProducts)
                        .subscribeOn(Schedulers.io())
                        .subscribe()
        );
        loadProducts();
    }

    private void loadProducts(){
        disposables.add(
                localDataRepository.getSavedCartProducts()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(productsBehaviorSubject::onNext)
        );
    }

    public BehaviorSubject<List<CartProductData>> getProductsBehaviorSubject() {
        return productsBehaviorSubject;
    }
}