package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by shravyagarlapati on 8/6/16.
 */
public class ComposeActivity extends AppCompatActivity {

    ImageButton btnCancel;
    ImageButton btnCompose;
    private TwitterClient client;
    EditText etComposeTweet;
    TextView tvUserName;
    ImageView ivProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        client = TwitterApplication.getRestClient();

        btnCancel = (ImageButton) findViewById(R.id.btnCancel);
        btnCompose = (ImageButton) findViewById(R.id.btnCompose);
        etComposeTweet = (EditText) findViewById(R.id.etComposeTweet);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);

        loadCurrentUserInfo();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Cancel click", "Buttton");

                Intent data = new Intent();
                data.putExtra("code", 20); // ints work too
                setResult(RESULT_OK, data); // set result code and bundle data for response
                finish();
            }
        });

        btnCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Compose click", "Button");
                String tweetVal = etComposeTweet.getText().toString();
                onComposeTweet(tweetVal);
            }
        });
    }

    private void onComposeTweet(String tweetVal) {
        client.composeTweet(tweetVal, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Success", response.toString());
                Intent data = new Intent();
                data.putExtra("code", 20);

                // Activity finished ok, return the data
                setResult(RESULT_OK, data); // set result code and bundle data for response
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Failure", errorResponse.toString());
            }

        });

    }

    private void loadCurrentUserInfo(){
        Log.d("Load user info", "USER");
        client.getCurrentUser(new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Success", response.toString());

                tvUserName.setText(User.fromJSON(response).getScreenName());
                Picasso.with(getApplicationContext()).load(User.fromJSON(response).getProfileImageURL()).into(ivProfileImage);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Failure", errorResponse.toString());
            }

        });
    }

    public void showSoftKeyboard(View view){
        if(view.requestFocus()){
            InputMethodManager imm =(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view,InputMethodManager.SHOW_IMPLICIT);
        }
    }

}
