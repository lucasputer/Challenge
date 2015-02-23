package com.application.challenge.challenge.domain.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by lucas on 8/2/15.
 */
@ParseClassName("Challenge")
public class UserObject extends ParseUser {

    public UserObject(){}

    public String getUsername(){
        return getString("username");
    }

    public void setUsername(String username){
        put("username",username);
    }

}
