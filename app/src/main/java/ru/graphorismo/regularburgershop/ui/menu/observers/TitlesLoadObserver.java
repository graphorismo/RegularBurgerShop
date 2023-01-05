package ru.graphorismo.regularburgershop.ui.menu.observers;

import android.util.Log;

import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import retrofit2.Response;
import ru.graphorismo.regularburgershop.data.remote.retrofit.exceptions.EmptyResponseException;
import ru.graphorismo.regularburgershop.data.remote.retrofit.exceptions.NullNetworkResponseException;
import ru.graphorismo.regularburgershop.data.remote.retrofit.exceptions.UnsuccessfulResponseException;

public class TitlesLoadObserver implements Observer<Response<List<String>>> {

    private CompositeDisposable disposables = new CompositeDisposable();
    private static final String TAG = "TitlesLoadObserver";
    private List<String> titles = Collections.emptyList();
    private final BehaviorSubject<List<String>> stateBehaviorSubject;

    public TitlesLoadObserver(BehaviorSubject<List<String>> stateBehaviorSubject) {
        this.stateBehaviorSubject = stateBehaviorSubject;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        Log.d(TAG, "onSubscribe: Start loading titles");
        disposables.add(d);
    }

    @Override
    public void onNext(@NonNull Response<List<String>> listResponse) {
        if(listResponse == null) throw new NullNetworkResponseException("");
        if( ! listResponse.isSuccessful()) throw new UnsuccessfulResponseException("");
        if( listResponse.body() == null ) throw new EmptyResponseException("");
        titles = listResponse.body();
        disposables.clear();
        stateBehaviorSubject.onNext(titles);
        Log.d(TAG, "onComplete: End loading titles.");
    }

    @Override
    public void onError(@NonNull Throwable e) {
        Log.e(TAG, "onError: " + e.getMessage());
    }

    @Override
    public void onComplete() {

    }
}
