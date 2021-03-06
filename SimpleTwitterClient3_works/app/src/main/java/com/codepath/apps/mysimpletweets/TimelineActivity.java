package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    @BindView(R.id.lvTweets) ListView lvTweets;
    @BindView(R.id.btnCompose) ImageButton btnCompose;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;

    private TwitterClient client;
    TweetsArrayAdapter adapter;
    ArrayList<Tweet> tweets;
    Long LowesttweetId = 0L;
    private final int REQUEST_CODE = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(this);

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                adapter.clear();
                populateTimeline(1L,25);
                swipeContainer.setRefreshing(false);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    //Create a arraylist
        tweets = new ArrayList<>();
        adapter = new TweetsArrayAdapter(this, tweets);
        lvTweets.setAdapter(adapter);
        client = TwitterApplication.getRestClient();
        populateTimeline(1L, 20);

        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int max, int totalItemsCount) {
                //Set the max_id and call populate again
                populateTimeline(LowesttweetId, totalItemsCount);
                return false;
            }
        });

        btnCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Button click", "Buttton");
                Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
                i.putExtra("mode", 2); // pass arbitrary data to launched activity
                startActivityForResult(i, REQUEST_CODE);

                //onComposeTweet();
            }
        });


    }

    private void populateTimeline(Long max_id, int count) {
        client.getHomeTimeline(max_id, count, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("Success", "LENGTH "+ response.toString().length() + " && VALUE" + response.toString());

                findLowestTweetId(response);
                //LOOk at JSON
                //Deserialize
                //Create Models
                //load the models data into list view
                adapter.addAll(Tweet.fromJSONArray(response));

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Failure", errorResponse.toString());
            }
        });
    }

    private void findLowestTweetId(JSONArray response) {
        ArrayList<Long> tweetIds = new ArrayList<>();
        for(int i=0;i<response.length();i++) {
            tweetIds.add(Tweet.fromJSONArray(response).get(i).tweetId);
        }
        Log.d("TWEET IDS", tweetIds.toString() + ":" + tweetIds.size());
        LowesttweetId = Collections.min(tweetIds);
        Log.d("LOWEST TWEET IDS", ":"+ LowesttweetId);
    }


}
