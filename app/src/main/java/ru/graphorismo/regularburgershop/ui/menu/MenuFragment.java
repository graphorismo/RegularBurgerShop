package ru.graphorismo.regularburgershop.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.Collections;
import java.util.Map;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import ru.graphorismo.regularburgershop.databinding.FragmentMenuBinding;
import ru.graphorismo.regularburgershop.ui.menu.observers.TitlesUiStateObserver;

@AndroidEntryPoint
public class MenuFragment extends Fragment {

    private static final String TAG = "MenuFragment";
    
    private FragmentMenuBinding binding;
    private LinearLayout topLinearLayout;
    private MenuViewModel menuViewModel;
    CompositeDisposable disposables = new CompositeDisposable();

    private Map<String, LinearLayout> layoutForTitles = Collections.emptyMap();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        menuViewModel = new ViewModelProvider(this).get(MenuViewModel.class);

        binding = FragmentMenuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        topLinearLayout = binding.fragmentMenuScrollViewLinearLayout;

        TitlesUiStateObserver titlesUiStateObserver = new TitlesUiStateObserver(getContext());
        menuViewModel.titlesStateBehaviorSubject.subscribe(titlesUiStateObserver);
        Disposable disposable = titlesUiStateObserver
                .getLoadCompleteStateBehaviorSubject().subscribe((b)->{
                    if(b==true){
                        layoutForTitles = titlesUiStateObserver.getLayoutsForTitles();
                        for (LinearLayout layout: layoutForTitles.values()){
                            topLinearLayout.addView(layout);
                        }
                    }
                }
        );
        disposables.add(disposable);
        menuViewModel.onEvent(new MenuUiEvent.Load());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        disposables.clear();
    }

}