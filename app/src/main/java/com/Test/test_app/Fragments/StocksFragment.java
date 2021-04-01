package com.Test.test_app.Fragments;

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

import com.Test.test_app.App;
import com.Test.test_app.Data.DataBase;
import com.Test.test_app.Data.StockDao;
import com.Test.test_app.Data.StockVIewModel;
import com.Test.test_app.RoomModels.StockRoom;
import com.Test.test_app.Stock;
import com.Test.test_app.R;
import com.Test.test_app.Adapters.StocksAdapter;

import java.util.ArrayList;

public class StocksFragment extends Fragment implements StocksAdapter.OnStarListener {

    private StocksAdapter stocksAdapter;
    private ArrayList<Stock> StockList = new ArrayList<Stock>();
    private final String TAG = "TAG";
    private StockVIewModel model;
    private StockDao stockDao = App.getInstance().getStockDao();

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
        RecyclerView recyclerView = view.findViewById(R.id.rv_stocks);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        stocksAdapter = new StocksAdapter(StockList, this);
        recyclerView.setAdapter(stocksAdapter);

        model = new ViewModelProvider(requireActivity()).get(StockVIewModel.class);
        model.getStocks("^GSPC");

        model.fetchDefaultStock().observe(getViewLifecycleOwner(), item -> {
            StockList.add(item);
            stocksAdapter.notifyItemChanged(StockList.size() - 1);
        });
    }

    @Override
    public void onStarClick(int position) {
        Log.i("TAG", "Star ckicked");
        int r = R.drawable.star_unselected;
        Stock currStock = StockList.get(position);
        if (currStock.getStarMode() == R.drawable.star_unselected) {
            r = R.drawable.star_selected;
            StockRoom stockRoom = new StockRoom();
            stockRoom.ticker = currStock.getTicker();
            stockRoom.name = currStock.getName();
            //stockRoom.logo = currStock.getLogoImage();

            stockDao.insert(stockRoom);
        }
        currStock.setStarMode(r);
        stocksAdapter.notifyItemChanged(position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}