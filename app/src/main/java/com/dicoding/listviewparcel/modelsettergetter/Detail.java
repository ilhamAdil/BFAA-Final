package com.dicoding.listviewparcel.modelsettergetter;

import android.os.Parcel;
import android.os.Parcelable;

public class Detail implements Parcelable {
    private String name;
    private String avatar;
    private String followers;
    private String following;
    private String repo;


    public Detail(){

    }

    protected Detail(Parcel in) {
        name = in.readString();
        avatar = in.readString();
        followers = in.readString();
        following = in.readString();
        repo = in.readString();
    }

    public static final Creator<Detail> CREATOR = new Creator<Detail>() {
        @Override
        public Detail createFromParcel(Parcel in) {
            return new Detail(in);
        }

        @Override
        public Detail[] newArray(int size) {
            return new Detail[size];
        }
    };

    public Detail(String username, String avatar, String followers, String following, String repository) {
        this.name = username;
        this.avatar = avatar;
        this.followers = followers;
        this.following = following;
        this.repo = repository;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(avatar);
        dest.writeString(followers);
        dest.writeString(following);
        dest.writeString(repo);
    }
}
