package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.utils.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;

/**
 * Created by jeffhsu on 2/16/15.
 */
public class HomeTimelineFragment extends TweetsListFragment {
    private TwitterClient client;
    private TweetsListFragment fragmentTweetsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient(); // singleton client
        populateTimeline();
    }

    private void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            // SUCCESS

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("DEBUG", json.toString());
                // DESERIALIZE JSON
                // CRAETE MODELS AND ADD THEM TO THE ADAPTER
                // LOAD MODEL DATA INTO THE LISTVIEW
//                ArrayList<Tweet> tweets = Tweet.fromJSONArray(json);
//                if(tweets!=null && tweets.size()!=0) {
//                    newestID = tweets.get(0).getUid();
//                    oldestID = tweets.get(tweets.size() - 1).getUid();
                addAll(Tweet.fromJSONArray(json));  //this is new!
//                    aTweets.addAll(tweets);
//                }
            }
            // FAILURE
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }
        }, 1);
    }
}
