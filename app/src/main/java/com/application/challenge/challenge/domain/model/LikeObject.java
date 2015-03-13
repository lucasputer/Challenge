package com.application.challenge.challenge.domain.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by lucas on 8/2/15.
 */
@ParseClassName("Like")
public class LikeObject extends ParseObject {

    public LikeObject(){}

    public boolean getLiked(){
        return getBoolean("liked");
    }

    public void setLiked(boolean liked){
        put("liked", liked);
    }

    public ParseObject getPhoto(){
        return getParseObject("photo");
    }

    public void setPhoto(ParseObject photo){
        put("photo", photo);
    }

    public ParseUser getUser(){
        return getParseUser("user");
    }

    public void setUser(ParseUser user){
        put("user", user);
    }

}
