package ru.graphorismo.regularburgershop.ui.menu;

import androidx.lifecycle.ViewModel;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import retrofit2.Response;
import ru.graphorismo.regularburgershop.data.Product;
import ru.graphorismo.regularburgershop.data.remote.IRemoteDataRepository;
import ru.graphorismo.regularburgershop.ui.menu.observers.TitlesLoadObserver;

@HiltViewModel
public class MenuViewModel extends ViewModel {

    private static final String TAG = "MenuViewModel";

    BehaviorSubject<List<String>> titlesStateBehaviorSubject = BehaviorSubject.create();
    BehaviorSubject<Map<String,List<Product>>> productsStateBehaviorSubject = BehaviorSubject.create();
    CompositeDisposable disposables = new CompositeDisposable();

    private final IRemoteDataRepository remoteDataRepository;

    @Inject
    public MenuViewModel(IRemoteDataRepository remoteDataRepository) {
        this.remoteDataRepository = remoteDataRepository;
    }

    void onEvent(MenuUiEvent event){
        if (event instanceof MenuUiEvent.Load){
            loadTitles();
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }

    private void loadTitles(){
        List<String> titles = Collections.emptyList();
        Observable<Response<List<String>>> titlesObservable = remoteDataRepository.getTitles();
        titlesObservable.subscribeOn(Schedulers.io());
        titlesObservable.observeOn(AndroidSchedulers.mainThread());
        TitlesLoadObserver titlesObserver = new TitlesLoadObserver(titlesStateBehaviorSubject);
        titlesObservable.subscribeWith(titlesObserver);
    }

}