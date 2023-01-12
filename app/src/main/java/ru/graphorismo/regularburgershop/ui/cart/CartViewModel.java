package ru.graphorismo.regularburgershop.ui.cart;

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
import ru.graphorismo.regularburgershop.data.local.ILocalDataRepository;
import ru.graphorismo.regularburgershop.data.local.room.cart.ProductCartData;

@HiltViewModel
public class CartViewModel extends ViewModel {

    private static final String TAG = "CartViewModel";

    private final MutableLiveData<List<ProductCartData>> productsLiveData = new MutableLiveData<>();

    private final ILocalDataRepository localDataRepository;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public CartViewModel(ILocalDataRepository localDataRepository) {
        this.localDataRepository = localDataRepository;
        loadProducts();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }

    public void onEvent(CartUiEvent event){
        if(event instanceof CartUiEvent.ClearCart){
            clearCart();
        }else if(event instanceof CartUiEvent.BuyCart){
            clearCart();
        }
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
                        .subscribe(productsLiveData::setValue)
        );
    }

    public LiveData<List<ProductCartData>> getProductsLiveData() {
        return productsLiveData;
    }
}