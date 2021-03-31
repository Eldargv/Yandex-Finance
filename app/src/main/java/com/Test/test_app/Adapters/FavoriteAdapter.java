package com.Test.test_app.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Test.test_app.R;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.StocksVH> {

    public FavoriteAdapter() {

    }

    @NonNull
    @Override
    public StocksVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_stock, parent, false);
        return new StocksVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StocksVH holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class StocksVH extends RecyclerView.ViewHolder {
        public StocksVH(@NonNull View itemView) {
            super(itemView);
        }
    }
}
