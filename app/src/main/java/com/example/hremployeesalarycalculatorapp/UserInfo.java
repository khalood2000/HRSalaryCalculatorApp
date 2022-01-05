package com.example.hremployeesalarycalculatorapp;

import com.google.firebase.database.Exclude;

public class UserInfo {
    public String userID;
    public String userName;
    public String userPost;
    public String userContact;
    public String userSalary;
    public String mKey;

    public UserInfo() {
    }

    public UserInfo(String userID, String userName, String userPost, String userContact, String userSalary) {
        this.userID = userID;
        this.userName = userName;
        this.userPost = userPost;
        this.userContact = userContact;
        this.userSalary = userSalary;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPost() {
        return userPost;
    }

    public void setUserPost(String userPost) {
        this.userPost = userPost;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getUserSalary() {
        return userSalary;
    }

    public void setUserSalary(String userSalary) {
        this.userSalary = userSalary;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }
    @Exclude
    public void setKey(String Key) {
        mKey = Key;
    }
}
