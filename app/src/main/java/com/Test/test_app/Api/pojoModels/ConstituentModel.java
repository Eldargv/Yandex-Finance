package com.Test.test_app.Api.pojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConstituentModel {

    @SerializedName("constituents")
    @Expose
    private final List<String> constituents = null;

    public List<String> getConstituents() {
        return constituents;
    }
}
