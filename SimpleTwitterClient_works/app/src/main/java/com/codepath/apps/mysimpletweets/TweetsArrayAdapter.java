package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by shravyagarlapati on 8/4/16.
 */
//Take the object and convert them into views

public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    public TweetsArrayAdapter(Context context, List tweet) {
        super(context, android.R.layout.simple_list_item_1, tweet);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Tweet tweet = getItem(position);
        if(convertView==null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUser = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvCreatedAt = (TextView) convertView.findViewById(R.id.tvCreatedAt);

        tvUser.setText(tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());
        tvCreatedAt.setText(tweet.getCreatedAt());
        imageView.setImageResource(android.R.color.transparent);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageURL()).into(imageView);

        return convertView;

        //Get the view
        //INflate the view
        //Find the subviews
        //populate data into subviews
        // Return the views
    }



}
