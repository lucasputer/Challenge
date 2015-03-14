package com.application.challenge.challenge.domain.helper;

import android.content.Context;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.cache.ImageLoader;
import com.application.challenge.challenge.domain.custom.CircularImageView;
import com.application.challenge.challenge.domain.custom.SquareImageView;
import com.application.challenge.challenge.domain.model.ChallengeObject;
import com.application.challenge.challenge.domain.model.FollowActivityObject;
import com.application.challenge.challenge.domain.model.LikeObject;
import com.application.challenge.challenge.domain.model.PhotoObject;
import com.parse.CountCallback;
import com.parse.FindCallback;
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

    public static ParseHelper instance;

    private ParseHelper(){}

    public static void initialize(){
        if(instance == null){
            instance = new ParseHelper();
        }
    }

    public static ParseQueryAdapter.QueryFactory<ChallengeObject> getChallengeListQueryFactory(){


        ParseQueryAdapter.QueryFactory<ChallengeObject>  factory = new ParseQueryAdapter.QueryFactory<ChallengeObject>(){
            public ParseQuery<ChallengeObject> create(){
                ParseQuery query = new ParseQuery("Challenge");
                query.include("firstPhoto");

                return query;
            }
        };
        return factory;
    }

    public static ParseQuery<ChallengeObject> getChallengeListQuery(){

                ParseQuery<ChallengeObject> query = ParseQuery.getQuery(ChallengeObject.class);
                query.include("firstPhoto");
                query.include("objectId");
                query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);

                return query;
    }

    public static  ParseQueryAdapter.QueryFactory<PhotoObject> getPicturesFromUser(final ParseUser user){
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

    public static  ParseQueryAdapter.QueryFactory<PhotoObject> getPicturesFromChallengeFactory(final ChallengeObject challengeObject){
        ParseQueryAdapter.QueryFactory<PhotoObject>  factory = new ParseQueryAdapter.QueryFactory<PhotoObject>(){
            public ParseQuery<PhotoObject> create(){
                ParseQuery query = new ParseQuery("Photo");
                query.include("user");
                query.include("challenge");
                query.whereEqualTo("challenge",challengeObject);
                query.orderByDescending("createdAt");

                return query;
            }
        };
        return factory;
    }


    public static ParseQuery<PhotoObject> getPopularPictures(){
        ParseQuery<PhotoObject> query = new ParseQuery<PhotoObject>("Photo");
        query.setLimit(20);
        query.include("user");
        query.include("objectId");
        query.orderByDescending("createdAt");

        return query;
    }

    public static ParseQuery<PhotoObject> getFriendsPictures(){

        final ArrayList<FollowActivityObject> followActivityObjs = new ArrayList<>();
        final ArrayList<ParseUser> userObjs = new ArrayList<>();

        ParseQuery<FollowActivityObject> query = new ParseQuery<FollowActivityObject>("FollowActivity");
        query.include("toUser");
        query.whereEqualTo("fromUser",ParseUser.getCurrentUser());
        try {
            followActivityObjs.addAll(query.find());
            for(FollowActivityObject fao: followActivityObjs){
                userObjs.add(fao.getToUser());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        ParseQuery<PhotoObject> query2 = new ParseQuery<PhotoObject>("Photo");
        query2.setLimit(20);
        query2.include("user");
        query2.whereContainedIn("user",userObjs);
        query2.orderByDescending("createdAt");

        return query2;
    }

    public static ParseQueryAdapter.QueryFactory<ParseUser> getDiscoverUsers(){
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

    public static ParseQueryAdapter.QueryFactory<ParseUser> getUsersFromSearch(final String searchedText){
        ParseQueryAdapter.QueryFactory<ParseUser>  factory = new ParseQueryAdapter.QueryFactory<ParseUser>(){
            public ParseQuery<ParseUser> create(){
                ParseQuery query = ParseUser.getQuery();
                query.whereContains("displayName",searchedText);
                query.whereNotEqualTo("objectId",ParseUser.getCurrentUser().getObjectId());
                query.setLimit(20);
                return query;
            }
        };
        return factory;
    }


    public static ParseQuery<PhotoObject> getDiscoverUserPictures(ParseUser user){
        ParseQuery<PhotoObject> query = new ParseQuery<PhotoObject>("Photo");
        query.include("user");
        query.whereEqualTo("user",user);
        query.setLimit(3);
        query.orderByDescending("likes");

        return query;
    }

    public static ParseQuery<PhotoObject> getPicture(String objectId){
        ParseQuery<PhotoObject> query = new ParseQuery<PhotoObject>("Photo");
        query.include("user");

        query.whereEqualTo("objectId",objectId);
        query.setLimit(1);

        return query;
    }

    private static ParseQuery<LikeObject> getLikeObjectQueryForCurrentUser(PhotoObject photo){

        ParseQuery<LikeObject> query = new ParseQuery<LikeObject>("Like");
        query.include("user");
        query.include("photo");
        query.whereEqualTo("user",ParseUser.getCurrentUser());
        query.whereEqualTo("photo", photo);

        return query;
    }

    public static boolean getLikedState(PhotoObject photo){
        ParseQuery<LikeObject> query = getLikeObjectQueryForCurrentUser(photo);
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

    public static PhotoObject getPhotoObjectFromPF( PhotoObject ph){
        ParseQuery<PhotoObject> query = new ParseQuery<PhotoObject>("Photo");
        ParseFile pdfd = ph.getPhoto();
        query.whereEqualTo("objectId",ph.getObjectId());
        try {
            return query.getFirst();
        }catch(Exception e){
            e.printStackTrace();
        }
        return ph;
    }

    public static PhotoObject savePhoto(PhotoObject photo){
        try{
            photo.save();
        }catch(Exception e){
            e.printStackTrace();
        }
        return photo;
    }

    public static void loadPicture(Context context, TextView username, CircularImageView thumbnail,
                            SquareImageView picture, TextView likes, TextView subtitle, PhotoObject phObj) {

        ImageLoader imageLoader = new ImageLoader(context);
        try {
            if(phObj.getUser().fetchIfNeeded().get("displayName") != null){
                username.setText(phObj.getUser().fetchIfNeeded().get("displayName").toString());
            }else{
                username.setText(phObj.getUser().fetchIfNeeded().getUsername());
            }
            ParseFile thumbnailFile = phObj.getUser().getParseFile("displayPictureThumbnail");
            if (thumbnailFile != null) {
                imageLoader.DisplayImage(phObj.getUser().getParseFile("displayPictureThumbnail").getUrl(),thumbnail);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

            imageLoader.DisplayImage(phObj.getPhoto().getUrl(),picture);


            likes.setText(context.getString(R.string.likes_amount, phObj.getLikes()));
            if(phObj.getSubtitle() != null){
                subtitle.setText(phObj.getSubtitle());
            }
    }

    public static void likePicture(final Context context,final  PhotoObject photo,final TextView likes, final Button likeButton){

       ParseQuery<LikeObject> query = getLikeObjectQueryForCurrentUser(photo);

        final ArrayList<LikeObject> list = new ArrayList<>();

        try {
           query.getFirstInBackground(new GetCallback<LikeObject>() {
               @Override
               public void done(LikeObject likeObject, ParseException e) {
                   if (e == null) {
                       final boolean state = likeObject.getLiked();
                       likeObject.setLiked(!state);
                       likeObject.saveInBackground(new SaveCallback() {
                           @Override
                           public void done(ParseException e) {
                               if (e == null) {
                                   if (!state) {
                                       likeButton.setBackground(context.getResources().getDrawable(R.drawable.btn_picture_heart_liked));
                                       photo.likePicture();
                                   } else {
                                       likeButton.setBackground(context.getResources().getDrawable(R.drawable.btn_picture_heart));
                                       photo.dislikePicture();
                                   }
                                   photo.saveInBackground(new SaveCallback() {
                                       @Override
                                       public void done(ParseException e) {
                                           likes.setText(context.getString(R.string.likes_amount, photo.getLikes()));
                                       }
                                   });
                               }
                           }
                       });
                   } else {
                       try {
                           LikeObject like = new LikeObject();
                           like.setLiked(true);
                           like.setUser(ParseUser.getCurrentUser());
                           like.setPhoto(photo);
                           like.saveInBackground(new SaveCallback() {
                               @Override
                               public void done(ParseException e) {
                                   if (e == null) {
                                       likeButton.setBackground(context.getResources().getDrawable(R.drawable.btn_picture_heart_liked));
                                       photo.likePicture();
                                       photo.saveInBackground(new SaveCallback() {
                                           @Override
                                           public void done(ParseException e) {
                                               likes.setText(context.getString(R.string.likes_amount, photo.getLikes()));
                                           }
                                       });
                                   }
                               }
                           });
                       } catch (Exception ex) {
                           ex.printStackTrace();
                       }
                   }
               }
           });

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public static void enableButtonIfIsNotFollowing(ParseUser user, final Button button){
        ParseQuery<FollowActivityObject> query = new ParseQuery<FollowActivityObject>("FollowActivity");
        query.whereEqualTo("fromUser",ParseUser.getCurrentUser());
        query.whereEqualTo("toUser",user);


           query.getFirstInBackground(new GetCallback<FollowActivityObject>() {
               @Override
               public void done(FollowActivityObject followActivityObject, ParseException e) {
                   if(e!=null){

                       button.setVisibility(View.VISIBLE);
                   }
               }
           });

    }


    public static void followUser(final ParseUser userToFollow, final Button followButton){
        FollowActivityObject followActivityObject = new FollowActivityObject();

        followActivityObject.setFromUser(ParseUser.getCurrentUser());
        followActivityObject.setToUser(userToFollow);

        followActivityObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    followButton.setVisibility(View.GONE);

                    //userToFollow.put("followerCount",Integer.parseInt(userToFollow.get("followerCount").toString()) + 1);
                    ParseUser.getCurrentUser().put("followingCount",Integer.parseInt(ParseUser.getCurrentUser().get("followingCount").toString()) + 1);
                    ParseUser.getCurrentUser().saveEventually(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                        }
                    });

                }
            }
        });
    }

    public static void setPhotosCountForUser(final TextView challenges, final ParseUser user){
        ParseQuery<PhotoObject> query = new ParseQuery<PhotoObject>("Photo");
        query.whereEqualTo("user",user);

            query.countInBackground(new CountCallback() {
                @Override
                public void done(int i, ParseException e) {
                    if(e == null) {
                        challenges.setText(Integer.toString(i));
                    }
                }
            });

    }

    public static void setFollowersCountForUser(final TextView followers, final ParseUser user){
        ParseQuery<FollowActivityObject> query = new ParseQuery<FollowActivityObject>("FollowActivity");
        query.whereEqualTo("toUser",user);

        query.countInBackground(new CountCallback() {
            @Override
            public void done(int i, ParseException e) {
                if(e == null){
                    followers.setText(Integer.toString(i));
                }
            }
        });
    }

}
