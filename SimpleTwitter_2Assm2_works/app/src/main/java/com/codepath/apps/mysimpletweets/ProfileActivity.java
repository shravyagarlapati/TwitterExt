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
//    @BindView(R.id.ivProfileViewImage) ImageView ivProfileImage;
//    @BindView(R.id.tvProfileUserName) TextView tvUserName;
//    @BindView(R.id.tvFollowers) TextView tvFollowers;
//    @BindView(R.id.tvFollowing) TextView tvFollowing;
//    @BindView(R.id.tvTagLine) TextView tvTagLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        String userName = getIntent().getStringExtra("screen_name");
        client = TwitterApplication.getRestClient();

        if(userName==null) {
            //Log.d("Screen NAME", checkUser);
            client.getCurrentUser(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    user = User.fromJSON(response);
                    getSupportActionBar().setTitle("@" + user.getScreenName());
                    populateProfileHeader(user);
                }

            });
        }
        else if(userName!=null) {
            Log.d("Screen NAME", userName);
            client.getUserProfileInfo(userName, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    user = User.fromJSON(response);
                    Log.d("Success", user.toString());
                    getSupportActionBar().setTitle("@" + user.getScreenName());
                    populateProfileHeader(user);
                }
            });
        }

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
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileViewImage);
        TextView tvTagLine = (TextView) findViewById(R.id.tvTagLine);

        tvUserName.setText(user.getName());
        tvFollowers.setText(user.getFollowersCount() + " Followers");
        tvFollowing.setText(user.getFriendsCount() + " Following");
        Glide.with(getApplicationContext()).load(user.getProfileImageURL()).into(ivProfileImage);
        tvTagLine.setText(user.getTagLine());

    }

}
