package com.Test.test_app.Data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.Test.test_app.Stock;

import java.util.ArrayList;
import java.util.List;

public class StockViewModel extends ViewModel {

    private StockRepository repository = new StockRepository();

    private MutableLiveData<List<Stock>> searchList = repository.getSearchList();
    private MutableLiveData<List<Stock>> defaultList = repository.getDefaultList();
    private LiveData<List<Stock>> FavoriteList = repository.getFavoriteList();
    private MutableLiveData<Stock> changedTicker = new MutableLiveData<Stock>();

    public MutableLiveData<Integer> getDefaultProcessCode() {
        return repository.getDefaultProcessCode();
    }

    public MutableLiveData<Integer> getSearchProcessCode() {
        return repository.getSearchProcessCode();
    }

    public LiveData<List<Stock>> fetchFavoriteList() {
        return FavoriteList;
    }

    public LiveData<List<Stock>> fetchSearchList() {
        return searchList;
    }

    public LiveData<List<Stock>> fetchDefaultList() {
        return defaultList;
    }

    public void insert(Stock stock) {
        repository.insert(stock);
        changedTicker.setValue(stock);
    }

    public void update(Stock stock) {
        repository.update(stock);
    }

    public void delete(Stock stock) {
        repository.delete(stock);
        changedTicker.setValue(stock);
    }

    public void getDefaultStocks(String index) {
        repository.getStocks(index);
    }

    public void getSearchStocks(String query) {
        if (query == null || query.equals("")) {
            return;
        }
        repository.SearchQuery(query);
    }

    public LiveData<Stock> getFavoriteChangedTicker() {
        return changedTicker;
    }

    public void ClearSearchList() {
        repository.InterruptSearch();
        searchList.setValue(new ArrayList<Stock>());
    }
}
