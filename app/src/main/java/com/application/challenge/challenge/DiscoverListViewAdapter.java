package com.application.challenge.challenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.challenge.challenge.domain.ChallengesItem;
import com.application.challenge.challenge.domain.CircularImageView;
import com.application.challenge.challenge.domain.DiscoverItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by lucas on 16/1/15.
 */
public class DiscoverListViewAdapter extends ArrayAdapter<DiscoverItem> {

    private final Context context;
    private ArrayList<DiscoverItem> discoverList;

    public DiscoverListViewAdapter(Context context, ArrayList<DiscoverItem> discoverList){
        super(context, R.layout.element_discover_row,discoverList);
        this.context = context;
        this.discoverList = new ArrayList<DiscoverItem>();
        this.discoverList.addAll(discoverList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.element_discover_row, parent, false);
        TextView username = (TextView) rowView.findViewById(R.id.discover_username);
        CircularImageView imageView = (CircularImageView) rowView.findViewById(R.id.circled_discover_profile_image);

        ImageView img1 = (ImageView) rowView.findViewById(R.id.discover_img1);
        ImageView img2 = (ImageView) rowView.findViewById(R.id.discover_img2);
        ImageView img3 = (ImageView) rowView.findViewById(R.id.discover_img3);


        imageView.setBorderWidth(0);

        username.setText(discoverList.get(position).getUsername());

        Picasso.with(context) //
                .load(discoverList.get(position).getProfilePicture()) //
                .fit() //
                .tag(context) //
                .into(imageView);

        Picasso.with(context) //
                .load(discoverList.get(position).getImg1()) ////
                .tag(context) //
                .into(img1);

        Picasso.with(context) //
                .load(discoverList.get(position).getImg2()) //
                .tag(context) //
                .into(img2);

        Picasso.with(context) //
                .load(discoverList.get(position).getImg3()) //
                .tag(context) //
                .into(img3);

        return rowView;
    }


}
