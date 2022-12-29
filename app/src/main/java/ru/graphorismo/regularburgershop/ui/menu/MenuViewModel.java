package ru.graphorismo.regularburgershop.ui.menu;
;
import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.subjects.BehaviorSubject;
import ru.graphorismo.regularburgershop.data.Menu;

public class MenuViewModel extends ViewModel {

    BehaviorSubject<Menu> menuUiState =
            BehaviorSubject.create();

    void onEvent(MenuUiEvent event){
        if (event instanceof MenuUiEvent.Load){
            menuUiState.onNext(((MenuUiEvent.Load) event).getLoadedMenu());
        }
    }
}