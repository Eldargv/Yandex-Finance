package com.Test.test_app.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Test.test_app.Api.ApiHolder;
import com.Test.test_app.Api.pojoModels.CompanyProfile;
import com.Test.test_app.Api.pojoModels.QuoteModel;
import com.Test.test_app.Api.pojoModels.SearchList;
import com.Test.test_app.Api.pojoModels.SearchResultList;
import com.Test.test_app.CompanyModel;
import com.Test.test_app.MainActivity;
import com.Test.test_app.R;
import com.Test.test_app.Adapters.StocksAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment implements StocksAdapter.OnStarListener {
    private static final String ARG_TEXT = "argQuery";
    private String Query = "";
    private ArrayList<CompanyModel> StockList = new ArrayList<CompanyModel>();
    private StocksAdapter stocksAdapter;
    private ApiHolder apiHolder;
    private String TAG = "TAG";

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

        Query = getArguments().getString(ARG_TEXT);

        Log.i(TAG, "Start query response " + Query);

        if (!getArguments().getString(ARG_TEXT).equals("")) {
            // getStocks(getArguments().getString(ARG_TEXT));
            Log.i("TAG", "Trying start a new thread");
            StartSearchThread();
        } else {
            StockList.clear();
            stocksAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    void StartSearchThread() {
        StockRunnable stockRunnable = new StockRunnable();
        new Thread(stockRunnable).start();
    }

    @Override
    public void onStarClick(int position) {

    }

    class StockRunnable implements Runnable {
        @Override
        public void run() {
            Call<SearchList> call = apiHolder.getSearchList(getArguments().getString(ARG_TEXT));
            try {
                Response<SearchList> response = call.execute();
                if (response.code() == 429) {
                    Toast.makeText(getActivity(), "API limit reached. Please try again later", Toast.LENGTH_LONG).show();
                }
                if (response.body().getCount() == 0) {
                    Toast.makeText(getActivity(), "Nothing", Toast.LENGTH_LONG).show();
                    return;
                }
                HashMap<String, Integer> count = new HashMap<String, Integer>();
                List<SearchResultList> result = response.body().getResult();
                for (int i = 0; i < response.body().getCount(); i++) {
                    SearchResultList res = result.get(i);
                    if (res.getType().equals("Common Stock")) {
                        Log.i(TAG, "Trying to get Profile of " + res.getSymbol());
                        getProfile(res.getSymbol());
                        count.put(res.getDescription(), 1);
                        Thread.sleep(100);
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void getProfile(String symbol) {
        Call<CompanyProfile> call = apiHolder.getProfile(symbol);
        call.enqueue(new Callback<CompanyProfile>() {
            @Override
            public void onResponse(Call<CompanyProfile> call, Response<CompanyProfile> response) {
                if (!response.isSuccessful()) {
                    switch (response.code()) {
                        case 403:
                            Toast.makeText(getActivity(), "Acces error", Toast.LENGTH_LONG).show();
                            return;
                        case 429:
                            Toast.makeText(getActivity(), "API limit reached. Please try again later", Toast.LENGTH_LONG).show();
                            return;
                        default:
                            Toast.makeText(getActivity(), "Server error... Please try again later", Toast.LENGTH_LONG).show();
                            return;
                    }
                }
                if (response.body() == null) {
                    return;
                }
                CompanyModel companyModel = new CompanyModel();
                Log.i(TAG, "Got Profile of " + response.body().getTicker());
                CompanyProfile profile = response.body();
                companyModel.setTicker(profile.getTicker());
                companyModel.setName(profile.getName());
                companyModel.setLogoUrl(profile.getLogo());
                companyModel.setCurrency(profile.getCurrency());
                getCurrDiff(symbol, companyModel);
            }

            @Override
            public void onFailure(Call<CompanyProfile> call, Throwable t) {
                Log.i(TAG, t.getMessage());
            }
        });
    }

    void getCurrDiff(String symbol, CompanyModel companyModel) {
        Call<QuoteModel> call = apiHolder.getQuote(symbol);
        CompanyModel CM = companyModel;
        Log.i(TAG, "Trying to get Quote of " + symbol);
        call.enqueue(new Callback<QuoteModel>() {
            @Override
            public void onResponse(Call<QuoteModel> call, Response<QuoteModel> response) {
                if (!response.isSuccessful()) {
                    if (response.code() == 429)
                        Toast.makeText(getActivity(), "API limit reached. Please try again later", Toast.LENGTH_LONG).show();
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                CM.setCurrentPrice(response.body().getCurrent());
                CM.setDifferent(response.body().getDifferent());
                Log.i(TAG, "Got Quote of " + symbol);
                StockList.add(CM);
                stocksAdapter.notifyItemChanged(StockList.size() - 1);
            }

            @Override
            public void onFailure(Call<QuoteModel> call, Throwable t) {
                Log.i(TAG, t.getMessage());
            }

        });
    }
}
