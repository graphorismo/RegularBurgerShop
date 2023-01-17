package ru.graphorismo.regularburgershop.ui.menu;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

import ru.graphorismo.regularburgershop.R;
import ru.graphorismo.regularburgershop.data.Product;


@AndroidEntryPoint
public class MenuFragment extends Fragment {

    private static final String TAG = "MenuFragment";

    private MenuViewModel menuViewModel;

    MenuRecyclerAdapter recyclerAdapter;

    CompositeDisposable disposables = new CompositeDisposable();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        menuViewModel = new ViewModelProvider(this).get(MenuViewModel.class);
        View root = inflater.inflate(R.layout.fragment_menu, container, false);
        TabLayout tabLayout = root.findViewById(R.id.fragmentMenu_tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                EventBus.getDefault().post(
                        new MenuUiEvent.ChangeOfShowedProductsTitle(
                                tab.getText().toString().toLowerCase(Locale.ROOT)));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        recyclerAdapter = new MenuRecyclerAdapter();
        RecyclerView recyclerView = root.findViewById(R.id.fragmentMenu_recyclerView);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(),
                        LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(recyclerAdapter);
        observeProductsFromViewModel();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposables.clear();
    }

    public void observeProductsFromViewModel(){
        //menuViewModel.getProductsLiveData()
                //.observe(getViewLifecycleOwner(), recyclerAdapter::setProducts);
        disposables.add(
                menuViewModel.getProductsBehaviorSubject()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((next)->{
                            Log.d(TAG, "observeProductsFromViewModel size "+next.size());
                            recyclerAdapter.setProducts(next);
                        }, throwable -> {
                            Log.e(TAG,"observeProductsFromViewModel Error:"+throwable.getMessage());
                        })
        );
    }

}