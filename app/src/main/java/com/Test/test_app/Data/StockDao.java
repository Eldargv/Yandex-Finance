package com.Test.test_app.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.Test.test_app.Stock;

import java.util.List;

@Dao
public interface StockDao {

    @Query("SELECT * FROM stock_table")
    LiveData<List<Stock>> getFavoriteList();

    @Query("SELECT * FROM stock_table WHERE Ticker LIKE :tick")
    Stock findByTicker(String tick);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Stock stock);

    @Delete
    void delete(Stock stock);

    @Update
    void update(Stock stock);

}
