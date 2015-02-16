package com.application.challenge.challenge.domain.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.model.ChallengeObject;
import com.application.challenge.challenge.domain.model.PhotoObject;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

/**
 * Created by lucas on 16/1/15.
 */
public class PictureListViewAdapter extends ParseQueryAdapter<PhotoObject> {

    private Context cntxt;


    public PictureListViewAdapter(Context context, ParseQueryAdapter.QueryFactory<PhotoObject>  queryFactory){

        super(context,queryFactory);

        cntxt = context;

    }

    @Override
    public View getItemView(PhotoObject photoObj, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.element_picture, null);
        }

        super.getItemView(photoObj, v, parent);

        ParseImageView photoImageView = (ParseImageView) v.findViewById(R.id.picture_image);
        ParseImageView thumbnailImageView = (ParseImageView) v.findViewById(R.id.circled_picture_profile_image);
        ParseFile thumbnailFile = photoObj.getUser().getParseFile("displayPictureThumbnail");

        photoImageView.setParseFile(photoObj.getParseFile("photo"));
        photoImageView.loadInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] data, ParseException e) {
                // nothing to do
            }
        });

        if(thumbnailFile != null) {
            thumbnailImageView.setParseFile(thumbnailFile);
            thumbnailImageView.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    // nothing to do
                }
            });

        }



        TextView usernameTextView = (TextView) v.findViewById(R.id.picture_username);
        usernameTextView.setText(photoObj.getUser().getUsername());


        TextView pictureLikesAmount = (TextView) v.findViewById(R.id.picture_heart_amount);
        pictureLikesAmount.setText(cntxt.getString(R.string.likes_amount,photoObj.getLikes()));


        return v;
    }

}