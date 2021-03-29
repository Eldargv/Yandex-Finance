package com.Test.test_app.Api.pojoModels;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuoteModel {
    @SerializedName("c")
    @Expose
    private Double current;
    @SerializedName("pc")
    @Expose
    private Double previous;

    public Double getCurrent() {
        return current;
    }

    public Double getDifferent() {
        return current - previous;
    }
}
