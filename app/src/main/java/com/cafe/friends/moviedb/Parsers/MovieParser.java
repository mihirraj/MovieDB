package com.cafe.friends.moviedb.Parsers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.cafe.friends.moviedb.Objects.MovieObject;
import com.cafe.friends.moviedb.Parsers.GenreParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by mihir on 4/1/2018.
 */

public class MovieParser {
    public static final String MyPREFERENCES = "MyPrefs";
    List<MovieObject> movieObjects = new ArrayList<MovieObject>();
    GenreParser genreParser = new GenreParser();

    public List<MovieObject> Movie_parser(String resposne, Context context) throws JSONException {
        SharedPreferences prfs = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        JSONObject object = new JSONObject(resposne);
        JSONArray jsonArray = object.getJSONArray("results");
        String response = prfs.getString("GenreDetails", "");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Log.d(TAG, "Movie_parser: " + jsonObject.toString());
            ArrayList<String> list = new ArrayList<String>();
            JSONArray jsonArray_genre = jsonObject.getJSONArray("genre_ids");
            if (jsonArray_genre != null) {
                int len = jsonArray_genre.length();
                for (int j = 0; j < len; j++) {
                    list.add(genreParser.getGenreName(response, jsonArray_genre.getInt(j)));
                }
            }
            movieObjects.add(new MovieObject(jsonObject.getInt("id"), jsonObject.getString("title"), jsonObject.getString("poster_path"), jsonObject.getDouble("popularity"), list));
        }
        return movieObjects;
    }
}
