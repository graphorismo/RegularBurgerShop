package ru.graphorismo.regularburgershop.ui.menu;

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

import java.util.Collections;
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

    private Map<String, LinearLayout> layoutForTitles = Collections.emptyMap();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        menuViewModel = new ViewModelProvider(this).get(MenuViewModel.class);

        binding = FragmentMenuBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        topLinearLayout = binding.fragmentMenuScrollViewLinearLayout;

        menuViewModel.onEvent(new MenuUiEvent.Load())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<Product>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull Product product) {
                        TextView textView = new TextView(getContext());
                        textView.setText("Name: "+product.getName()+", Price: "+product.getPrice());
                        topLinearLayout.addView(textView);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.e(TAG, "onError: "+e.getMessage() );
                    }

                    @Override
                    public void onComplete() {

                    }
                });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        disposables.clear();
    }

}