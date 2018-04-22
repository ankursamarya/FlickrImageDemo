package com.example.flickerimages.imageSearch;


import com.example.flickerimages.api.RetrofitInterface;
import com.example.flickerimages.entity.Data;
import com.example.flickerimages.utils.Util;

import rx.Observer;
import rx.Subscription;


public class ImageInteractor implements InteractorContract {

    private Subscription imageListSubscription;
    @Override
    public void searchPic(String text, final int page, Observer observer) {

        imageListSubscription = RetrofitInterface.getInstance().getApiInterface().getImagesData(text, page)
                .compose(Util.<Data>applySchedulers())
                .subscribe(observer);
    }

    @Override
    public void unbind() {
        if (imageListSubscription != null) {
            imageListSubscription.unsubscribe();
        }
    }
}
