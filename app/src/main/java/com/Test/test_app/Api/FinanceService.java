package com.Test.test_app.Api;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FinanceService {
    public static final String TOKEN = "c1ca1gf48v6scqmqp4v0";
    public static final String BASE_URL = "https://finnhub.io/api/v1/";

    ApiHolder apiHolder;

    public FinanceService() {
        Retrofit retrofit = createRetrofit();
        apiHolder = retrofit.create(ApiHolder.class);
    }

    private Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkhttpClient())
                .build();
    }

    private OkHttpClient createOkhttpClient() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                // Добавляем ключ ко всем запросам
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
                        final Request original = chain.request();
                        final HttpUrl originalHttpUrl = original.url();
                        final HttpUrl url = originalHttpUrl.newBuilder()
                                .addQueryParameter("token", TOKEN)
                                .build();
                        final Request.Builder requestBuilder = original.newBuilder()
                                .url(url);
                        final Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                })
                .addInterceptor(loggingInterceptor)
                .build();

    }

    public ApiHolder getApiHolder() {
        return apiHolder;
    }


}
