package com.Test.test_app.Fragments;

import android.content.Context;
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

public class SearchFragment extends Fragment implements StocksAdapter.OnStarListener {
    private static final String ARG_TEXT = "argQuery";
    private StocksAdapter stocksAdapter;
    private StockViewModel model;
    private ProgressBar progressBar;

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
        return inflater.inflate(R.layout.activity_page_stocks, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        model = new ViewModelProvider(requireActivity()).get(StockViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.rv_stocks);
        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        stocksAdapter = new StocksAdapter(this);
        recyclerView.setAdapter(stocksAdapter);

//        model.fetchSearchList().observe(getViewLifecycleOwner(), item -> stocksAdapter.setStockList(item));

        String query = getArguments().getString(ARG_TEXT);

        String TAG = "TAG";
        Log.i(TAG, "Start query response " + query);

        Log.i("TAG", "Trying start a new thread");

        model.ClearSearchList();
        model.getSearchStocks(getArguments().getString(ARG_TEXT));

        progressBar = view.findViewById(R.id.progress_bar);

        if (query == null || query.isEmpty()) {
            progressBar.setVisibility(View.INVISIBLE);
        }

        model.getSearchProcessCode().observe(getViewLifecycleOwner(), code -> {
            Log.i("TAG", "Code " + code);
            switch (code) {
                case 999:
                    progressBar.setVisibility(View.INVISIBLE);
                    stocksAdapter.setStockList(model.fetchSearchList().getValue());
                    break;
                case 429:
                    Toast.makeText(getActivity(), R.string.apiLimit, Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    stocksAdapter.setStockList(model.fetchSearchList().getValue());
                    break;
                case 0:
                    Toast.makeText(getActivity(), R.string.nothing, Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    break;
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
