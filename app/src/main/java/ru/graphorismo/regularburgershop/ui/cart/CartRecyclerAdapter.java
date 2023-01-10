package ru.graphorismo.regularburgershop.ui.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

import ru.graphorismo.regularburgershop.R;
import ru.graphorismo.regularburgershop.data.Product;
import ru.graphorismo.regularburgershop.ui.menu.MenuUiEvent;

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.ViewHolder> {

    private final ArrayList<Product> products = new ArrayList<>();

    private final CartViewModel cartViewModel;

    CartRecyclerAdapter(CartViewModel cartViewModel) {
        this.cartViewModel = cartViewModel;
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
        Picasso.get()
                .load(products.get(position).getPictureUrl())
                .into(imageView);
        imageView.setOnClickListener(view -> {
            cartViewModel.onEvent(new CartUiEvent.RemoveProductFromCart(products.get(position)));
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void addProducts(List<Product> productsToAdd) {
        products.addAll(productsToAdd);
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

