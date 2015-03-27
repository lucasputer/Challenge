package com.application.challenge.challenge.domain.helper;

import com.application.challenge.challenge.domain.connector.FacebookGraphAPIConnector;
import com.application.challenge.challenge.domain.custom.Tabs;
import com.parse.ParseUser;

/**
 * Created by lucas on 27/2/15.
 */
public class TabHelper {

    private static TabHelper instance;
    private Tabs tab;


    private TabHelper(){
        tab = Tabs.HOME;
    }

    public static void initialize(){
        if(instance == null){
            instance = new TabHelper();
        }
    }

    public static void setTab(Tabs tab){
        instance.tab = tab;
    }

    public static Tabs getTab(){
        return instance.tab;
    }




}
