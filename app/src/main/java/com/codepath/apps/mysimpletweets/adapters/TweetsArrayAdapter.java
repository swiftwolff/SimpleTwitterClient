package com.codepath.apps.mysimpletweets.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.utils.TimeStamp;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by jeffhsu on 2/7/15.
 */

// Taking the Tweet objects and turning them into views displayed in the list
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }
    // Override and setup custom template
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1.  Get the tweet
        Tweet tweet = getItem(position);
        // 2.  Find or inflate the template
        if (convertView == null ) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }
        // 3.  Find the subviews to fill with data in the template
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvTagline);
        TextView tvTimeStamp = (TextView) convertView.findViewById(R.id.tvTimeStamp);

        // 4.  Populate data into the subviews
        if(tweet.getUser()!=null && tweet.getUser().getScreenName()!=null) {
            tvUserName.setText(tweet.getUser().getScreenName());
        }
        tvBody.setText(tweet.getBody());
        tvTimeStamp.setText(TimeStamp.getTimeStamp(tweet.getCreatedAt()));

        ivProfileImage.setImageResource(android.R.color.transparent);  //clear out the old image for a recycled view
        if(tweet.getUser()!=null) {
            Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);
        }

        // 5.  Return the view to be inserted into the list
        return convertView;
    }
}
