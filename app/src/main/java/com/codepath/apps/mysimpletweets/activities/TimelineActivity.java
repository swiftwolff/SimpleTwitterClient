package com.codepath.apps.mysimpletweets.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.adapters.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.fragments.HomeTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.MentionsTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.TweetsListFragment;
import com.codepath.apps.mysimpletweets.fragments.UserTimelineFragment;
import com.codepath.apps.mysimpletweets.listeners.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.utils.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends ActionBarActivity {

//    private ArrayList<Tweet> tweets;
//    private TweetsArrayAdapter aTweets;
//    private ListView lvTweets;
//    private long newestID;
//    private long oldestID;
//    private SwipeRefreshLayout swipeContainer;
//    private TwitterClient client;
//    private TweetsListFragment fragmentTweetsList;
    TweetsPagerAdapter tweetsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        // GEt the viewpager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);

        tweetsAdapter = new TweetsPagerAdapter(getSupportFragmentManager());
        // Set the viewpager adapter for the pager
        vpPager.setAdapter(tweetsAdapter);

        // Find the pager sliding tabs
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        // Attach the pager tabstrip to the viewpager
        tabsStrip.setViewPager(vpPager);

//        // Get the client
//        client = TwitterApplication.getRestClient();  // singleton client
//        populateTimeline();

//        if (savedInstanceState == null) {
//            // access the fragment
//            fragmentTweetsList = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_timeline);
//        }

//        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
//        // Setup refresh listener which triggers new data loading
//        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // Your code to refresh the list here.
//                // Make sure you call swipeContainer.setRefreshing(false)
//                // once the network request has completed successfully.
//                fetchTimelineAsync();
//            }
//        });
        // Configure the refreshing colors
//        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);

        // Attach the scroll listener
//        lvTweets.setOnScrollListener(new EndlessScrollListener() {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount) {
//                Log.d("DEBUG", "LOAD MORE!");
//                populateTimelineBackward();
//            }
//        });

    }

//    public void fetchTimelineAsync() {
//        client.getHomeTimelineOnRefresh(new JsonHttpResponseHandler() {
//            // SUCCESS
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
//                Log.d("DEBUG", json.toString());
//                // DESERIALIZE JSON
//                // CRAETE MODELS AND ADD THEM TO THE ADAPTER
//                // LOAD MODEL DATA INTO THE LISTVIEW
//                Log.d("DEBUG", "How many json objects? " + json.length());
//                Log.d("DEBUG", "Current newest ID is " + newestID);
//                tweets.clear();
//                tweets = Tweet.fromJSONArray(json);
//                if(tweets!=null && tweets.size()!=0) {
//
////                    newestID = tweets.get(0).getUid();
//                    oldestID = tweets.get(tweets.size() - 1).getUid();
//                    aTweets.addAll(tweets);
//                    aTweets.notifyDataSetChanged();
//                }
//                swipeContainer.setRefreshing(false);
//            }
//            // FAILURE
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//            }
//        }, 1);
//    }

    // Send an API request to get the timeline json
    // Fill the listview by creating the tweet objects from the json
//    private void populateTimeline() {
//        client.getHomeTimeline(new JsonHttpResponseHandler() {
//            // SUCCESS
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
//                Log.d("DEBUG", json.toString());
//                // DESERIALIZE JSON
//                // CRAETE MODELS AND ADD THEM TO THE ADAPTER
//                // LOAD MODEL DATA INTO THE LISTVIEW
////                ArrayList<Tweet> tweets = Tweet.fromJSONArray(json);
////                if(tweets!=null && tweets.size()!=0) {
////                    newestID = tweets.get(0).getUid();
////                    oldestID = tweets.get(tweets.size() - 1).getUid();
//                    fragmentTweetsList.addAll(Tweet.fromJSONArray(json));  //this is new!
////                    aTweets.addAll(tweets);
////                }
//            }
//            // FAILURE
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//            }
//        }, 1);
//    }

//    private void populateTimelineBackward() {
//        client.getHomeTimelineBackward(new JsonHttpResponseHandler() {
//            // SUCCESS
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
//                // DESERIALIZE JSON
//                // CRAETE MODELS AND ADD THEM TO THE ADAPTER
//                // LOAD MODEL DATA INTO THE LISTVIEW
//                ArrayList<Tweet> tweets = Tweet.fromJSONArray(json);
//                oldestID = tweets.get(tweets.size() - 1).getUid();
//                aTweets.addAll(tweets);
//            }
//
//            // FAILURE
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//            }
//        }, oldestID);
//    }


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
//            Toast.makeText(this,"Clicked!", Toast.LENGTH_LONG).show();
            writeTweet();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void writeTweet() {
            Intent intent = new Intent(TimelineActivity.this, WriteTweetActivity.class);
            startActivityForResult(intent,1);  //1 is the requestcode
    }

    public void onProfileView(MenuItem item) {
        // Launch profile view
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    public void profileLookUp(View view) {
        Toast.makeText(this,"you clicked on user profile image!", Toast.LENGTH_LONG).show();
        TextView tvName = (TextView) findViewById(R.id.tvName);
        Toast.makeText(this,"this user is " + tvName.getText().toString(), Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("screen_name",tvName.getText().toString());
        startActivity(i);
    }

    // Return the order of the fragments in the view paper
    public class TweetsPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 2;  // timeline and mentions
        private String tabTitles[] = {"Home","Mentions"};

        // Adapter gets the manager insert or remove fragment from activity
        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // The order and creation of fragments within the pager
        @Override
        public Fragment getItem(int position) {
            if (position == 0 ) {
                return new HomeTimelineFragment();
            } else if (position == 1) {
                return new MentionsTimelineFragment();
            } else {
                return null;
            }
        }

        // Returns the tab title
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        // How many fragments there are to swipe between
        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                tweetsAdapter.getItem(0);
            }
        }
    }
}
