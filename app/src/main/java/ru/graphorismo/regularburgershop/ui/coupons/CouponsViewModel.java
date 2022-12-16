package ru.graphorismo.regularburgershop.ui.coupons;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CouponsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CouponsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is coupons fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}