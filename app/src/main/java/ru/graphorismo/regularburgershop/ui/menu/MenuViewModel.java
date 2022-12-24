package ru.graphorismo.regularburgershop.ui.menu;
;
import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import ru.graphorismo.regularburgershop.domain.Menu;

public class MenuViewModel extends ViewModel {

    BehaviorSubject<Menu> menuUiState =
            BehaviorSubject.create();

    void onEvent(MenuUiEvent event){
        if (event instanceof MenuUiEvent.Load){
            menuUiState.onNext(((MenuUiEvent.Load) event).getLoadedMenu());
        }
    }
}