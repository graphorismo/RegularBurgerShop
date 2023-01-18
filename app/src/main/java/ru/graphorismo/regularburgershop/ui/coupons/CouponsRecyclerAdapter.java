package ru.graphorismo.regularburgershop.ui.coupons;

import android.graphics.Color;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import ru.graphorismo.regularburgershop.R;
import ru.graphorismo.regularburgershop.data.Coupon;

public class CouponsRecyclerAdapter extends RecyclerView.Adapter<CouponsRecyclerAdapter.ViewHolder> {

    private final List<Coupon> coupons = new ArrayList<>();
    private Integer chosenCouponPosition = -1;
    private final CouponsViewModel couponsViewModel;
    private final CompositeDisposable disposables;

    CouponsRecyclerAdapter(CouponsViewModel couponsViewModel, CompositeDisposable disposables){
        this.couponsViewModel = couponsViewModel;
        this.disposables = disposables;
        observeChosenCouponFromViewModel();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.coupons_recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getCouponEditText().setText(coupons.get(position).getCouponName());
        holder.getProductEditText().setText(coupons.get(position).getProduct().getName());
        holder.getDiscountEditText()
                .setText("-"+coupons.get(position).getDiscountPercents().toString()+"%");
        ImageView imageView = holder.getProductImageView();
        if(!coupons.get(position).getProduct().getPictureUrl().isEmpty()){
            Picasso.get()
                    .load(coupons.get(position).getProduct().getPictureUrl())
                    .into(imageView);
        }
        holder.getCardView().setCardBackgroundColor(Color.WHITE);
        holder.getCardView().setOnClickListener(view -> {
            EventBus.getDefault().post(new CouponsUIEvent.CouponChosen(coupons.get(position)));
        });
        if(chosenCouponPosition != -1 && position == chosenCouponPosition){
            holder.getCardView().setCardBackgroundColor(Color.GRAY);
        }


    }

    @Override
    public int getItemCount() {
        return coupons.size();
    }

    public void setCoupons(List<Coupon> coupons){
        this.coupons.clear();
        this.coupons.addAll(coupons);
        notifyDataSetChanged();
    }

    void observeChosenCouponFromViewModel(){
        disposables.add(
                couponsViewModel.getChosenCouponBehaviorSubject()
                        .delay(300, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((coupon)->{
                            Integer position = coupons.indexOf(coupon);
                            if (position != -1) {
                                chosenCouponPosition = position;
                                this.notifyDataSetChanged();
                            }
                        })
        );
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final EditText couponEditText;
        private final EditText productEditText;
        private final EditText discountEditText;
        private final ImageView productImageView;
        private final CardView cardView;


        public ViewHolder(View view) {
            super(view);

            couponEditText = view.findViewById(R.id.couponsRecyclerItem_editText_coupon);
            productEditText = view.findViewById(R.id.couponsRecyclerItem_editText_product);
            discountEditText = view.findViewById(R.id.couponsRecyclerItem_editText_discount);
            productImageView = view.findViewById(R.id.couponsRecyclerItem_imageView_product);
            cardView = view.findViewById(R.id.couponsRecyclerItem_cardView);

            couponEditText.setInputType(InputType.TYPE_NULL);
            productEditText.setInputType(InputType.TYPE_NULL);
            discountEditText.setInputType(InputType.TYPE_NULL);


        }

        public EditText getCouponEditText() {
            return couponEditText;
        }

        public EditText getProductEditText() {
            return productEditText;
        }

        public EditText getDiscountEditText() {
            return discountEditText;
        }

        public ImageView getProductImageView() {
            return productImageView;
        }

        public CardView getCardView() {
            return cardView;
        }
    }


}
