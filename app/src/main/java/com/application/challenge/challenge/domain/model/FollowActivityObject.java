package com.application.challenge.challenge.domain.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by lucas on 8/2/15.
 */
@ParseClassName("FollowActivity")
public class FollowActivityObject extends ParseObject {

    public FollowActivityObject(){}

    public ParseUser getFromUser(){
        return getParseUser("fromUser");
    }

    public void setFromUser(ParseUser user){
        put("fromUser", user);
    }

    public ParseUser getToUser(){
        return getParseUser("toUser");
    }

    public void setToUser(ParseUser user){
        put("toUser", user);
    }

}
