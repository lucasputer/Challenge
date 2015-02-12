package com.application.challenge.challenge.domain;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.parse.PhotoObject;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lucas on 15/1/15.
 */
public class SquareImageGridViewAdapter extends BaseAdapter {
    private final Context context;
    private ArrayList<PhotoObject> photos = new ArrayList<PhotoObject>();

    public SquareImageGridViewAdapter(Context context,  ArrayList<PhotoObject> photos) {
        this.context = context;
        this.photos.addAll(photos);

    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        SquareImageView view = (SquareImageView) convertView;
        if (view == null) {
            view = new SquareImageView(context);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        // Get the image URL for the current position.
        PhotoObject ph = getItem(position);

        view.setParseFile(ph.getPhoto());
        view.loadInBackground();

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

