package com.application.challenge.challenge.domain.helper;

import com.application.challenge.challenge.domain.model.ChallengeObject;
import com.application.challenge.challenge.domain.model.PhotoObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

/**
 * Created by lucas on 16/2/15.
 */
public class ParseHelper {

    public ParseHelper(){}

    public ParseQueryAdapter.QueryFactory<ChallengeObject> getChallengeListQuery(){


        ParseQueryAdapter.QueryFactory<ChallengeObject>  factory = new ParseQueryAdapter.QueryFactory<ChallengeObject>(){
            public ParseQuery<ChallengeObject> create(){
                ParseQuery query = new ParseQuery("Challenge");
                query.include("firstPhoto");

                return query;
            }
        };
        return factory;
    }

    public ParseQueryAdapter.QueryFactory<PhotoObject> getPicturesFromUser(final ParseUser user){
        ParseQueryAdapter.QueryFactory<PhotoObject>  factory = new ParseQueryAdapter.QueryFactory<PhotoObject>(){
            public ParseQuery<PhotoObject> create(){
                ParseQuery query = new ParseQuery("Photo");
                query.include("user");
                query.whereEqualTo("user",user);
                query.orderByDescending("createdAt");

                return query;
            }
        };
        return factory;
    }

    public ParseQuery<PhotoObject> getPopularPictures(){
        ParseQuery<PhotoObject> query = new ParseQuery<PhotoObject>("Photo");
        query.setLimit(20);
        query.orderByDescending("createdAt");

        return query;
    }

    public ParseQuery<PhotoObject> getFriendsPictures(final ParseUser user){
        return null;
    }

}
