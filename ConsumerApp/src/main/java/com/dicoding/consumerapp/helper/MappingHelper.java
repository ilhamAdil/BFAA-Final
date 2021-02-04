package com.dicoding.consumerapp.helper;

import android.database.Cursor;

import com.dicoding.consumerapp.db.DatabaseContract;
import com.dicoding.consumerapp.modelsettergetter.Detail;

import java.util.ArrayList;

public class MappingHelper {

    public static ArrayList<Detail> mapCursorToArrayList(Cursor usersCursor){
        ArrayList<Detail> usersList = new ArrayList<>();

        while (usersCursor.moveToNext()){
            String username = usersCursor.getString(usersCursor.getColumnIndexOrThrow(DatabaseContract.FavColumns.USERNAME));
            String avatar = usersCursor.getString(usersCursor.getColumnIndexOrThrow(DatabaseContract.FavColumns.AVATAR));
            String followers = usersCursor.getString(usersCursor.getColumnIndexOrThrow(DatabaseContract.FavColumns.FOLLOWERS));
            String following = usersCursor.getString(usersCursor.getColumnIndexOrThrow(DatabaseContract.FavColumns.FOLLOWING));
            String repository = usersCursor.getString(usersCursor.getColumnIndexOrThrow(DatabaseContract.FavColumns.REPO));
            usersList.add(new Detail(username,avatar,followers,following,repository));
        }
        return usersList;
    }
}
