package com.application.challenge.challenge.domain.helper;

import com.application.challenge.challenge.domain.connector.FacebookGraphAPIConnector;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by lucas on 27/2/15.
 */
public class FacebookLoginHelper{

    private static FacebookLoginHelper instance;
    private static FacebookGraphAPIConnector facebookGraphAPIConnector;


    private FacebookLoginHelper(){
        facebookGraphAPIConnector = new FacebookGraphAPIConnector();
    }

    public static void initialize(){
        if(instance == null){
            instance = new FacebookLoginHelper();
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
            if(ParseUser.getCurrentUser().get("displayPicture") == null){
                setProfilePicture();
            }
            if(ParseUser.getCurrentUser().get("displayPictureThumbnail") == null){
                setThumbnail();
            }
            if(ParseUser.getCurrentUser().get("firstName") == null || ParseUser.getCurrentUser().get("lastName") == null){
                setFirstAndLastName();
            }

            ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                }
            });
        }
    }

    private static void setProfilePicture(){
        facebookGraphAPIConnector.setProfilePicture();
    }

    private static void setThumbnail(){
        facebookGraphAPIConnector.setThumbnailPicture();
    }

    private static void setFirstAndLastName(){

    }
}
