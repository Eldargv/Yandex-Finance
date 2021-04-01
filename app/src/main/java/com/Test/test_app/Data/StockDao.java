package com.Test.test_app.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.Test.test_app.RoomModels.StockRoom;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface StockDao {
//    @Query("SELECT * FROM StockRoom")
//    ArrayList<StockRoom> getAll();

    @Query("SELECT * FROM StockRoom")
    LiveData<List<StockRoom>> getAllLiveData();

    @Query("SELECT * FROM StockRoom WHERE Ticker LIKE :tick")
    StockRoom findByTicker(String tick);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(StockRoom stockRoom);

    @Delete
    void delete(StockRoom stockRoom);

    @Update
    void update(StockRoom stockRoom);

}
