package ru.graphorismo.regularburgershop.ui.menu;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
    private LinearLayout topLinearLayout;
    private MenuViewModel menuViewModel;
    CompositeDisposable disposables = new CompositeDisposable();

    private Map<String, RecyclerView> recyclerViewMap = new HashMap<>();
    private Map<String, MenuRecyclerAdapter> adapterMap = new HashMap<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        menuViewModel = new ViewModelProvider(this).get(MenuViewModel.class);

        binding = FragmentMenuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        topLinearLayout = binding.fragmentMenuScrollViewLinearLayout;
        observeProductsFromViewModel();

        binding.swipeRefreshLayout.setOnRefreshListener(()->{
            menuViewModel.onEvent(new MenuUiEvent.Refresh());
            binding.swipeRefreshLayout.setRefreshing(false);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        disposables.clear();
    }

    public void observeProductsFromViewModel(){
        menuViewModel.getProductsBehaviorSubject()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<List<Product>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<Product> products) {
                        loadContent(products);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.e(TAG, "onError: "+e.getMessage() );
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void loadContent(List<Product> products) {
        for(Product product: products){
            String title = product.getTitle();
            if( ! recyclerViewMap.containsKey(title)){
                RecyclerView recyclerView = new RecyclerView(getContext());
                MenuRecyclerAdapter menuRecyclerAdapter = new MenuRecyclerAdapter();
                recyclerViewMap.put(title, recyclerView);
                adapterMap.put(title, menuRecyclerAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                        LinearLayoutManager.HORIZONTAL, false));
                recyclerView.setAdapter(menuRecyclerAdapter);
                TextView textView = new TextView(getContext());
                textView.setText(title);
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(32);
                topLinearLayout.addView(textView);
                topLinearLayout.addView(recyclerView);
            }else{
                adapterMap.get(title).addProduct(product);
            }
        }
    }

}