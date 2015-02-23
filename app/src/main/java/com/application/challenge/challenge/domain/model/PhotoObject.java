package com.application.challenge.challenge.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by lucas on 8/2/15.
 */
@ParseClassName("Photo")
public class PhotoObject extends ParseObject{

    public PhotoObject(){
    }

    public String getObjectId(){
        return getString("objectId");
    }

    public void setObjectId(String objectId){
        put("objectId",objectId);
    }

    public ParseObject getChallenge(){
        return getParseObject("challenge");
    }

    public void setChallenge(ParseObject challenge){
        put("challenge", challenge);
    }

    public int getDislikes(){
        return getInt("dislikes");
    }

    public void dislikePicture(){
        increment("dislikes");
        put("overallLikes",getOverallLikes() -1);

    }

    public int getLikes(){
        return getInt("likes");
    }

    public void likePicture(){
        increment("likes");
        increment("overallLikes");

    }

    public int getOverallLikes(){
        return getInt("overallLikes");
    }

    public ParseFile getPhoto(){
        return getParseFile("photo");
    }

    public void setPhoto(ParseFile photo){
        put("photo", photo);
    }

    public ParseFile getThumbnail(){
        return getParseFile("thumbnail");
    }

    public void setThumbnail(ParseFile thumbnail){
        put("thumbnail", thumbnail);
    }

    public ParseUser getUser(){
        return getParseUser("user");
    }

    public void setUser(ParseUser user){
        put("user", user);
    }

    public String getSubtitle(){ return getString("subtitle");}

    public void setSubtitle(String subtitle){put("subtitle",subtitle);}

}
