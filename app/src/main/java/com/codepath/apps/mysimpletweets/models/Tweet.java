package com.codepath.apps.mysimpletweets.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jeffhsu on 2/7/15.
 */

// Parse the JSON + Store the data, encapsulate logic or display logic
public class Tweet {
    // list out the attributes
    private String body;
    private long uid;  //not the userid, but unique id for the tweet
    //private User user;
    private String createdAt;
    private User user;



    // deserialize the JSON
    // Tweet.fromJSON({...}) => <Tweet>
    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        // Extract the values from the json, store them
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Return the tweet object
        return tweet;
    }

    public User getUser() {
        return user;
    }

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    // Tweet.fromJSONArray([{...},{...}] => List<Tweet>
    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        for(int i=0; i<jsonArray.length();i ++) {
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                if( tweet != null) {
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        // Iterate the json array and create tweets
        // Return the finished list
        return tweets;
    }
}
