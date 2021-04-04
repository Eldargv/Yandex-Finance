package com.Test.test_app.Data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.Test.test_app.Stock;

@Database(entities =  {Stock.class}, version = 1, exportSchema = false)
public abstract class DataBase extends RoomDatabase {
    public abstract StockDao stockDao();
}
