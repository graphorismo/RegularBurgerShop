package ru.graphorismo.regularburgershop.ui.menu;

import android.util.Log;

import androidx.lifecycle.ViewModel;

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
import ru.graphorismo.regularburgershop.data.remote.IRemoteDataRepository;
import ru.graphorismo.regularburgershop.data.remote.retrofit.ConverterProductResponseToProduct;
import ru.graphorismo.regularburgershop.data.remote.retrofit.exceptions.EmptyResponseException;
import ru.graphorismo.regularburgershop.data.remote.retrofit.exceptions.NullNetworkResponseException;
import ru.graphorismo.regularburgershop.data.remote.retrofit.exceptions.UnsuccessfulResponseException;


@HiltViewModel
public class MenuViewModel extends ViewModel {

    private static final String TAG = "MenuViewModel";

    private final Subject<List<Product>> productsBehaviorSubject = BehaviorSubject.create();

    private final CompositeDisposable disposables = new CompositeDisposable();
    private final IRemoteDataRepository remoteDataRepository;

    @Inject
    public MenuViewModel(IRemoteDataRepository remoteDataRepository) {
        this.remoteDataRepository = remoteDataRepository;
        loadProducts();
    }

    public void onEvent(MenuUiEvent event){
        if(event instanceof MenuUiEvent.Refresh){
            loadProducts();
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }

    private void loadProducts(){
        disposables.add(
                remoteDataRepository.getIds()
                        .subscribeOn(Schedulers.io())
                        .map((response)->{
                            if( response == null) throw new NullNetworkResponseException("");
                            if( ! response.isSuccessful()) throw new UnsuccessfulResponseException("");
                            if( response.body() == null ) throw new EmptyResponseException("");
                            return response.body();
                        })
                        .flatMap(Observable::fromIterable)
                        .flatMap(remoteDataRepository::getProductUnderId)
                        .map((response)->{
                            if( response == null) throw new NullNetworkResponseException("");
                            if( ! response.isSuccessful()) throw new UnsuccessfulResponseException("");
                            if( response.body() == null ) throw new EmptyResponseException("");
                            return response.body();
                        })
                        .flatMap(Observable::fromIterable)
                        .map((productResponse) -> {
                            ConverterProductResponseToProduct converterProductResponseToProduct
                                    = new ConverterProductResponseToProduct();
                            return converterProductResponseToProduct
                                    .convertProductResponseToProduct(productResponse);
                        })
                        .toList()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(productsBehaviorSubject::onNext,
                                (throwable)->{
                                    Log.e(TAG, "loadProducts: "+throwable.getMessage() );
                                })
        );

    }

    public Subject<List<Product>> getProductsBehaviorSubject() {
        return productsBehaviorSubject;
    }
}