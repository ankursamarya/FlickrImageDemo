package com.example.flickerimages.imageSearch;

import rx.Observer;


public interface InteractorContract {

    void searchPic(String text, final int page, Observer observer);

    void unbind();
}
