package com.Test.test_app.Api.pojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchRequest {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("result")
    @Expose
    private List<SearchResultList> result = null;

    public Integer getCount() {
        return count;
    }

    public List<SearchResultList> getResult() {
        return result;
    }

    public void setResult(List<SearchResultList> result) {
        this.result = result;
    }
}
