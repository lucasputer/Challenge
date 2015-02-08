package com.application.challenge.challenge.domain.parse;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by lucas on 8/2/15.
 */
@ParseClassName("Challenge")
public class ChallengeObject extends ParseObject {

    public ChallengeObject(){}

    public String getObjectId(){
        return getString("objectId");
    }

    public void setObjectId(String objectId){
        put("objectId",objectId);
    }

    public ParseObject getFirstPhoto(){
        return getParseObject("firstPhoto");
    }

    public void setFirstPhoto(ParseFile file){
        put("firstPhoto",file);
    }

    public String getName(){
        return getString("name");
    }

    public String setName(String name){
        put("name",name);
    }

    public ParseObject getSponsor(){
        return getParseObject("sponsor");
    }

    public String setSponsor(ParseObject sponsor){
        put("sponsor",sponsor);
    }



}
