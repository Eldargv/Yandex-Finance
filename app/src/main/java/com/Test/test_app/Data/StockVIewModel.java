package com.Test.test_app.Data;

import android.graphics.Bitmap;
import android.media.tv.TvContract;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.Test.test_app.Api.ApiHolder;
import com.Test.test_app.Api.pojoModels.CompanyProfile;
import com.Test.test_app.Api.pojoModels.ConstituentModel;
import com.Test.test_app.Api.pojoModels.QuoteModel;
import com.Test.test_app.Api.pojoModels.SearchList;
import com.Test.test_app.Api.pojoModels.SearchResultList;
import com.Test.test_app.App;
import com.Test.test_app.RoomModels.StockRoom;
import com.Test.test_app.Stock;
import com.bumptech.glide.Glide;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockVIewModel extends ViewModel {

    Uri.Builder builder = new Uri.Builder();
    private final String TAG = "TAG";
    private final ApiHolder apiHolder = App.getInstance().getApiHolder();
    private final ImageLoader imageLoader = ImageLoader.getInstance();

    private MutableLiveData<Stock> searchStock = new MutableLiveData<Stock>();
    private MutableLiveData<Stock> defaultStock = new MutableLiveData<Stock>();
    private LiveData<List<StockRoom>> FavoriteList = App.getInstance().getStockDao().getAllLiveData();

    private MutableLiveData<List<Stock>> defaultStockList = new MutableLiveData<List<Stock>>();

    public LiveData<Stock> fetchSearchStock() {
        return searchStock;
    }

    public LiveData<Stock> fetchDefaultStock() {
        return defaultStock;
    }

    public void getStocks(String index) {
        Call<ConstituentModel> call = apiHolder.getConstituents(index);
        call.enqueue(new Callback<ConstituentModel>() {
            @Override
            public void onResponse(Call<ConstituentModel> call, Response<ConstituentModel> response) {
                if (!response.isSuccessful()) {
                    // Toast.makeText(getActivity(), "API limit reached. Please try again later", Toast.LENGTH_LONG).show();
                    return;
                }
                List<String> constituents = response.body().getConstituents();
                for (int i = 0; i < Math.min(constituents.size(), 15); i++) {
                    getProfile(constituents.get(i), defaultStock);
                }
            }

            @Override
            public void onFailure(Call<ConstituentModel> call, Throwable t) {
                Log.i(TAG, t.getMessage());
            }
        });
    }

    public void SearchQuery(String query) {
        StockRunnable stockRunnable = new StockRunnable(query);
        new Thread(stockRunnable).start();
    }

    class StockRunnable implements Runnable {

        String query;

        public StockRunnable(String query) {
            this.query = query;
        }

        @Override
        public void run() {
            Call<SearchList> call = apiHolder.getSearchList(query);
            try {
                Response<SearchList> response = call.execute();
                if (response.code() == 429) {
                    //Toast.makeText(getActivity(), "API limit reached. Please try again later", Toast.LENGTH_LONG).show();
                }
                if (response.body().getCount() == 0) {
                    //Toast.makeText(getActivity(), "Nothing", Toast.LENGTH_LONG).show();
                    return;
                }
                HashMap<String, Integer> count = new HashMap<String, Integer>();
                List<SearchResultList> result = response.body().getResult();
                for (int i = 0; i < response.body().getCount(); i++) {
                    SearchResultList res = result.get(i);
                    if (res.getType().equals("Common Stock")) {
                        Log.i(TAG, "Trying to get Profile of " + res.getSymbol());
                        getProfile(res.getSymbol(), searchStock);
                        count.put(res.getDescription(), 1);
                        Thread.sleep(100);
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void getProfile(String symbol, MutableLiveData<Stock> liveStock) {
        Call<CompanyProfile> call = apiHolder.getProfile(symbol);
        call.enqueue(new Callback<CompanyProfile>() {
            @Override
            public void onResponse(Call<CompanyProfile> call, Response<CompanyProfile> response) {
                if (!response.isSuccessful()) {
                    switch (response.code()) {
//                        case 403:
//                            Toast.makeText(, "Acces error", Toast.LENGTH_LONG).show();
//                            return;
//                        case 429:
//                            Toast.makeText(getActivity(), "API limit reached. Please try again later", Toast.LENGTH_LONG).show();
//                            return;
//                        default:
//                            Toast.makeText(getActivity(), "Server error... Please try again later", Toast.LENGTH_LONG).show();
//                            return;
                    }
                }
                if (response.body() == null) {
                    return;
                }
                Stock stock = new Stock();
                Log.i(TAG, "Got Profile of " + response.body().getTicker());
                CompanyProfile profile = response.body();
                stock.setTicker(profile.getTicker());
                stock.setName(profile.getName());
                stock.setCurrency(profile.getCurrency());
                imageLoader.loadImage(GetURL(profile.getLogo()), new SimpleImageLoadingListener() {
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        stock.setLogo(loadedImage);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        // Logo.setImageResource(R.drawable.placeholder);
//                        imageLoader.cancelDisplayTask(Logo);
                        // imageLoader.stop();
                    }
                });
                getCurrDiff(symbol, stock, liveStock);
            }

            @Override
            public void onFailure(Call<CompanyProfile> call, Throwable t) {
                Log.i(TAG, t.getMessage());
            }
        });
    }

    void getCurrDiff(String symbol, Stock stock, MutableLiveData<Stock> liveStock) {
        Call<QuoteModel> call = apiHolder.getQuote(symbol);
        Stock CM = stock;
        Log.i(TAG, "Trying to get Quote of " + symbol);
        call.enqueue(new Callback<QuoteModel>() {
            @Override
            public void onResponse(Call<QuoteModel> call, Response<QuoteModel> response) {
                if (!response.isSuccessful()) {
                    if (response.code() == 429)
//                        Toast.makeText(getActivity(), "API limit reached. Please try again later", Toast.LENGTH_LONG).show();
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                CM.setCurrentPrice(response.body().getCurrent());
                CM.setDifferent(response.body().getDifferent());
                Log.i(TAG, "Got Quote of " + symbol);
                liveStock.setValue(CM);
            }

            @Override
            public void onFailure(Call<QuoteModel> call, Throwable t) {
                Log.i(TAG, t.getMessage());
            }

        });
    }

    String GetURL(String logoUrl) {
        if (logoUrl == null || logoUrl.isEmpty()) {
            return "";
        }
        String host = "";
        try {
            host = getUrlHost(logoUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return "";
        }
        builder.scheme("https")
                .authority("logo.clearbit.com")
                .appendPath(host)
                .build();
        return builder.toString();
    }

    String getUrlHost(String url) throws URISyntaxException {
        URI uri = new URI(url);
        return uri.getHost();
    }
}
