package ru.graphorismo.regularburgershop.ui.coupons;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import ru.graphorismo.regularburgershop.R;

@AndroidEntryPoint
public class CouponsFragment extends Fragment {

    private CouponsViewModel couponsViewModel;
    private CouponsRecyclerAdapter recyclerAdapter;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        couponsViewModel =
                new ViewModelProvider(this).get(CouponsViewModel.class);

        View root = inflater.inflate(R.layout.fragment_coupons, container, false);


        RecyclerView recyclerView = root.findViewById(R.id.fragmentCoupons_recyclerView);
        recyclerAdapter = new CouponsRecyclerAdapter(couponsViewModel, disposables);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                        LinearLayoutManager.VERTICAL, false));

        observeCouponsFromViewModel();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposables.clear();
    }

    private void observeCouponsFromViewModel(){
        disposables.add(
                couponsViewModel.getShowedCouponsBehaviorSubject()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(coupons -> {
                            recyclerAdapter.setCoupons(coupons);
                        })
        );
    }


}