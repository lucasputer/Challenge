package com.application.challenge.challenge.domain.helper;

import com.application.challenge.challenge.domain.connector.FacebookGraphAPIConnector;
import com.facebook.android.Facebook;
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
                user.get("firstName") == null || user.get("lastName") == null || user.get("displayName") == null){
            res = false;
        }
        return res;
    }


    public static void setMissingData() {
        if (!hasAllTheDataNeeded(ParseUser.getCurrentUser())) {
            boolean firstAndLastName = false, profilePicture = false, thumbnail = false, displayName = false;
            if (ParseUser.getCurrentUser().get("displayPicture") == null) {
                profilePicture = true;
            }
            if (ParseUser.getCurrentUser().get("displayPictureThumbnail") == null) {
               thumbnail = true;
            }

            if (ParseUser.getCurrentUser().get("firstName") == null || ParseUser.getCurrentUser().get("lastName") == null) {
                firstAndLastName = true;
            }

            if (ParseUser.getCurrentUser().get("displayName") == null) {
                displayName = true;
            }

            if(displayName || firstAndLastName || profilePicture || thumbnail){
                facebookGraphAPIConnector.setInformation(firstAndLastName,displayName,profilePicture,thumbnail);
            }

        }
    }

}
