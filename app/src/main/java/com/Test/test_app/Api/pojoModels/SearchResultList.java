package com.Test.test_app.Api.pojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchResultList {

    @SerializedName("symbol")
    @Expose
    private String symbol;

    public String getSymbol() {
        return symbol;
    }
}