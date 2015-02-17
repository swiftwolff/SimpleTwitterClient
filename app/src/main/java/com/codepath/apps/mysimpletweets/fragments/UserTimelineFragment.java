package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.listeners.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.utils.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by jeffhsu on 2/16/15.
 */
public class UserTimelineFragment extends TweetsListFragment {
    private TwitterClient client;
    private TweetsListFragment fragmentTweetsList;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        // Attach the scroll listener
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Log.d("DEBUG", "LOAD MORE!");
                if(totalItemsCount >= 25) {
                    populateTimelineBackward();
                }
            }
        });
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient(); // singleton client
        populateTimeline();
    }

    public static UserTimelineFragment newInstance(String screen_name) {
        UserTimelineFragment userFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screen_name);
        userFragment.setArguments(args);
        return userFragment;
    }

    private void populateTimeline() {
        String screenName = getArguments().getString("screen_name");
        client.getUserTimeline(screenName, new JsonHttpResponseHandler() {
            // SUCCESS

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("DEBUG", json.toString());
                // DESERIALIZE JSON
                // CRAETE MODELS AND ADD THEM TO THE ADAPTER
                // LOAD MODEL DATA INTO THE LISTVIEW
                ArrayList<Tweet> tweets = Tweet.fromJSONArray(json);
//                if(tweets!=null && tweets.size()!=0) {
//                    newestID = tweets.get(0).getUid();
                oldestID = tweets.get(tweets.size() - 1).getUid();
                addAll(Tweet.fromJSONArray(json));
//                    aTweets.addAll(tweets);
//                }
            }

            // FAILURE
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }
        });
    }

    private void populateTimelineBackward() {
        client.getHomeTimelineBackward(new JsonHttpResponseHandler() {
            // SUCCESS
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                // DESERIALIZE JSON
                // CRAETE MODELS AND ADD THEM TO THE ADAPTER
                // LOAD MODEL DATA INTO THE LISTVIEW
                ArrayList<Tweet> tweets = Tweet.fromJSONArray(json);
                oldestID = tweets.get(tweets.size() - 1).getUid();
                addAll(tweets);
            }

            // FAILURE
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }
        }, oldestID);
    }
}
