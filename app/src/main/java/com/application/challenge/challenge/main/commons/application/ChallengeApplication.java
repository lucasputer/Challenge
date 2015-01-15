package com.application.challenge.challenge.main.commons.application;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;

/**
 * Created by lucas on 10/12/14.
 */
public class ChallengeApplication extends Application {
    public static final String APPLICATION_ID = "anSnkP0SXPzZ4knCJrcyWeOzVbYpLkhHoFOdqQ7m";
    public static final String CLIENT_KEY = "hakYECYyeB00NoUHdRNrJ7f2PWanNJEi4EYadCd0";

    private static final String TWITTER_KEY = "ceugZujHm6d1jFjXQHiLs2Zro";
    private static final String TWITTER_SECRET = "kfN5IgyIkyapjeaPvZWeCNhdAIAcV9zoS3mgTazucMwqipAQFb";

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
        Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);


        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);

        ParseTwitterUtils.initialize(TWITTER_KEY, TWITTER_SECRET);

    }

    public void setSelectedMenuTab(int tab){
        selectedMenuTab = tab;
    }

    public int getSelectedMenuTab(){
        return selectedMenuTab;
    }
}
