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

import butterknife.BindView;
import butterknife.ButterKnife;

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
        ViewHolder viewHolder;

        Tweet tweet = getItem(position);
        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvUser.setText(tweet.getUser().getScreenName());
        viewHolder.tvBody.setText(tweet.getBody());
        viewHolder.tvCreatedAt.setText(tweet.getCreatedAt());
        viewHolder.imageView.setImageResource(android.R.color.transparent);
        viewHolder.imageView.setTag(tweet.getUser().getScreenName());

        //Glide.with(getContext())
        //.load(tweet.getUser().getProfileImageURL()).into(viewHolder.imageView);
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageURL()).into(viewHolder.imageView);

        return convertView;

    }

    public class ViewHolder {

        @BindView(R.id.ivProfileImage) ImageView imageView;
        @BindView(R.id.tvUserName) TextView tvUser;
        @BindView(R.id.tvBody) TextView tvBody;
        @BindView(R.id.tvCreatedAt) TextView tvCreatedAt;

        public ViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
        }
    }


}
