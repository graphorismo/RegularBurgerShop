package ru.graphorismo.regularburgershop.ui.menu.observers;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import ru.graphorismo.regularburgershop.ui.menu.MenuRecyclerAdapter;

public class TitlesUiStateObserver  implements Observer<List<String>> {

    private final Context context;
    private static final String TAG = "TitlesUiStateObserver";
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final Map<String, LinearLayout> layoutsForTitles = new HashMap<>();
    private BehaviorSubject<Boolean> loadCompleteStateBehaviorSubject = BehaviorSubject.create();


    public TitlesUiStateObserver(Context context) {
        this.context = context;
    }

    public BehaviorSubject<Boolean> getLoadCompleteStateBehaviorSubject() {
        return loadCompleteStateBehaviorSubject;
    }

    public Map<String, LinearLayout> getLayoutsForTitles() {
        return layoutsForTitles;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        Log.d(TAG, "onSubscribe: Start generating layout");
        disposables.add(d);
    }

    @Override
    public void onNext(@NonNull List<String> titles) {
        for (String title : titles){
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            TextView textView = new TextView(context);
            textView.setText(title);
            layout.addView(textView);
            layoutsForTitles.put(title,layout);
        }
        loadCompleteStateBehaviorSubject.onNext(true);
        Log.d(TAG, "onNext: End loading ui for titles");
        disposables.clear();
    }

    @Override
    public void onError(@NonNull Throwable e) {
        Log.e(TAG, "onError: " + e.getMessage() );
    }

    @Override
    public void onComplete() {
        
    }
}
