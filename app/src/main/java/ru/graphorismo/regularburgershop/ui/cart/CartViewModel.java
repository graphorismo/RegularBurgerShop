package ru.graphorismo.regularburgershop.ui.cart;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.Subject;
import ru.graphorismo.regularburgershop.data.Product;
import ru.graphorismo.regularburgershop.data.local.ILocalDataRepository;
import ru.graphorismo.regularburgershop.data.local.room.ConverterBetweenProductAndProductCartData;
import ru.graphorismo.regularburgershop.data.local.room.ProductCartData;

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

    }

    private void loadProducts(){
        disposables.add(
                localDataRepository.getSavedProducts()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(productsLiveData::setValue)
        );
    }

    public LiveData<List<ProductCartData>> getProductsLiveData() {
        return productsLiveData;
    }
}