package com.codepath.apps.mysimpletweets.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jeffhsu on 2/7/15.
 */
public class User {
    // List attributes
    private String name;
    private long uid;
    private String screenName;
    private String profileImageUrl;
    private String tagline;
    private int followersCount;
    private int followingsCount;

    // deserialize the user json => User

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getTagline() {
        return tagline;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFriendsCount() {
        return followingsCount;
    }

    public static User fromJSON(JSONObject json) {
        // Extract and fill the values
        Log.d("DEBUG", "checking json items");
        Log.d("DEBUG", json.toString());
        User u = new User();
        try {
            u.name = json.getString("name");
            Log.d("DEBUG", u.name);
            u.uid = json.getLong("id");
            Log.d("DEBUG", ""+u.uid);
            u.screenName = json.getString("screen_name");
            Log.d("DEBUG", ""+u.screenName);
            u.profileImageUrl = json.getString("profile_image_url");
//            Log.d("DEBUG", "profile image url is " + u.profileImageUrl);
            u.tagline = json.getString("description");
            u.followersCount = json.getInt("followers_count");
            u.followingsCount = json.getInt("friends_count");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Return a user
        return u;
    }

}
