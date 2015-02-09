package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jeffhsu on 2/8/15.
 */
public class Profile {
    private String name;
    private String screenName;
    private String profileImageUrl;

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public static Profile fromJSON(JSONObject json) {
        // Extract and fill the values

        Profile profile = new Profile();
        try {
            profile.name = json.getString("name");
            profile.screenName = json.getString("screen_name");
            profile.profileImageUrl = json.getString("profile_image_url");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Return a user
        return profile;
    }
}
