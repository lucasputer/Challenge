package com.application.challenge.challenge.main.commons.application;

import android.app.Application;

import com.application.challenge.challenge.R;
import com.application.challenge.challenge.domain.helper.FacebookLoginHelper;
import com.application.challenge.challenge.domain.helper.ParseHelper;
import com.application.challenge.challenge.domain.helper.TabHelper;
import com.application.challenge.challenge.domain.helper.TwitterLoginHelper;
import com.application.challenge.challenge.domain.model.ChallengeObject;
import com.application.challenge.challenge.domain.model.FollowActivityObject;
import com.application.challenge.challenge.domain.model.LikeObject;
import com.application.challenge.challenge.domain.model.PhotoObject;
import com.application.challenge.challenge.domain.model.SponsorObject;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseObject;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;

/**
 * Created by lucas on 10/12/14.
 */
public class ChallengeApplication extends Application {


    private static ChallengeApplication singleton;

    private int selectedMenuTab = 0;


    public ChallengeApplication getInstance(){
        return this.singleton;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;

        // Initialize Crash Reporting.
        ParseCrashReporting.enable(this);

        // Add your initialization code here

        ParseObject.registerSubclass(PhotoObject.class);
        ParseObject.registerSubclass(LikeObject.class);
        ParseObject.registerSubclass(FollowActivityObject.class);
        ParseObject.registerSubclass(SponsorObject.class);
        ParseObject.registerSubclass(ChallengeObject.class);

        Parse.initialize(this, getResources().getString(R.string.parse_application_id), getResources().getString(R.string.parse_client_key));

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this line.
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);

        ParseTwitterUtils.initialize(getResources().getString(R.string.twitter_key),getResources().getString(R.string.twitter_secret));

        ParseHelper.initialize();
        FacebookLoginHelper.initialize();
        TwitterLoginHelper.initialize();
        TabHelper.initialize();

    }

    public void setSelectedMenuTab(int tab){
        selectedMenuTab = tab;
    }

    public int getSelectedMenuTab(){
        return selectedMenuTab;
    }
}
