package com.Test.test_app.Api.pojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompanyProfile {

    @SerializedName("currency")
    @Expose
    private String currency;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("ticker")
    @Expose
    private String ticker;

    @SerializedName("weburl")
    @Expose
    private String logo;

    public String getCurrency() {
        return currency;
    }

    public String getName() {
        return name;
    }

    public String getTicker() {
        return ticker;
    }

    public String getLogo() {
        return logo;
    }
}
