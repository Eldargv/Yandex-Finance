package com.Test.test_app.Api;

import com.Test.test_app.Api.pojoModels.CompanyProfile;
import com.Test.test_app.Api.pojoModels.ConstituentModel;
import com.Test.test_app.Api.pojoModels.QuoteModel;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiHolder {

    @GET("index/constituents")
    Call<ConstituentModel> getConstituents(@Query("symbol") String symbol);

    @GET("stock/profile2")
    Call<CompanyProfile> getProfile(@Query("symbol") String symbol);

    @GET("quote")
    Call<QuoteModel> getQuote(@Query("symbol") String symbol);

    @GET("search")
    Call<JSONObject> getJsonRequest(@Query("q") String q);
}
