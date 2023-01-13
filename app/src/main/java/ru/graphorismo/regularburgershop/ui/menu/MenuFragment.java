package ru.graphorismo.regularburgershop.ui.menu;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import ru.graphorismo.regularburgershop.data.Product;
import ru.graphorismo.regularburgershop.databinding.FragmentMenuBinding;


@AndroidEntryPoint
public class MenuFragment extends Fragment {

    private static final String TAG = "MenuFragment";
    
    private FragmentMenuBinding binding;
    private MenuViewModel menuViewModel;
    CompositeDisposable disposables = new CompositeDisposable();

    private MenuRecyclerAdapter recyclerViewComboAdapter;
    private MenuRecyclerAdapter recyclerViewCheeseburgerAdapter;
    private MenuRecyclerAdapter recyclerViewFriesAdapter;
    private MenuRecyclerAdapter recyclerViewHamburgerAdapter;
    private MenuRecyclerAdapter recyclerViewIcecreamAdapter;
    private MenuRecyclerAdapter recyclerViewSodaAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        menuViewModel = new ViewModelProvider(this).get(MenuViewModel.class);

        binding = FragmentMenuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerViewCombo = binding.fragmentMenuRecyclerViewCombo;
        recyclerViewCombo.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        recyclerViewComboAdapter = new MenuRecyclerAdapter(menuViewModel);
        recyclerViewCombo.setAdapter(recyclerViewComboAdapter);

        RecyclerView recyclerViewCheeseburger = binding.fragmentMenuRecyclerViewCheeseburger;
        recyclerViewCheeseburger.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCheeseburgerAdapter = new MenuRecyclerAdapter(menuViewModel);
        recyclerViewCheeseburger.setAdapter(recyclerViewCheeseburgerAdapter);

        RecyclerView recyclerViewFries = binding.fragmentMenuRecyclerViewFries;
        recyclerViewFries.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        recyclerViewFriesAdapter = new MenuRecyclerAdapter(menuViewModel);
        recyclerViewFries.setAdapter(recyclerViewFriesAdapter);

        RecyclerView recyclerViewHamburger = binding.fragmentMenuRecyclerViewHamburger;
        recyclerViewHamburger.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        recyclerViewHamburgerAdapter = new MenuRecyclerAdapter(menuViewModel);
        recyclerViewHamburger.setAdapter(recyclerViewHamburgerAdapter);

        RecyclerView recyclerViewIcecream = binding.fragmentMenuRecyclerViewIcecream;
        recyclerViewIcecream.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        recyclerViewIcecreamAdapter = new MenuRecyclerAdapter(menuViewModel);
        recyclerViewIcecream.setAdapter(recyclerViewIcecreamAdapter);

        RecyclerView recyclerViewSoda = binding.fragmentMenuRecyclerViewSoda;
        recyclerViewSoda.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        recyclerViewSodaAdapter = new MenuRecyclerAdapter(menuViewModel);
        recyclerViewSoda.setAdapter(recyclerViewSodaAdapter);


        LinearLayout topLinearLayout = binding.fragmentMenuScrollViewLinearLayout;
        observeProductsFromViewModel();
        observeExceptionsFromViewModel();

        binding.swipeRefreshLayout.setOnRefreshListener(()->{
            menuViewModel.onEvent(new MenuUiEvent.Refresh());
            binding.swipeRefreshLayout.setRefreshing(false);
        });

        Log.d(TAG, "onCreateView: Create fragment");
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        disposables.clear();
    }

    private void observeExceptionsFromViewModel() {
        menuViewModel.getExceptionLiveData().observe(getViewLifecycleOwner(),
                throwable -> {
                    showDialog("Error", throwable.getMessage());
                } );
    }

    public void observeProductsFromViewModel(){
        menuViewModel.getProductsLiveData().observe(getViewLifecycleOwner(), this::loadContent);
    }

    private void loadContent(List<Product> products) {

        for(Product product : products) {
            if (Objects.equals(product.getTitle(), "combo")){
                recyclerViewComboAdapter.addProduct(product);
            }else if (Objects.equals(product.getTitle(), "cheeseburger")){
                recyclerViewCheeseburgerAdapter.addProduct(product);
            }else if (Objects.equals(product.getTitle(), "hamburger")){
                recyclerViewHamburgerAdapter.addProduct(product);
            }else if (Objects.equals(product.getTitle(), "fries")){
                recyclerViewFriesAdapter.addProduct(product);
            }else if (Objects.equals(product.getTitle(), "ice cream")){
                recyclerViewIcecreamAdapter.addProduct(product);
            }else if (Objects.equals(product.getTitle(), "soda")){
                recyclerViewSodaAdapter.addProduct(product);
            }
        }
    }

    private void showDialog(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}