package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1/"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "8yQBZJQ2wOBsqEOd8AbeQy8Ok";       // Change this
	public static final String REST_CONSUMER_SECRET = "OqV6YgZcu4I1tiVPL9HWcOcJ21P9GHLxwn1C9vFR6EkIs6QVOk"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cpsimpletweets"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	//EACH METHOD HERE == ENDPOINT
	public void getHomeTimeline(Long max_id, int count, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", count);

		if (max_id > 1L) {
			params.put("max_id", (max_id-1));//LOwest tweet id
			params.put("since_id", 1);
		}
		else
			params.put("since_id", 1);

		Log.d("PARAMs HOME TIMELINE", params.toString());
		getClient().get(apiUrl, params, handler);

	}

	//Compose Tweet
	public void composeTweet(String tweetVal, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status",tweetVal);

		getClient().post(apiUrl, params, handler);

	}

	//Get Current User Info
	//account/verify_credentials.json
	public void getCurrentUser(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("account/verify_credentials.json");
		getClient().get(apiUrl, null, handler);

	}

	//get MentionsTimeline
	public void getMentionsTimeline(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", 25);
		Log.d("PARAMs HOME TIMELINE", params.toString());
		getClient().get(apiUrl, params, handler);

	}

	//Get UserTimeline
	public void getUserTimeline(String screenname, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		RequestParams params = new RequestParams();
		params.put("count", 25);
		params.put("screen_name", screenname);
		Log.d("PARAMs HOME TIMELINE", params.toString());
		getClient().get(apiUrl, params, handler);

	}

	//Get Other user info
	//Get UserTimeline
	public void getUserProfileInfo(String screenname, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("users/show.json");
		RequestParams params = new RequestParams();
		params.put("screen_name", screenname);
		Log.d("PARAMs HOME TIMELINE", params.toString());
		getClient().get(apiUrl, params, handler);

	}



	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}