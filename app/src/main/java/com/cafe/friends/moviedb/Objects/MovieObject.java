package com.cafe.friends.moviedb.Objects;

import java.util.List;

/**
 * Created by mihir on 4/1/2018.
 */

public class MovieObject {
    private int id;
    private String MovieName,Image;
    private double Popularity;
    private List<String> Genres;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return MovieName;
    }

    public void setMovieName(String movieName) {
        MovieName = movieName;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public double getPopularity() {
        return Popularity;
    }

    public void setPopularity(double popularity) {
        Popularity = popularity;
    }

    public List<String> getGenres() {
        return Genres;
    }

    public void setGenres(List<String> genres) {
        Genres = genres;
    }

    public MovieObject(int id, String movieName, String image, double popularity, List<String> genres) {
        this.id = id;

        MovieName = movieName;
        Image = image;
        Popularity = popularity;
        Genres = genres;
    }
}
