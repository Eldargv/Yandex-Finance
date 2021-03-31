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

import com.Test.test_app.CompanyModel;
import com.Test.test_app.MainActivity;
import com.Test.test_app.R;
import com.Test.test_app.Adapters.StocksAdapter;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment implements StocksAdapter.OnStarListener {

    private ArrayList<CompanyModel> FavoriteList = new ArrayList<CompanyModel>();
    private StocksAdapter stocksAdapter;

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
        RecyclerView recyclerView = view.findViewById(R.id.rv_stocks);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        stocksAdapter = new StocksAdapter(FavoriteList, this);
        recyclerView.setAdapter(stocksAdapter);
    }

    @Override
    public void onStarClick(int position) {

    }
}