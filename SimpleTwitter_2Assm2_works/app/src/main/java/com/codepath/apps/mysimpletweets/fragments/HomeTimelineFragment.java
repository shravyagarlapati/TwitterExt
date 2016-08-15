package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import cz.msebera.android.httpclient.Header;

/**
 * Created by shravyagarlapati on 8/13/16.
 */
public class HomeTimelineFragment extends TweetsListFragment {

    private TwitterClient client;
    Long LowesttweetId = 0L;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = TwitterApplication.getRestClient();
        populateTimeline(1L, 20);

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
                addAll(Tweet.fromJSONArray(response));
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
