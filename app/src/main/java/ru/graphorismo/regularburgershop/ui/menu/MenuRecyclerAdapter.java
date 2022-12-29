package ru.graphorismo.regularburgershop.ui.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.graphorismo.regularburgershop.R;
import ru.graphorismo.regularburgershop.data.Product;

public class MenuRecyclerAdapter extends RecyclerView.Adapter<MenuRecyclerAdapter.ViewHolder> {

    ArrayList<Product> products = new ArrayList<>();

    MenuRecyclerAdapter(ArrayList<Product> products){
        this.products = products;
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
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameTextView;
        private final TextView priceTextView;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            nameTextView = view.findViewById(R.id.menuRecyclerItem_textView_name);
            priceTextView = view.findViewById(R.id.menuRecyclerItem_textView_price);
        }

        public TextView getNameTextView() {return nameTextView;}
        public TextView getPriceTextView() {return priceTextView;}
    }


}
