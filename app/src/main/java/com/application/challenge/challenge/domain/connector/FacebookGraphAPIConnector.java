package com.application.challenge.challenge.domain.connector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by lucas on 27/2/15.
 */
public class FacebookGraphAPIConnector {

    private static final String FACEBOOK_GRAPH_BASE_URL = "http://graph.facebook.com/";
    private static final String PROFILE_PICTURE_LARGE = "/picture?type=large";
    private static final String DISPLAY_PICTURE = "displayPicture";
    private static final String THUMBNAIL_PICTURE = "displayPictureThumbnail";
    private static final String PROFILE_PICTURE_SQUARE = "/picture?type=square";


    public FacebookGraphAPIConnector(){

    }

    private void requestPicture(final String url, final String field,final GraphUser user){

        AsyncTask<Void, Void, Bitmap> t = new AsyncTask<Void, Void, Bitmap>() {
            protected Bitmap doInBackground(Void... p) {
                Bitmap bm = null;
                try {
                    URL aURL = new URL(FACEBOOK_GRAPH_BASE_URL + user.getId() + url);
                    bm  = BitmapFactory.decodeStream(aURL.openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bm;
            }

            protected void onPostExecute(Bitmap bm) {
                if(bm != null){
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                    byte[] scaledData = bos.toByteArray();
                    ParseUser.getCurrentUser().put(field,new ParseFile(field+"_"+ParseUser.getCurrentUser().getUsername()+".jpg",scaledData));

                    ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                        }
                    });
                }
            }
        };
        t.execute();
    }

    public void setInformation(final boolean firstAndLastName, final boolean displayName, final boolean profilePicture, final boolean thumbnail){
        Request request = Request.newMeRequest(ParseFacebookUtils.getSession(),
                new Request.GraphUserCallback() {
                    @Override
                    public void onCompleted(final GraphUser user, Response response) {
                        if(user != null){
                            if(firstAndLastName){
                                ParseUser.getCurrentUser().put("firstName",user.getFirstName());
                                ParseUser.getCurrentUser().put("lastName",user.getLastName());
                            }
                            if(displayName){
                                ParseUser.getCurrentUser().put("displayName",user.getFirstName().toLowerCase()  + user.getLastName().toLowerCase());
                            }
                            if(profilePicture){
                                requestPicture(PROFILE_PICTURE_LARGE,DISPLAY_PICTURE,user);
                            }
                            if(thumbnail){
                                requestPicture(PROFILE_PICTURE_SQUARE,THUMBNAIL_PICTURE,user);
                            }

                            ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                }
                            });
                        } else if (response.getError() != null) {
                            // handle error
                        }
                    }
                });
        request.executeAsync();
    }
}
