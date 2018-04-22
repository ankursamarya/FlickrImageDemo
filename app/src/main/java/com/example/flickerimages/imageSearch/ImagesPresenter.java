package com.example.flickerimages.imageSearch;

import android.util.Log;

import com.example.flickerimages.entity.Data;

import rx.Subscriber;


public class ImagesPresenter implements PresenterContract {
    public static final String TAG = "ImagesPresenter";

    private ViewContract view;
    private InteractorContract interactor;

    public ImagesPresenter(ViewContract viewContract) {
        this.view = viewContract;
        interactor = new ImageInteractor();
    }

    @Override
    public void searchImagesResult(String text) {

        view.showProgress(true);
        interactor.searchPic(text, 1, new Subscriber<Data>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
                e.printStackTrace();
                view.showError();
                view.showProgress(false);
            }

            @Override
            public void onNext(Data userData) {
                Log.d(TAG, "onNext: " + userData);

                view.showProgress(false);
                if (userData.photos.photo.size() == 0) {
                    view.noMoreImage();
                    return;
                }
                view.showImages(userData.photos.photo);
            }
        });

    }

    @Override
    public void loadMoreImages(String text, int page) {

        interactor.searchPic(text, page, new Subscriber<Data>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
                e.printStackTrace();
                view.showError();
            }

            @Override
            public void onNext(Data userData) {
                Log.d(TAG, "onNext: " + userData);
                if (userData.photos.photo.size() == 0) {
                    view.noMoreImage();
                    return;
                }
                view.showMore(userData.photos.photo);
            }
        });

    }

    @Override
    public void unbind() {
        interactor.unbind();
    }

}
