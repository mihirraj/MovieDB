package com.cafe.friends.moviedb.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cafe.friends.moviedb.Parsers.GenreParser;
import com.cafe.friends.moviedb.Objects.MovieObject;
import com.cafe.friends.moviedb.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.ContentValues.TAG;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<MovieObject> mData;
    private LayoutInflater mInflater;
    GenreParser genreParser;

    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, List<MovieObject> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        genreParser = new GenreParser();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_item, parent, false);
        return new ViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String genres = "";
        MovieObject movieObject = mData.get(position);
        Picasso.get()
                .load("http://image.tmdb.org/t/p/w185/" + movieObject.getImage())
                .into(holder.movie_image);
        holder.movie_name.setText(movieObject.getMovieName());
        if (movieObject.getPopularity() >= 200)
            holder.movie_popularity.setText("Hot " + new String(Character.toChars(0x1F525)));
        else if (movieObject.getPopularity() < 200 && movieObject.getPopularity() >= 100)
            holder.movie_popularity.setText("Trending " + new String(Character.toChars(0x1F4C8)));
        else if (movieObject.getPopularity() < 100 && movieObject.getPopularity() >= 0)
            holder.movie_popularity.setText("Above Average " + new String(Character.toChars(0x1F53A)));
        for (String s : movieObject.getGenres()) {
            genres = genres + ", ";
            genres = genres + s;
        }
        Log.d(TAG, "onBindViewHolder: " + genres);
        holder.movie_genres.setText(genres.substring(2));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView movie_image;
        private TextView movie_name, movie_popularity, movie_genres;


        public ViewHolder(View itemView) {
            super(itemView);
            movie_image = itemView.findViewById(R.id.Movie_photo);
            movie_name = itemView.findViewById(R.id.Movie_name);
            movie_popularity = itemView.findViewById(R.id.Movie_popularity);
            movie_genres = itemView.findViewById(R.id.Movie_genres);
        }
    }
}