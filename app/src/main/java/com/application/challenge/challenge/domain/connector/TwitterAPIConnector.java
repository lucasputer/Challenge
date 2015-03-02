package com.application.challenge.challenge.domain.connector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFile;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by lucas on 27/2/15.
 */
public class TwitterAPIConnector {

    private static final String TWITTER_URL = "https://api.twitter.com/1.1/users/show.json?screen_name=";



    public TwitterAPIConnector(){

    }


    public void setInformation(final boolean picture,final boolean thumbnail,final boolean name){



        AsyncTask<Void, Void, JSONObject> t = new AsyncTask<Void, Void, JSONObject>() {
            protected JSONObject doInBackground(Void... p) {
                    JSONObject json = null;
                    try {
                        HttpClient client = new DefaultHttpClient();
                        HttpGet verifyGet = new HttpGet(
                                TWITTER_URL + ParseTwitterUtils.getTwitter().getScreenName());
                        ParseTwitterUtils.getTwitter().signRequest(verifyGet);
                        HttpResponse response = client.execute(verifyGet);
                        HttpEntity entity = response.getEntity();
                        InputStream is = entity.getContent();
                        BufferedReader streamReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                        StringBuilder responseStrBuilder = new StringBuilder();

                        String inputStr;
                        while ((inputStr = streamReader.readLine()) != null)
                            responseStrBuilder.append(inputStr);
                        json = new JSONObject(responseStrBuilder.toString());
                    }catch (Exception e) {
                        e.printStackTrace();
                     }
                    return json;
                }

            protected void onPostExecute(JSONObject json){
                                    if(json != null){
                                        if(name){
                                            try {
                                                String username = json.getString("name");
                                                if(username != null){
                                                    String splitUsername[] = username.split(" ");
                                                    if(splitUsername.length > 1){
                                                        ParseUser.getCurrentUser().put("firstName",splitUsername[0]);
                                                        ParseUser.getCurrentUser().put("lastName",splitUsername[1]);
                                                    }else{
                                                        ParseUser.getCurrentUser().put("firstName",username);
                                                        ParseUser.getCurrentUser().put("lastName","");
                                                    }
                                                }

                                                ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                                                    @Override
                                                    public void done(ParseException e) {

                                                    }
                                                });

                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }

                                        if(picture || thumbnail){
                                            try {
                                                setPictures(json.getString("profile_image_url"),picture, thumbnail);
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }

                                    }
                                }


        };
        t.execute();
    }


    private void setPictures(final String url,final boolean picture, final boolean thumbnail){
        AsyncTask<Void, Void, Bitmap> t = new AsyncTask<Void, Void, Bitmap>() {
            protected Bitmap doInBackground(Void... p) {
                Bitmap bm = null;
                try {
                    URL aURL = new URL(url);
                    bm  = BitmapFactory.decodeStream(aURL.openConnection().getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bm;
            }

            protected void onPostExecute(Bitmap bm){
                if(bm != null){
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                    byte[] scaledData = bos.toByteArray();
                    if(picture){
                        ParseUser.getCurrentUser().put("displayPicture",new ParseFile("displayPicture_"+ParseUser.getCurrentUser().getUsername()+".jpg",scaledData));
                    }
                    if(thumbnail){
                        ParseUser.getCurrentUser().put("displayPictureThumbnail",new ParseFile("displayPictureThumbnail_"+ParseUser.getCurrentUser().getUsername()+".jpg",scaledData));
                    }
                }

                ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                    }
                });
            }


        };
        t.execute();
    }
}
