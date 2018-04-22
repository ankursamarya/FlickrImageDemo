package com.example.flickerimages.imageSearch;


public interface PresenterContract {

    void searchImagesResult(String text);
    void loadMoreImages(String text, int page);
    void unbind();
}
