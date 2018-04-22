package com.example.flickerimages.imageSearch;

import com.example.flickerimages.entity.Image;

import java.util.List;


public interface ViewContract {

    void showImages(List<Image> images);

    void showError();

    void showMore(List<Image> images);

    void showProgress(boolean show);

    void noMoreImage();

}
