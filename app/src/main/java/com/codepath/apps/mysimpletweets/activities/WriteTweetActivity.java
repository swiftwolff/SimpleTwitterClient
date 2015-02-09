package com.codepath.apps.mysimpletweets.activities;

import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.models.Profile;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.utils.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class WriteTweetActivity extends ActionBarActivity {

    private TwitterClient client;
    private TextView tvName;
    private TextView tvScreenName;
    private ImageView ivProfile;
    private EditText etTweetInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_write_tweet);
        tvName = (TextView) findViewById(R.id.tvName);
        tvScreenName = (TextView) findViewById(R.id.tvScreenName);
        ivProfile = (ImageView) findViewById(R.id.ivProfile);
        etTweetInput = (EditText) findViewById(R.id.etTweetInput);
        // Get the client
        client = TwitterApplication.getRestClient();  // singleton client
        populateProfile();
    }

    // Send an API request to get the timeline json
    // Fill the listview by creating the tweet objects from the json
    private void populateProfile() {
        client.getCurrentUserInfo(new JsonHttpResponseHandler() {
            // SUCCESS
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                Log.d("DEBUG", json.toString());

                Profile p = Profile.fromJSON(json);
                tvName.setText(p.getName());
                tvScreenName.setText(p.getScreenName());
                Picasso.with(getBaseContext()).load(p.getProfileImageUrl()).into(ivProfile);
            }
            // FAILURE
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }
        });
    }

    private void tweet() {
        client.postTweet(new JsonHttpResponseHandler() {
            // SUCCESS
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                Log.d("DEBUG", "Successfully tweeted!");
                setResult(RESULT_OK);
                finish();
            }

            // FAILURE
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }
        }, etTweetInput.getText().toString());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_write_tweet, menu);
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
            tweet();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
