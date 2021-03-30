package com.Test.test_app.Api.pojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchResultList {

    @SerializedName("symbol")
    @Expose
    private String symbol;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("type")
    @Expose
    private String type;

    public String getSymbol() {
        return symbol;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }
}