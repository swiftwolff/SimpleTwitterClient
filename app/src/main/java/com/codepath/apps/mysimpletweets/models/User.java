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

    public static User fromJSON(JSONObject json) {
        // Extract and fill the values

        User u = new User();
        try {
            u.name = json.getString("name");
            u.uid = json.getLong("id");
            u.screenName = json.getString("screen_name");
            u.profileImageUrl = json.getString("profile_image_url");
//            Log.d("DEBUG", "profile image url is " + u.profileImageUrl);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Return a user
        return u;
    }
}
