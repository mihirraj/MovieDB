package com.cafe.friends.moviedb.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.bridge.Bridge;
import com.afollestad.bridge.BridgeException;
import com.afollestad.bridge.Callback;
import com.afollestad.bridge.Request;
import com.afollestad.bridge.Response;
import com.cafe.friends.moviedb.Objects.MovieObject;
import com.cafe.friends.moviedb.Parsers.MovieParser;
import com.cafe.friends.moviedb.Adapters.MyRecyclerViewAdapter;
import com.cafe.friends.moviedb.R;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class UpcomingMovies extends Fragment{

    List<MovieObject> movieObjects = new ArrayList<>();
    RecyclerView recyclerView;
    MovieParser movieParser;
    private static final String upcoming = "https://api.themoviedb.org/3/movie/upcoming?api_key=676a0afb6adf02cdba938ed669e4198d&language=en-US&page=1";

    public UpcomingMovies() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieParser = new MovieParser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_upcoming_movies, container, false);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.Up_MovieView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Bridge.get(upcoming)
                .throwIfNotSuccess() // optional
                .request(new Callback() {
                    @Override
                    public void response(Request request, Response response, BridgeException e) {
                        if (e != null) {
                            // See the 'Error Handling' section for information on how to process BridgeExceptions
                            int reason = e.reason();
                        } else {
                            // Use the Response object
                            String responseContent = response.asString();
                            Log.d(TAG, "response: "+responseContent);
                            try {
                                movieObjects = movieParser.Movie_parser(responseContent,getContext());
                                Log.d(TAG, "response: "+movieObjects.get(0).getImage());
                                recyclerView.setAdapter(new MyRecyclerViewAdapter(getContext(),movieObjects));
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }

                        }
                    }
                });

        return rootView;

    }

}
