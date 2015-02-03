package com.application.challenge.challenge.domain;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.application.challenge.challenge.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucas on 16/1/15.
 */
public class PictureListViewAdapter extends ArrayAdapter<String> {

    private final Context context;
    private List<String> urls = new ArrayList<String>();
    private String username;
    private String thumbnail;

    public PictureListViewAdapter(Context context, ArrayList<String> urls,String username, String thumbnail){
        super(context, R.layout.element_picture,urls);
        this.context = context;
        this.urls.addAll(urls);
        this.username = username;
        this.thumbnail = thumbnail;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.element_picture, parent, false);
        TextView user = (TextView) rowView.findViewById(R.id.picture_username);
        CircularImageView profilePictureThumbnail = (CircularImageView) rowView.findViewById(R.id.circled_picture_profile_image);
        SquareImageView picture = (SquareImageView) rowView.findViewById(R.id.picture_image);

        profilePictureThumbnail.setBorderWidth(0);

        user.setText(username);

        Picasso.with(context) //
                .load(thumbnail) //
                .fit() //
                .tag(context) //
                .into(profilePictureThumbnail);

        Picasso.with(context) //
                .load(urls.get(position)) //
                .tag(context) //
                .into(picture);

        return rowView;
    }


}
