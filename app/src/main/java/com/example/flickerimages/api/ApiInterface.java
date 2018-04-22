package com.example.flickerimages.api;


import com.example.flickerimages.entity.Data;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;


public interface ApiInterface {

    @Headers({
            "Cache-max-age: 172800"
    })
    @GET("?method=flickr.photos.search&api_key=ab726e418794ee185dc80ea739c331e0&format=json&nojsoncallback=1&per_page=10")
    Observable<Data> getImagesData(@Query("text") String text, @Query("page") int page);
}
