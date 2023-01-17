package ru.graphorismo.regularburgershop.ui.menu;

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

import javax.inject.Inject;

import ru.graphorismo.regularburgershop.R;
import ru.graphorismo.regularburgershop.data.Product;

public class MenuRecyclerAdapter extends RecyclerView.Adapter<MenuRecyclerAdapter.ViewHolder> {

    private final ArrayList<Product> products = new ArrayList<>();

    MenuRecyclerAdapter(){

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_recycler_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getNameTextView().setText(products.get(position).getName());
        holder.getPriceTextView().setText(products.get(position).getPrice().toString());
        ImageView imageView = holder.getPictureImageView();
        Picasso.get()
                .load(products.get(position).getPictureUrl())
                .into(imageView);
        imageView.setOnClickListener(view -> {
            EventBus.getDefault().post(new MenuUiEvent.AddProductToCart(products.get(position)));
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(List<Product> productsToAdd) {
        products.clear();
        products.addAll(productsToAdd);
        this.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameTextView;
        private final TextView priceTextView;
        private final ImageView pictureImageView;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            nameTextView = view.findViewById(R.id.menuRecyclerItem_textView_nameField);
            priceTextView = view.findViewById(R.id.menuRecyclerItem_textView_priceField);
            pictureImageView = view.findViewById(R.id.menuRecyclerItem_imageView_picture);
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
