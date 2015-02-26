package com.application.challenge.challenge.domain.helper;

import android.content.Context;
import android.provider.SyncStateContract;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.custom.CircularImageView;
import com.application.challenge.challenge.domain.custom.SquareImageView;
import com.application.challenge.challenge.domain.model.ChallengeObject;
import com.application.challenge.challenge.domain.model.FollowActivityObject;
import com.application.challenge.challenge.domain.model.LikeObject;
import com.application.challenge.challenge.domain.model.PhotoObject;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucas on 16/2/15.
 */
public class ParseHelper {

    public ParseHelper(){}

    public ParseQueryAdapter.QueryFactory<ChallengeObject> getChallengeListQuery(){


        ParseQueryAdapter.QueryFactory<ChallengeObject>  factory = new ParseQueryAdapter.QueryFactory<ChallengeObject>(){
            public ParseQuery<ChallengeObject> create(){
                ParseQuery query = new ParseQuery("Challenge");
                query.include("firstPhoto");

                return query;
            }
        };
        return factory;
    }

    public ParseQueryAdapter.QueryFactory<PhotoObject> getPicturesFromUser(final ParseUser user){
        ParseQueryAdapter.QueryFactory<PhotoObject>  factory = new ParseQueryAdapter.QueryFactory<PhotoObject>(){
            public ParseQuery<PhotoObject> create(){
                ParseQuery query = new ParseQuery("Photo");
                query.include("user");
                query.whereEqualTo("user",user);
                query.orderByDescending("createdAt");

                return query;
            }
        };
        return factory;
    }

    public ParseQuery<PhotoObject> getPopularPictures(){
        ParseQuery<PhotoObject> query = new ParseQuery<PhotoObject>("Photo");
        query.setLimit(20);
        query.include("user");
        query.include("objectId");
        query.orderByDescending("createdAt");

        return query;
    }

    public ParseQuery<PhotoObject> getFriendsPictures(final ParseUser user){
        return null;
    }

    public ParseQueryAdapter.QueryFactory<ParseUser> getDiscoverUsers(){
        ParseQueryAdapter.QueryFactory<ParseUser>  factory = new ParseQueryAdapter.QueryFactory<ParseUser>(){
            public ParseQuery<ParseUser> create(){
                ParseQuery query = ParseUser.getQuery();
                query.orderByDescending("followerCount");
                query.whereNotEqualTo("objectId",ParseUser.getCurrentUser().getObjectId());
                query.setLimit(20);
                return query;
            }
        };
        return factory;
    }


    public ParseQuery<PhotoObject> getDiscoverUserPictures(ParseUser user){
        ParseQuery<PhotoObject> query = new ParseQuery<PhotoObject>("Photo");
        query.include("user");
        query.whereEqualTo("user",user);
        query.setLimit(3);
        query.orderByDescending("likes");

        return query;
    }

    public ParseQuery<PhotoObject> getPicture(String objectId){
        ParseQuery<PhotoObject> query = new ParseQuery<PhotoObject>("Photo");
        query.include("user");

        query.whereEqualTo("objectId",objectId);
        query.setLimit(1);

        return query;
    }

    private ParseQuery<LikeObject> getLikeObjectQueryForCurrentUser(PhotoObject photo){

        ParseQuery<LikeObject> query = new ParseQuery<LikeObject>("Like");
        query.include("user");
        query.include("photo");
        query.whereEqualTo("user",ParseUser.getCurrentUser());
        query.whereEqualTo("photo", photo);

        return query;
    }

    public boolean getLikedState(PhotoObject photo){
        ParseQuery<LikeObject> query = this.getLikeObjectQueryForCurrentUser(photo);
        LikeObject obj = null;
        boolean state = false;
        try {
             obj = query.getFirst();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (obj != null){
            state = obj.getLiked();
        }
        return state;
    }

    public PhotoObject getPhotoObjectFromPF( PhotoObject ph){
        ParseQuery<PhotoObject> query = new ParseQuery<PhotoObject>("Photo");
        query.whereEqualTo("photo",ph.getPhoto());
        try {
            return query.getFirst();
        }catch(Exception e){
            e.printStackTrace();
        }
        return ph;
    }

    public PhotoObject savePhoto(PhotoObject photo){
        try{
            photo.save();
        }catch(Exception e){
            e.printStackTrace();
        }
        return photo;
    }

    public void loadPicture(Context context, TextView username, CircularImageView thumbnail,
                            SquareImageView picture, TextView likes, PhotoObject phObj) {

        username.setText(phObj.getUser().getUsername());
        ParseFile thumbnailFile = phObj.getUser().getParseFile("displayPictureThumbnail");
        if (thumbnailFile != null) {
            thumbnail.setParseFile(thumbnailFile);
            thumbnail.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    // nothing to do
                }
            });
        }

            picture.setParseFile(phObj.getPhoto());
            picture.loadInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    // nothing to do
                }
            });

            likes.setText(context.getString(R.string.likes_amount, phObj.getLikes()));
    }

    public void likePicture(final Context context,final  PhotoObject photo,final TextView likes, final Button likeButton){

       ParseQuery<LikeObject> query = this.getLikeObjectQueryForCurrentUser(photo);

        ArrayList<LikeObject> list = new ArrayList<>();

        try {
           list.add(query.getFirst());
        }catch(Exception e){
            e.printStackTrace();
        }


        if(list.size() == 0){
            final LikeObject like = new LikeObject();
            like.setLiked(true);
            like.setUser(ParseUser.getCurrentUser());
            //like.setPhoto(photo);
            photo.fetchIfNeededInBackground(new GetCallback<PhotoObject>() {
                @Override
                public void done(PhotoObject o, ParseException e) {
                    if (e == null) {
                        PhotoObject p = savePhoto(o);
                        like.setPhoto(p);
                        like.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e != null) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            });


        }else{
            LikeObject obj = list.get(0);
            final boolean state = obj.getLiked();
            obj.setLiked(!state);
            obj.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e == null){
                        if(!state){
                            likeButton.setBackground(context.getResources().getDrawable(R.drawable.btn_picture_heart_liked));
                        }else{
                            likeButton.setBackground(context.getResources().getDrawable(R.drawable.btn_picture_heart));
                        }
                    }
                }
            });
        }



        try {
            photo.likePicture();
            photo.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e == null){
                        likes.setText(context.getString(R.string.likes_amount, photo.getLikes()));
                        likeButton.setBackground(context.getResources().getDrawable(R.drawable.btn_picture_heart_liked));
                    }

                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean isFollowing(ParseUser user){
        ParseQuery<FollowActivityObject> query = new ParseQuery<FollowActivityObject>("FollowActivity");
        query.whereEqualTo("fromUser",ParseUser.getCurrentUser());
        query.whereEqualTo("toUser",user);

        boolean ret = false;
        try{
           FollowActivityObject fao = query.getFirst();
           ret = true;
        }catch(Exception e){
           ret = false;
        }
        return ret;
    }




}
