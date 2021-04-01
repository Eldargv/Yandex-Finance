package com.Test.test_app.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Test.test_app.Api.ApiHolder;
import com.Test.test_app.Data.StockVIewModel;
import com.Test.test_app.Stock;
import com.Test.test_app.MainActivity;
import com.Test.test_app.R;
import com.Test.test_app.Adapters.StocksAdapter;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements StocksAdapter.OnStarListener {
    private static final String ARG_TEXT = "argQuery";
    private String Query = "";
    private ArrayList<Stock> StockList = new ArrayList<Stock>();
    private StocksAdapter stocksAdapter;
    private ApiHolder apiHolder;
    private String TAG = "TAG";
    private StockVIewModel model;

    public SearchFragment() {
    }

    public static SearchFragment newInstance(String query) {
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, query);
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_page_stocks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rv_stocks);
        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        stocksAdapter = new StocksAdapter(StockList, this);
        recyclerView.setAdapter(stocksAdapter);

        apiHolder = MainActivity.getApp().getApiHolder();

        model = new ViewModelProvider(requireActivity()).get(StockVIewModel.class);

        model.fetchSearchStock().observe(getViewLifecycleOwner(), item -> {
            StockList.add(item);
            stocksAdapter.notifyItemChanged(StockList.size() - 1);
        });

        Query = getArguments().getString(ARG_TEXT);

        Log.i(TAG, "Start query response " + Query);

        if (!getArguments().getString(ARG_TEXT).equals("")) {
            // getStocks(getArguments().getString(ARG_TEXT));
            Log.i("TAG", "Trying start a new thread");
            model.SearchQuery(getArguments().getString("argQuery"));
        } else {
            StockList.clear();
            stocksAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStarClick(int position) {
        Log.i("TAG", "Star ckicked");
        int r = R.drawable.star_selected;
        if (StockList.get(position).getStarMode() == R.drawable.star_selected) {
            r = R.drawable.star_unselected;
        }
        StockList.get(position).setStarMode(r);
        stocksAdapter.notifyItemChanged(position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
