package com.application.challenge.challenge.domain;

/**
 * Created by lucas on 16/1/15.
 */
public class DiscoverItem {

    private String username;
    private String profilePicture;
    private String img1;
    private String img2;
    private String img3;

    public DiscoverItem(){

    }


    public DiscoverItem(String username, String profilePicture, String img1, String img2, String img3) {
        this.username = username;
        this.profilePicture = profilePicture;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }
}
