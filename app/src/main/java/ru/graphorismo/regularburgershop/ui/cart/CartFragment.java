package ru.graphorismo.regularburgershop.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import ru.graphorismo.regularburgershop.data.Product;
import ru.graphorismo.regularburgershop.data.local.room.ConverterBetweenProductAndProductCartData;
import ru.graphorismo.regularburgershop.data.local.room.ProductCartData;
import ru.graphorismo.regularburgershop.databinding.FragmentCartBinding;

@AndroidEntryPoint
public class CartFragment extends Fragment {

    private FragmentCartBinding binding;
    private CartViewModel cartViewModel;
    private CompositeDisposable disposables = new CompositeDisposable();
    private CartRecyclerAdapter recyclerAdapter;

    private static final String TAG = "CartFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cartViewModel =
                new ViewModelProvider(this).get(CartViewModel.class);

        binding = FragmentCartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.fragmentCartButtonBuy.setOnClickListener(view -> {
            cartViewModel.onEvent(new CartUiEvent.BuyCart());
        });

        binding.fragmentCartButtonClear.setOnClickListener(view -> {
            cartViewModel.onEvent(new CartUiEvent.ClearCart());
        });

        recyclerAdapter = new CartRecyclerAdapter(cartViewModel);
        binding.fragmentCartRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        binding.fragmentCartRecyclerView.setAdapter(recyclerAdapter);

        observeProductsFormViewModel();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void observeProductsFormViewModel(){
        cartViewModel.getProductsLiveData()
                .observe(getViewLifecycleOwner(), productCartData -> {
                    List<Product> products = new ArrayList<>();
                    for(ProductCartData cartData : productCartData){
                        Product product =
                                ConverterBetweenProductAndProductCartData
                                        .convertProductCartDataToProduct(cartData);
                        products.add(product);
                    }
                    recyclerAdapter.setProducts(products);
                });

    }
}