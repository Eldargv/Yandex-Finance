package com.Test.test_app;

import android.app.Application;

import com.Test.test_app.Api.ApiHolder;
import com.Test.test_app.Api.FinanceService;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class App extends Application {
    private FinanceService financeService;

    @Override
    public void onCreate() {
        super.onCreate();
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
    }

    public ApiHolder getApiHolder() {
        return financeService.getApiHolder();
    }
}
