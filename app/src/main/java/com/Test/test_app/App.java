package com.Test.test_app;

import android.app.Application;

import androidx.room.Room;

import com.Test.test_app.Api.ApiHolder;
import com.Test.test_app.Api.FinanceService;
import com.Test.test_app.Data.DataBase;
import com.Test.test_app.Data.StockDao;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class App extends Application {

    private FinanceService financeService;
    private DataBase dataBase;
    private StockDao stockDao;
    private static App app;

    public static App getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        financeService = new FinanceService();

        ImageLoader imageLoader;
        DisplayImageOptions defaultoptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheSize(2 * 1024 * 1024)
                .defaultDisplayImageOptions(defaultoptions)
                .build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);

        dataBase = Room.databaseBuilder(getApplicationContext(),
                DataBase.class, "stock_database")
                .fallbackToDestructiveMigration()
                .build();
        stockDao = dataBase.stockDao();
    }

    public StockDao getStockDao() {
        return stockDao;
    }

    public ApiHolder getApiHolder() {
        return financeService.getApiHolder();
    }
}
