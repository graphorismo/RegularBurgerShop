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
import ru.graphorismo.regularburgershop.databinding.FragmentCouponsBinding;

@AndroidEntryPoint
public class CouponsFragment extends Fragment {

    private FragmentCouponsBinding binding;
    private CouponsViewModel couponsViewModel;
    private RecyclerView recyclerView;
    private CouponsRecyclerAdapter recyclerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        couponsViewModel =
                new ViewModelProvider(this).get(CouponsViewModel.class);

        binding = FragmentCouponsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.fragmentCouponsRecyclerView;
        recyclerAdapter = new CouponsRecyclerAdapter(couponsViewModel, getViewLifecycleOwner());
        recyclerView.setAdapter(recyclerAdapter);
        binding.fragmentCouponsRecyclerView
                .setLayoutManager(new LinearLayoutManager(getContext(),
                        LinearLayoutManager.VERTICAL, false));

        observeCouponsFromViewModel();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void observeCouponsFromViewModel(){
        couponsViewModel.getCouponsLiveData().observe(getViewLifecycleOwner(), coupons -> {
            recyclerAdapter.setCoupons(coupons);
        });
    }


}