package com.codepath.apps.mysimpletweets;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.mysimpletweets.fragments.UserTimelineFragment;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {

    TwitterClient client;
    User user;
//    @BindView(R.id.ivProfileImage) ImageView ivProfileImage;
//    @BindView(R.id.tvTagLine) TextView tvTagLine;
//    @BindView(R.id.tvUserName) TextView tvUserName;
//    @BindView(R.id.tvFollowers) TextView tvFollowers;
//    @BindView(R.id.tvFollowing) TextView tvFollowing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        client = TwitterApplication.getRestClient();
        client.getCurrentUser(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                user = User.fromJSON(response);
                getSupportActionBar().setTitle("@" + user.getScreenName());
                populateProfileHeader(user);
            }

        });

        //Get the screenname from activity that launches profile
        String screen_name = getIntent().getStringExtra("screen_name");

        UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screen_name);

        //Display user fragment (Dynamically)
        if(savedInstanceState==null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flContainer, userTimelineFragment);
            fragmentTransaction.commit();
        }

    }


    private void populateProfileHeader(User user) {
        Log.d("USER INFO", user.getName());
        TextView tvUserName = (TextView) findViewById(R.id.tvProfileUserName);
        tvUserName.setText(user.getName());
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        tvFollowers.setText(user.getFollowersCount() + " Followers");
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        tvFollowing.setText(user.getFriendsCount() + " Following");

        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileViewImage);
        Glide.with(getApplicationContext())
                .load(user.getProfileImageURL()).into(ivProfileImage);
        TextView tvTagLine = (TextView) findViewById(R.id.tvTagLine);
        tvTagLine.setText(user.getTagLine());

    }

}
