package com.application.challenge.challenge.domain;

import android.net.Uri;

/**
 * Created by lucas on 16/1/15.
 */
public class ChallengesItem {

    private String title;
    private String subtitle;
    private String url;

    public ChallengesItem(){

    }

    public ChallengesItem(String title, String subtitle, String url){
        this.title = title;
        this.subtitle = subtitle;
        this.url = url;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUri(String url) {
        this.url = url;
    }
}
