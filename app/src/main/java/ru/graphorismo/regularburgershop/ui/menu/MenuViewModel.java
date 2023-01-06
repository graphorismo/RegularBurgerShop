package ru.graphorismo.regularburgershop.ui.menu;

import androidx.lifecycle.ViewModel;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import ru.graphorismo.regularburgershop.data.Product;
import ru.graphorismo.regularburgershop.data.remote.IRemoteDataRepository;
import ru.graphorismo.regularburgershop.data.remote.retrofit.exceptions.EmptyResponseException;
import ru.graphorismo.regularburgershop.data.remote.retrofit.exceptions.NullNetworkResponseException;
import ru.graphorismo.regularburgershop.data.remote.retrofit.exceptions.UnsuccessfulResponseException;


@HiltViewModel
public class MenuViewModel extends ViewModel {

    private static final String TAG = "MenuViewModel";

    private final BehaviorSubject<List<Integer>> idsBehaviorSubject = BehaviorSubject.create();
    private final BehaviorSubject<List<Product>> productsBehaviorSubject = BehaviorSubject.create();

    private final CompositeDisposable disposables = new CompositeDisposable();
    private final IRemoteDataRepository remoteDataRepository;

    @Inject
    public MenuViewModel(IRemoteDataRepository remoteDataRepository) {
        this.remoteDataRepository = remoteDataRepository;
    }

    Observable<Product> onEvent(MenuUiEvent event){
            return loadProducts();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }

    private Observable<Product> loadProducts(){
        return remoteDataRepository.getIds()
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
                    return new Product(productResponse.getName(),
                            productResponse.getPrice());
                });
    }

}