package com.application.challenge.challenge.domain.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.custom.CircularImageView;
import com.application.challenge.challenge.domain.custom.SquareImageView;
import com.application.challenge.challenge.domain.helper.ParseHelper;
import com.application.challenge.challenge.domain.model.ChallengeObject;
import com.application.challenge.challenge.domain.model.PhotoObject;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by lucas on 16/1/15.
 */
public class DiscoverListViewAdapter extends ParseQueryAdapter<ParseUser> {


    public DiscoverListViewAdapter(Context context, ParseQueryAdapter.QueryFactory<ParseUser>  queryFactory){
        super(context, queryFactory);
    }



    @Override
    public View getItemView(ParseUser user, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.element_discover_row, null);
        }

        super.getItemView(user, v, parent);

        TextView username = (TextView) v.findViewById(R.id.discover_username);
        CircularImageView thumbnailImageView = (CircularImageView) v.findViewById(R.id.circled_discover_profile_image);

        ArrayList<SquareImageView> imageList = new ArrayList<SquareImageView>();
        SquareImageView img1 = (SquareImageView) v.findViewById(R.id.discover_img1);
        SquareImageView img2 = (SquareImageView) v.findViewById(R.id.discover_img2);
        SquareImageView img3 = (SquareImageView) v.findViewById(R.id.discover_img3);
        imageList.add(img1);
        imageList.add(img2);
        imageList.add(img3);

        username.setText(user.getUsername());

        ParseFile thumbnailFile = user.getParseFile("displayPictureThumbnail");
        if(thumbnailFile != null) {
            thumbnailImageView.setParseFile(thumbnailFile);
            thumbnailImageView.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    // nothing to do
                }
            });
        }

            ArrayList<PhotoObject> photoArray = new ArrayList<PhotoObject>();

            try {
                ParseQuery<PhotoObject> query = new ParseHelper().getDiscoverUserPictures(user);
                photoArray.addAll(query.find());
            }catch (ParseException e){
                e.printStackTrace();
            }
        if(photoArray.size() > 0){
            int i = 0;
            while(i < photoArray.size()){
                imageList.get(i).setParseFile(photoArray.get(i).getParseFile("photo"));
                imageList.get(i).loadInBackground(new GetDataCallback() {
                    @Override
                    public void done(byte[] data, ParseException e) {
                        // nothing to do
                    }
                });
            }
        }

        return v;
    }


}
