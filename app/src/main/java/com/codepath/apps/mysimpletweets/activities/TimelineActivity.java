package com.codepath.apps.mysimpletweets.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.adapters.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.listeners.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.utils.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;

import java.util.ArrayList;

public class TimelineActivity extends ActionBarActivity {

    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;
    private long newestID;
    private long oldestID;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        newestID = 1;
        lvTweets = (ListView) findViewById(R.id.lvTweets);
        // Create the arrayList (data source)
        tweets = new ArrayList<>();
        // Construct the adapter from data source
        aTweets = new TweetsArrayAdapter(this, tweets);
        // Connect adapter to list view
        lvTweets.setAdapter(aTweets);

        // Attach the scroll listener
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Log.d("DEBUG", "LOAD MORE!");
                populateTimelineBackward();
            }
        });
        // Get the client
        client = TwitterApplication.getRestClient();  // singleton client
        populateTimeline();

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchTimelineAsync();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public void fetchTimelineAsync() {
        client.getHomeTimelineOnRefresh(new JsonHttpResponseHandler() {
            // SUCCESS
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("DEBUG", json.toString());
                // DESERIALIZE JSON
                // CRAETE MODELS AND ADD THEM TO THE ADAPTER
                // LOAD MODEL DATA INTO THE LISTVIEW
                Log.d("DEBUG", "How many json objects? " + json.length());
                Log.d("DEBUG", "Current newest ID is " + newestID);
                tweets.clear();
                tweets = Tweet.fromJSONArray(json);
                if(tweets!=null && tweets.size()!=0) {

//                    newestID = tweets.get(0).getUid();
                    oldestID = tweets.get(tweets.size() - 1).getUid();
                    aTweets.addAll(tweets);
                    aTweets.notifyDataSetChanged();
                }
                swipeContainer.setRefreshing(false);
            }
            // FAILURE
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }
        }, 1);
    }

    // Send an API request to get the timeline json
    // Fill the listview by creating the tweet objects from the json
    private void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            // SUCCESS

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("DEBUG", json.toString());
                // DESERIALIZE JSON
                // CRAETE MODELS AND ADD THEM TO THE ADAPTER
                // LOAD MODEL DATA INTO THE LISTVIEW
                ArrayList<Tweet> tweets = Tweet.fromJSONArray(json);
                if(tweets!=null && tweets.size()!=0) {
                    newestID = tweets.get(0).getUid();
                    oldestID = tweets.get(tweets.size() - 1).getUid();
                    aTweets.addAll(tweets);
                }
            }
            // FAILURE
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }
        }, newestID);
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
                aTweets.addAll(tweets);
            }

            // FAILURE
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }
        }, oldestID);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.barCreate) {
            Toast.makeText(this,"Clicked!", Toast.LENGTH_LONG).show();
            writeTweet();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void writeTweet() {
            Intent intent = new Intent(TimelineActivity.this, WriteTweetActivity.class);
            startActivityForResult(intent,1);  //1 is the requestcode
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                fetchTimelineAsync();
                Toast.makeText(this,"Result is ok!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
