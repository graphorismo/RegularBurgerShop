package ru.graphorismo.regularburgershop.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Set;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import ru.graphorismo.regularburgershop.databinding.FragmentMenuBinding;
import ru.graphorismo.regularburgershop.domain.Menu;
import ru.graphorismo.regularburgershop.domain.Product;

public class MenuFragment extends Fragment implements Observer<Menu> {

    private FragmentMenuBinding binding;
    LinearLayout linearLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MenuViewModel menuViewModel =
                new ViewModelProvider(this).get(MenuViewModel.class);

        binding = FragmentMenuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        linearLayout = binding.fragmentMenuScrollViewLinearLayout;

        menuViewModel.menuUiState.subscribe(this);

        Menu menu = new Menu();
        menu.addTitle("TITLE1");
        menu.addProductUnderTitle(new Product("APPLE1", 10), "TITLE1");
        menu.addProductUnderTitle(new Product("APPLE2", 20), "TITLE1");
        menu.addTitle("TITLE2");
        menu.addProductUnderTitle(new Product("APPLE3", 30), "TITLE2");
        menu.addProductUnderTitle(new Product("APPLE4", 40), "TITLE2");

        menuViewModel.menuUiState.onNext(menu);





        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

    }

    @Override
    public void onNext(@io.reactivex.rxjava3.annotations.NonNull Menu menu) {
        Set<String> titles = menu.getTitles();
        for (String title : titles){
            TextView textView = new TextView(getContext());
            textView.setText(title);

            RecyclerView recyclerView = new RecyclerView(getContext());
            MenuRecyclerAdapter adapter = new MenuRecyclerAdapter(menu.getProductsUnderTitle(title));
            LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

            linearLayout.addView(textView);
            linearLayout.addView(recyclerView);
        }
    }

    @Override
    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}