package ru.graphorismo.regularburgershop.ui.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import ru.graphorismo.regularburgershop.R;
import ru.graphorismo.regularburgershop.data.Product;

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.ViewHolder> {

    private final ArrayList<Product> products = new ArrayList<>();

    CartRecyclerAdapter() {

    }

    @NonNull
    @Override
    public CartRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_recycler_item, parent, false);
        return new CartRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartRecyclerAdapter.ViewHolder holder, int position) {
        holder.getNameTextView().setText(products.get(position).getName());
        holder.getPriceTextView().setText(products.get(position).getPrice().toString());
        ImageView imageView = holder.getPictureImageView();
        if(!products.get(position).getPictureUrl().isEmpty()){
            Picasso.get()
                    .load(products.get(position).getPictureUrl())
                    .into(imageView);
        }
        imageView.setOnClickListener(view -> {
            EventBus.getDefault().post(new CartUiEvent.RemoveProductFromCart(products.get(position)));
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(List<Product> productsToSet) {
        products.clear();
        products.addAll(productsToSet);
        this.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameTextView;
        private final TextView priceTextView;
        private final ImageView pictureImageView;


        public ViewHolder(View view) {
            super(view);

            nameTextView = view.findViewById(R.id.cartRecyclerItem_textView_name);
            priceTextView = view.findViewById(R.id.cartRecyclerItem_textView_price);
            pictureImageView = view.findViewById(R.id.cartRecyclerItem_imageView_picture);
        }

        public TextView getNameTextView() {
            return nameTextView;
        }

        public TextView getPriceTextView() {
            return priceTextView;
        }

        public ImageView getPictureImageView() {
            return pictureImageView;
        }
    }
}

