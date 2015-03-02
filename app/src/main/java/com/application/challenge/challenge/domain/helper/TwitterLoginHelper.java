package com.application.challenge.challenge.domain.helper;

import com.application.challenge.challenge.domain.connector.FacebookGraphAPIConnector;
import com.application.challenge.challenge.domain.connector.TwitterAPIConnector;
import com.parse.ParseException;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;

/**
 * Created by lucas on 27/2/15.
 */
public class TwitterLoginHelper{

    private static TwitterLoginHelper instance;
    private static TwitterAPIConnector twitterAPIConnector;


    private TwitterLoginHelper(){
        twitterAPIConnector = new TwitterAPIConnector();
    }


    public static void initialize(){
        if(instance == null){
            instance = new TwitterLoginHelper();
        }
    }

    private static boolean hasAllTheDataNeeded(ParseUser user){
        boolean res = true;
        if(user.get("displayPicture") == null || user.get("displayPictureThumbnail") == null ||
                user.get("firstName") == null || user.get("lastName") == null){
            res = false;
        }
        return res;
    }

    public static void setMissingData(){
        if(!hasAllTheDataNeeded(ParseUser.getCurrentUser())){
            boolean profile = false, thumbnail = false, name = false;

            if(ParseUser.getCurrentUser().get("displayPicture") == null){
                profile = true;
            }
            if(ParseUser.getCurrentUser().get("displayPictureThumbnail") == null){
                thumbnail = true;
            }
            if(ParseUser.getCurrentUser().get("firstName") == null || ParseUser.getCurrentUser().get("lastName") == null){
                name = true;
            }

            twitterAPIConnector.setInformation(profile,thumbnail,name);

            ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                }
            });
        }
    }


}
