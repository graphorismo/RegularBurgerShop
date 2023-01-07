package ru.graphorismo.regularburgershop.ui.menu;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.ReplaySubject;
import io.reactivex.rxjava3.subjects.Subject;
import ru.graphorismo.regularburgershop.data.Product;
import ru.graphorismo.regularburgershop.data.remote.IRemoteDataRepository;
import ru.graphorismo.regularburgershop.data.remote.retrofit.exceptions.EmptyResponseException;
import ru.graphorismo.regularburgershop.data.remote.retrofit.exceptions.NullNetworkResponseException;
import ru.graphorismo.regularburgershop.data.remote.retrofit.exceptions.UnsuccessfulResponseException;


@HiltViewModel
public class MenuViewModel extends ViewModel {

    private static final String TAG = "MenuViewModel";

    private final Subject<Product> productsReplaySubject = ReplaySubject.create();

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
                    return new Product(productResponse.getName(),
                            productResponse.getPrice());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productsReplaySubject);
    }

    public Subject<Product> getProductsReplaySubject() {
        return productsReplaySubject;
    }
}