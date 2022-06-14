package com.nirmalbhetwal.lab2_nirmal_c0841296_android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nirmalbhetwal.lab2_nirmal_c0841296_android.R;
import com.nirmalbhetwal.lab2_nirmal_c0841296_android.models.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context context;
    List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a inflater
        LayoutInflater inflater = LayoutInflater.from(context);
        // use the inflater to inflate from the dashboard_row xml layout file
        View listItem = inflater.inflate(R.layout.layout_dashboard_row, parent, false);

        ViewHolder viewHolder = new ViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Product product = productList.get(position);

        holder.tvProductName.setText(product.getName());
        holder.tvProductDescription.setText(product.getDescription());
        holder.tvProductPrice.setText(String.format("$ %.2f", product.getPrice()));
    }

    @Override
    public int getItemCount() {
        return this.productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvProductName, tvProductDescription, tvProductPrice;

        public ViewHolder (View itemView) {
            super(itemView);

            this.tvProductName = (TextView) itemView.findViewById(R.id.tvProductName);
            this.tvProductDescription = (TextView) itemView.findViewById(R.id.tvProductDescription);
            this.tvProductPrice = (TextView) itemView.findViewById(R.id.tvProductPrice);
        }
    }

    public void setProductList (List<Product> productList) {
        this.productList = productList;
        this.notifyDataSetChanged();
    }
}
