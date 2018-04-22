package com.example.flickerimages.api;


import android.util.Log;

import com.example.flickerimages.BuildConfig;
import com.example.flickerimages.FlickrApplication;
import com.example.flickerimages.utils.NetworkUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitInterface {
    public static final String HOST = "https://api.flickr.com/services/rest/";
    public static final String NO_CACHE = "no-cache";
    private static long SIZE_OF_CACHE = 20 * 1024 * 1024; // 20 MB

    private static final OkHttpInterceptor okHttpInterceptor = new OkHttpInterceptor();



    private static RetrofitInterface INSTANCE = null;

    private ApiInterface apiInterface;

    private RetrofitInterface() {

    }

    public static RetrofitInterface getInstance() {
        if (INSTANCE == null) {
            synchronized (RetrofitInterface.class) {
                if (INSTANCE == null) {
                    return new RetrofitInterface();
                }
            }
        }

        return INSTANCE;

    }

    public ApiInterface getApiInterface() {
        if (apiInterface == null) {
            return apiInterface = create();
        }

        return apiInterface;
    }

    private ApiInterface create() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(buildClient())
                .build();
        return retrofit.create(ApiInterface.class);
    }


    public static OkHttpClient buildClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // Create Cache
        Cache cache = null;
        try {
            cache = new Cache(new File(FlickrApplication.getInstance().getCacheDir(), "http"), SIZE_OF_CACHE);
        } catch (Exception e) {
            Log.e("tag", "Could not create Cache!", e);
        }

        // Create OkHttpClient
        builder.networkInterceptors().add(okHttpInterceptor);
        builder.cache(cache);



        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.interceptors().add(loggingInterceptor);

        return builder.build();
    }


    public static class OkHttpInterceptor implements okhttp3.Interceptor {


        public OkHttpInterceptor() {

        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            String maxAge = originalRequest.header("Cache-max-age");
            Request.Builder builder = originalRequest.newBuilder();


            if (originalRequest.method().equals("GET")) {

                if (maxAge != null && !NO_CACHE.equals(originalRequest.header("Cache-Control"))) {
                    builder.header("Cache-Control", "public, max-age=" + maxAge);
                }
            }

            if (!NetworkUtils.isConnected(FlickrApplication.getInstance())) {
                builder.header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7);
            }
            originalRequest = builder.build();
            Response response = chain.proceed(originalRequest);

            boolean isPublic = response.cacheControl().isPublic();
            if (maxAge != null && !isPublic) {
                if (BuildConfig.DEBUG)
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", String.format("max-age=%s, only-if-cached, max-stale=%d", maxAge, 86400))
                        .build();
            }
            return response;
        }
    }

}
