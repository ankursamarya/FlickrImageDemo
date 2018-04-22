package com.example.flickerimages;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.flickerimages.entity.Image;
import com.example.flickerimages.imageSearch.ListAdapter;
import com.example.flickerimages.imageSearch.ImagesPresenter;
import com.example.flickerimages.imageSearch.PresenterContract;
import com.example.flickerimages.imageSearch.ViewContract;
import com.example.flickerimages.utils.EndlessRecyclerViewScrollListener;
import com.example.flickerimages.view.SpacesItemDecoration;
import com.example.flickerimages.viewImage.ViewImageActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.flickerimages.viewImage.ViewImageActivity.ARG_IMAGE_URL;

public class SearchImagesActivity extends AppCompatActivity implements ViewContract, View.OnClickListener {

    private PresenterContract presenter;

    private RecyclerView rvImages;
    private EditText searchTextView;
    private View progressBar;

    private ListAdapter adapter;
    private GridLayoutManager gridLayoutManager;

    private List<Image> images = new ArrayList<>();
    private EndlessRecyclerViewScrollListener scrollListener;

    private int columnCount = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_images);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.searchBtn).setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar);

        presenter = new ImagesPresenter(this);

        searchTextView = findViewById(R.id.searchImages);


        //for grid Layout
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), columnCount);

        rvImages = findViewById(R.id.rvImageList);
        rvImages.setLayoutManager(gridLayoutManager);
        rvImages.setItemAnimator(new DefaultItemAnimator());

        rvImages.addItemDecoration(new SpacesItemDecoration(10));

        adapter = new ListAdapter(this, images, this);
        rvImages.setAdapter(adapter);

        scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            //For pagination
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                presenter.loadMoreImages(searchTextView.getText().toString(), page + 1);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvImages.addOnScrollListener(scrollListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.grid_menu, menu);
        return true;
    }

    //change column from menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.col_2:
                columnCount = 2;
                gridLayoutManager.setSpanCount(columnCount);
                return true;
            case R.id.col_3:
                columnCount = 3;
                gridLayoutManager.setSpanCount(columnCount);
                return true;
            case R.id.col_4:
                columnCount = 4;
                gridLayoutManager.setSpanCount(columnCount);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // show result for first time so it clears adapter then notify adapter
    @Override
    public void showImages(List<Image> imageList) {
        images.clear();
        images.addAll(imageList);
        rvImages.getAdapter().notifyDataSetChanged();
        scrollListener.resetState();
    }

    //show result for pagination request
    @Override
    public void showMore(List<Image> imageList) {
        images.addAll(imageList);
        rvImages.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError() {
        Toast.makeText(this, R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void noMoreImage() {
        Toast.makeText(this, R.string.no_more_images, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.searchBtn:
                //search api request
                presenter.searchImagesResult(searchTextView.getText().toString());
                return;
        }
        //handle click from grid
        int position = (int) v.getTag(R.id.position);
        Intent intent = new Intent(SearchImagesActivity.this, ViewImageActivity.class);
        intent.putExtra(ARG_IMAGE_URL, images.get(position).getUrl());
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(SearchImagesActivity.this,
                        v,
                        ViewCompat.getTransitionName(v));
        startActivity(intent, options.toBundle());
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unbind();
    }

}
