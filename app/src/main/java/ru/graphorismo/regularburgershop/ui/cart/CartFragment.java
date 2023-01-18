package ru.graphorismo.regularburgershop.ui.cart;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import ru.graphorismo.regularburgershop.R;
import ru.graphorismo.regularburgershop.data.Product;
import ru.graphorismo.regularburgershop.data.local.room.cart.product.CartProductData;
import ru.graphorismo.regularburgershop.data.local.room.cart.product.ConverterBetweenProductAndCartProductData;

@AndroidEntryPoint
public class CartFragment extends Fragment {

    private CartViewModel cartViewModel;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private CartRecyclerAdapter recyclerAdapter;
    private EditText editTextCouponName;
    private EditText editTextProductsSumPrice;

    private static final String TAG = "CartFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cartViewModel =
                new ViewModelProvider(this).get(CartViewModel.class);

        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        Button buttonBuy = root.findViewById(R.id.fragmentCart_button_buy);
        buttonBuy.setOnClickListener(view -> {
            EventBus.getDefault().post(new CartUiEvent.BuyCart());
        });

        Button buttonClear = root.findViewById(R.id.fragmentCart_button_clear);
        buttonClear.setOnClickListener(view -> {
            EventBus.getDefault().post(new CartUiEvent.ClearCart());
        });

        recyclerAdapter = new CartRecyclerAdapter();
        RecyclerView recyclerView = root.findViewById(R.id.fragmentCart_recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(recyclerAdapter);


        editTextCouponName = root.findViewById(R.id.fragmentCart_editText_coupon);
        editTextProductsSumPrice = root.findViewById(R.id.fragmentCart_editText_sum);
        editTextCouponName.setInputType(InputType.TYPE_NULL);
        editTextProductsSumPrice.setInputType(InputType.TYPE_NULL);

        observeProductsFormViewModel();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposables.clear();
    }

    private void observeProductsFormViewModel(){
        disposables.add(
                cartViewModel.getProductsBehaviorSubject()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(productCartData -> {
                            List<Product> products = new ArrayList<>();
                            Integer sum = 0;
                            for(CartProductData cartData : productCartData){
                                Product product =
                                        ConverterBetweenProductAndCartProductData
                                                .convertCartProductDataToProduct(cartData);
                                products.add(product);
                                sum += cartData.getPrice();
                            }
                            recyclerAdapter.setProducts(products);
                            if (sum > 0)
                                editTextProductsSumPrice.setText("Sum: "+sum.toString());
                            else
                                editTextProductsSumPrice.setText("");
                        })
        );

    }
}