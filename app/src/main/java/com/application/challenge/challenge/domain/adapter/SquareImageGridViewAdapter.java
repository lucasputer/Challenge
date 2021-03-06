package com.application.challenge.challenge.domain.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.application.challenge.challenge.domain.cache.ImageLoader;
import com.application.challenge.challenge.domain.custom.SquareImageView;
import com.application.challenge.challenge.domain.model.PhotoObject;

import java.util.ArrayList;

/**
 * Created by lucas on 15/1/15.
 */
public class SquareImageGridViewAdapter extends BaseAdapter {
    private final Context context;
    private ArrayList<PhotoObject> photos = new ArrayList<PhotoObject>();
    private ImageLoader imageLoader;

    public SquareImageGridViewAdapter(Context context,  ArrayList<PhotoObject> photos) {
        this.context = context;
        this.photos.addAll(photos);
        this.imageLoader = new ImageLoader(context);
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        SquareImageView view = (SquareImageView) convertView;
        if (view == null) {
            view = new SquareImageView(context);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        PhotoObject ph = getItem(position);

        imageLoader.DisplayImage(ph.getPhoto().getUrl(),view);

//        view.setParseFile(ph.getPhoto());
//        view.loadInBackground();

        return view;
    }




    @Override public int getCount() {
        return photos.size();
    }

    @Override public PhotoObject getItem(int position) {
        return photos.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }
}

