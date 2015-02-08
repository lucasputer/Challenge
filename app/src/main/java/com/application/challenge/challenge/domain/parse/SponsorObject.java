package com.application.challenge.challenge.domain.parse;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by lucas on 8/2/15.
 */
@ParseClassName("Sponsor")
public class SponsorObject extends ParseObject {

    public SponsorObject(){}

    public String getObjectId(){
        return getString("objectId");
    }

    public void setObjectId(String objectId){
        put("objectId",objectId);
    }

}
