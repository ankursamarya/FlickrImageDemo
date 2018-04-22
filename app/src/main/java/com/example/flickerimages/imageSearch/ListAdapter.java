package com.example.flickerimages.imageSearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flickerimages.R;
import com.example.flickerimages.entity.Image;


import java.util.ArrayList;
import java.util.List;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.CustomViewHolder> {


    private List<Image> images = new ArrayList<Image>();

    private final Context mContext;



    public ListAdapter(Context context, List<Image> photos) {
        this.mContext = context;
        this.images = photos;

    }

    @Override
    public ListAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.item, parent, false);
        return new CustomViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(ListAdapter.CustomViewHolder holder, int position) {
        Image image = images.get(position);
        Glide.with(mContext).load(image.getUrl())
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder  {

        ImageView image;

        public CustomViewHolder(View view) {
            super(view);

            image = (ImageView) view;//(ImageView) view.findViewById(R.id.image);

        }



    }
}