package com.Test.test_app.Fragments;

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
import com.Test.test_app.Api.pojoModels.ConstituentModel;
import com.Test.test_app.Api.pojoModels.QuoteModel;
import com.Test.test_app.CompanyModel;
import com.Test.test_app.MainActivity;
import com.Test.test_app.R;
import com.Test.test_app.Adapters.StocksAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StocksFragment extends Fragment implements StocksAdapter.OnStarListener {

    private ApiHolder apiHolder;
    private StocksAdapter stocksAdapter;
    private ArrayList<CompanyModel> StockList = new ArrayList<CompanyModel>();
    private final String TAG = "TAG";

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

        apiHolder = MainActivity.getApp().getApiHolder();

        getStocks();
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