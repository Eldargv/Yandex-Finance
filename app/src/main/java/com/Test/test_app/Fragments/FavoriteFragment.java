package com.Test.test_app.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Test.test_app.CompanyModel;
import com.Test.test_app.R;
import com.Test.test_app.StocksAdapter;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

    private ArrayList<CompanyModel> FavoriteList = new ArrayList<CompanyModel>();

    public FavoriteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_page_favorite, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_favorite);
        Context context = view.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new StocksAdapter(FavoriteList, view.getContext()));
        return view;
    }
}