package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by shravyagarlapati on 8/4/16.
 */
public class User
{
    private String name;

    public String getName() {
        return name;
    }

    public long getUserId() {
        return userId;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    private long userId;
    private String screenName;
    private String profileImageURL;

    public static User fromJSON(JSONObject jsonObject){
        User u = new User();
        try {
            u.name = jsonObject.getString("name");
            u.userId = jsonObject.getLong("id");
            u.profileImageURL = jsonObject.getString("profile_image_url");
            u.screenName = jsonObject.getString("screen_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return u;
    }

}
