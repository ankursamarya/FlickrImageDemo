package com.example.flickerimages.imageSearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.flickerimages.R;
import com.example.flickerimages.entity.Image;


import java.util.ArrayList;
import java.util.List;
/*
* Image list adapter show images in recylerview
* */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ImageViewHolder> {

    private List<Image> images = new ArrayList<Image>();
    private View.OnClickListener clickListener;

    private final Context mContext;

    public ListAdapter(Context context, List<Image> photos, View.OnClickListener clickListener) {
        this.mContext = context;
        this.images = photos;
        this.clickListener = clickListener;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        final View sView = mInflater.inflate(R.layout.item, parent, false);
        sView.setOnClickListener(clickListener);
        return new ImageViewHolder(sView);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Image image = images.get(position);
        Glide.with(mContext).load(image.getUrl())
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .into(holder.image);
        holder.itemView.setTag(R.id.position, position);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder  {

        ImageView image;

        public ImageViewHolder(View view) {
            super(view);

            image = (ImageView) view;

        }
    }
}