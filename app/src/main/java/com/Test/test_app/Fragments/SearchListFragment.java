package com.Test.test_app.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Test.test_app.Api.ApiHolder;
import com.Test.test_app.CompanyModel;
import com.Test.test_app.MainActivity;
import com.Test.test_app.R;
import com.Test.test_app.StocksAdapter;

import java.util.ArrayList;

public class SearchListFragment extends Fragment
{
    private static final String ARG_TEXT = "argQuery";
    private String Query = "";

    private ArrayList<CompanyModel> SearchList = new ArrayList<CompanyModel>();

    private StocksAdapter stocksAdapter;

    public SearchListFragment() {
    }

    public static SearchListFragment newInstance(String query) {
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, query);
        SearchListFragment fragment = new SearchListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_page_stocks, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_stocks);
        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new StocksAdapter(SearchList, view.getContext()));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rv_stocks);
        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        stocksAdapter = new StocksAdapter(SearchList, context);
        recyclerView.setAdapter(stocksAdapter);

        ApiHolder apiHolder = MainActivity.getApp().getFinanceService().getApiHolder();

        getStocks(Query);
    }

    void getStocks(String query) {
    }
}
