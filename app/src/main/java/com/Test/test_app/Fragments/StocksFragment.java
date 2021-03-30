package com.Test.test_app.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.Test.test_app.Api.FinanceService;
import com.Test.test_app.Api.pojoModels.CompanyProfile;
import com.Test.test_app.Api.pojoModels.ConstituentModel;
import com.Test.test_app.Api.pojoModels.QuoteModel;
import com.Test.test_app.App;
import com.Test.test_app.CompanyModel;
import com.Test.test_app.MainActivity;
import com.Test.test_app.R;
import com.Test.test_app.StocksAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StocksFragment extends Fragment {

    private ApiHolder apiHolder;
    private StocksAdapter stocksAdapter;
    private ArrayList<CompanyModel> StockList = new ArrayList<>();
    private RecyclerView RvStocks;
    private final String TAG = "TAG";
    private StocksFragment context = this;

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

        stocksAdapter = new StocksAdapter(StockList);
        recyclerView.setAdapter(stocksAdapter);

        apiHolder = MainActivity.getApp().getApiHolder();

        // StartThread(view);

        getStocks();
    }


    public void StartThread(View view) {
        RequestThread requestThread = new RequestThread("^GSPC");
        new Thread(requestThread).start();
    }
    
    public class RequestThread implements Runnable {
        private String symbol;

        public RequestThread(String symbol) {
            this.symbol = symbol;
        }

        @Override
        public void run() {
            List<String> constituents = new ArrayList<String>();
            Call<ConstituentModel> call = apiHolder.getConstituents(symbol);
            Log.i(TAG, "Trying to get constituents");
            try {
                constituents = call.execute().body().getConstituents();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "Got constituents");

            for (int i = 0; i < Math.min(constituents.size(), 15); i++) {
                CompanyModel model = new CompanyModel();
                Log.i(TAG, "Trying to get Profile of " + constituents.get(i));
                Call<CompanyProfile> companyProfileCall = apiHolder.getProfile(constituents.get(i));
                try {
                    CompanyProfile profile = companyProfileCall.execute().body();
                    model.setTicker(profile.getTicker());
                    model.setName(profile.getName());
                    model.setLogoUrl(profile.getLogo());
                    Log.i(TAG, "Got Profile of " + profile.getTicker());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Call<QuoteModel> quoteModelCall = apiHolder.getQuote(constituents.get(i));
                Log.i(TAG, "Trying to get Quote of " + constituents.get(i));
                try {
                    QuoteModel quote = quoteModelCall.execute().body();
                    model.setCurrentPrice(quote.getCurrent());
                    model.setDifferent(quote.getDifferent());
                    Log.i(TAG, "Got Quote of " + model.getTicker());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("TAG", "Trying post to Main Activity");
                        StockList.add(model);
                        stocksAdapter.notifyItemChanged(StockList.size() - 1);
                    }
                });
            }/*
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    stocksAdapter.notifyDataSetChanged();
                }
            });*/
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    void getStocks() {
        Call<ConstituentModel> call = apiHolder.getConstituents("^GSPC");
        Log.i(TAG, "Trying to get constituents");
        call.enqueue(new Callback<ConstituentModel>() {
            @Override
            public void onResponse(Call<ConstituentModel> call, Response<ConstituentModel> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "API limit reached. Please try again later", Toast.LENGTH_LONG).show();
                    return;
                }
                List<String> constituents = response.body().getConstituents();
                Log.i(TAG, "Got constituents");
                for (int i = 0; i < Math.min(constituents.size(), 10); i++) {
                    getProfile(constituents.get(i));

                }
            }

            @Override
            public void onFailure(Call<ConstituentModel> call, Throwable t) {
                Log.i(TAG,t.getMessage());
            }
        });
    }

    void getProfile(String symbol) {
        Call<CompanyProfile> call = apiHolder.getProfile(symbol);
        Log.i(TAG, "Trying to get Profile of " + symbol);
        call.enqueue(new Callback<CompanyProfile>() {
            @Override
            public void onResponse(Call<CompanyProfile> call, Response<CompanyProfile> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "API limit reached. Please try again later", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getActivity(), "API limit reached. Please try again later", Toast.LENGTH_LONG).show();
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