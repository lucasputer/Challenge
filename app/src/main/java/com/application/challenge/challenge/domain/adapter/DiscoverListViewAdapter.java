package com.application.challenge.challenge.domain.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.cache.ImageLoader;
import com.application.challenge.challenge.domain.custom.CircularImageView;
import com.application.challenge.challenge.domain.custom.SquareImageView;
import com.application.challenge.challenge.domain.helper.ParseHelper;
import com.application.challenge.challenge.domain.model.ChallengeObject;
import com.application.challenge.challenge.domain.model.PhotoObject;
import com.application.challenge.challenge.main.profile.ProfileActivity;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by lucas on 16/1/15.
 */
public class DiscoverListViewAdapter extends ParseQueryAdapter<ParseUser> {

    ArrayList<SquareImageView> imageList = new ArrayList<SquareImageView>();

    ImageLoader imageLoader;


    public DiscoverListViewAdapter(Context context, QueryFactory<ParseUser>  queryFactory){
        super(context, queryFactory);
        imageLoader = new ImageLoader(context);
    }



    @Override
    public View getItemView(final ParseUser user, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.element_discover_row, null);
        }

        super.getItemView(user, v, parent);


        TextView username = (TextView) v.findViewById(R.id.discover_username);
        final CircularImageView thumbnailImageView = (CircularImageView) v.findViewById(R.id.circled_discover_profile_image);
        thumbnailImageView.setParseFile(null);

        imageList = new ArrayList<SquareImageView>();
        SquareImageView img1 = (SquareImageView) v.findViewById(R.id.discover_img1);
        SquareImageView img2 = (SquareImageView) v.findViewById(R.id.discover_img2);
        SquareImageView img3 = (SquareImageView) v.findViewById(R.id.discover_img3);

        img1.setParseFile(null);
        img2.setParseFile(null);
        img3.setParseFile(null);

        imageList.add(img1);
        imageList.add(img2);
        imageList.add(img3);

        if(user.get("displayName") != null){
            username.setText(user.get("displayName").toString());
        }else{
            username.setText(user.getUsername());
        }


        final ParseFile thumbnailFile = user.getParseFile("displayPictureThumbnail");
        if(thumbnailFile != null) {
            thumbnailImageView.setParseFile(thumbnailFile);
            thumbnailImageView.loadInBackground();
        }

        final Button follow = (Button) v.findViewById(R.id.btn_discover_follow);
        ParseHelper.enableButtonIfIsNotFollowing(user,follow);


            ParseQuery<PhotoObject> query = ParseHelper.getDiscoverUserPictures(user);
            query.findInBackground(new FindCallback<PhotoObject>() {
                @Override
                public void done(List<PhotoObject> photoObjects, ParseException e) {
                    if(e == null){
                        addAll(photoObjects, user);
                    }
                }
            });



        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseHelper.followUser(user,follow);
            }
        });

        username.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                EventBus.getDefault().postSticky(user);
                getContext().startActivity(intent);
            }
        });


        return v;
    }


    private void addAll(List<PhotoObject> photoObjects,ParseUser user){
        ArrayList<PhotoObject> photoArray = new ArrayList<PhotoObject>();
        photoArray.addAll(photoObjects);
        if(photoArray.size() > 0){
            int i = 0;
            while(i < photoArray.size()){
                if(photoArray.get(i).getUser().getObjectId().equals(user.getObjectId())) {
//                    imageList.get(i).setParseFile(photoArray.get(i).getParseFile("photo"));
//                    imageList.get(i).loadInBackground();
                    imageLoader.DisplayImage(photoArray.get(i).getParseFile("photo").getUrl(),imageList.get(i));
                }
                i++;
            }
        }
    }

}
