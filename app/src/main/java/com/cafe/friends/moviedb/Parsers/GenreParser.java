package com.cafe.friends.moviedb.Parsers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

/**
 * Created by mihir on 4/1/2018.
 */

public class GenreParser {

    //HashMap<Integer,String> hashMap = new HashMap<Integer, String>();
    public String getGenreName(String response,int id) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray jsonArray = jsonObject.getJSONArray("genres");
        for (int i = 0;i< jsonArray.length();i++)
        {
            JSONObject object = jsonArray.getJSONObject(i);
            if (object.getInt("id") == id)
            {
                Log.d(TAG, "getGenreName: "+object.getString("name"));
                return object.getString("name");

            }
        }

        return "";
    }
}
