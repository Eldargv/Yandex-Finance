package com.Test.test_app.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Test.test_app.Data.StockViewModel;
import com.Test.test_app.Stock;
import com.Test.test_app.R;
import com.Test.test_app.Adapters.StocksAdapter;

public class StocksFragment extends Fragment implements StocksAdapter.OnStarListener {

    private StocksAdapter stocksAdapter;
    private StockViewModel model;

    public StocksFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_page_stocks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model = new ViewModelProvider(requireActivity()).get(StockViewModel.class);
        RecyclerView recyclerView = view.findViewById(R.id.rv_stocks);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        stocksAdapter = new StocksAdapter(this);
        recyclerView.setAdapter(stocksAdapter);

        ProgressBar progressBar = view.findViewById(R.id.progress_bar);

        model.getDefaultStocks("^GSPC");

        model.getDefaultProcessCode().observe(getViewLifecycleOwner(), code -> {
            Log.i("TAG", "Code " + code);
            if (code == 999) {
                progressBar.setVisibility(View.INVISIBLE);
                stocksAdapter.setStockList(model.fetchDefaultList().getValue());
            } else if (code == 429) {
                Toast.makeText(getActivity(), R.string.apiLimit, Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
                stocksAdapter.setStockList(model.fetchDefaultList().getValue());
            }
        });

        model.getFavoriteChangedTicker().observe(getViewLifecycleOwner(), item -> {
            Log.i("TAG", "try to change stocklist");
            for (int i = 0; i < stocksAdapter.getItemCount(); i++) {
                if (item != null && stocksAdapter.getItemAt(i).getTicker().equals(item.getTicker())) {
                    stocksAdapter.getItemAt(i).setStarMode(item.getStarMode());
                    stocksAdapter.notifyItemChanged(i);
                    break;
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStarClick(int position) {
        Stock stock = stocksAdapter.getItemAt(position);
        if (stock.getStarMode() == R.drawable.star_selected) {
            stock.setStarMode(R.drawable.star_unselected);
            model.delete(stock);
        } else {
            stock.setStarMode(R.drawable.star_selected);
            model.insert(stock);
        }
        stocksAdapter.notifyItemChanged(position);
    }
}