package com.Test.test_app.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Test.test_app.Adapters.ViewPagerAdapter;
import com.Test.test_app.Data.StockVIewModel;
import com.Test.test_app.Stock;
import com.Test.test_app.R;
import com.Test.test_app.Adapters.StocksAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class FavoriteFragment extends Fragment implements StocksAdapter.OnStarListener {

    private StocksAdapter stocksAdapter;
    private StockVIewModel model;

    public FavoriteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_page_stocks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = new ViewModelProvider(requireActivity()).get(StockVIewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.rv_stocks);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        stocksAdapter = new StocksAdapter(this);
        recyclerView.setAdapter(stocksAdapter);

        ProgressBar progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        model.fetchFavoriteList().observe(getViewLifecycleOwner(), item -> {
            stocksAdapter.setStockList(item);
            Log.i("TAG", "Favorite data changed");
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStarClick(int position) {
        Stock stock = stocksAdapter.getItemAt(position);
        stock.setStarMode(R.drawable.star_unselected);
        stocksAdapter.notifyItemChanged(position);
        model.delete(stock);
        stocksAdapter.RemoveItemAt(position);
    }
}