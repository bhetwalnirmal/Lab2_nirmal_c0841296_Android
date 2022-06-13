package com.nirmalbhetwal.lab2_nirmal_c0841296_android.adapters;

import android.content.Context;
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
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
}
