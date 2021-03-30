package com.Test.test_app.Api.pojoModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

import java.util.List;

public class SearchList {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("result")
    @Expose
    private List<SearchResultList> result;

    public Integer getCount() {
        return count;
    }

    public List<SearchResultList> getResult() {
        return result;
    }
}
