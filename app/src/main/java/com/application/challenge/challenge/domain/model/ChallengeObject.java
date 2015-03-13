package com.application.challenge.challenge.domain.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by lucas on 8/2/15.
 */
@ParseClassName("Challenge")
public class ChallengeObject extends ParseObject {

    public ChallengeObject(){}

    public ParseObject getFirstPhoto(){
        return getParseObject("firstPhoto");
    }

    public void setFirstPhoto(ParseFile file){
        put("firstPhoto",file);
    }

    public String getName(){
        return getString("name");
    }

    public void setName(String name){
        put("name",name);
    }

    public String getDescription(){
        return getString("description");
    }

    public void setDescription(String desc){
        put("description",desc);
    }

    public ParseObject getSponsor(){
        return getParseObject("sponsor");
    }

    public void setSponsor(ParseObject sponsor){
        put("sponsor",sponsor);
    }



}
