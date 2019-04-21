package com.example.movies.utilities;


import com.example.movies.models.Movie;
import com.example.movies.models.Review;
import com.example.movies.models.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieJsonUtils {
    public static ArrayList<Movie> parseMovieJson(String json) {
        try {

            JSONObject movies = new JSONObject(json);
            JSONArray results = movies.getJSONArray("results");
            ArrayList<Movie> movie = new ArrayList<>();
            for (int i = 0; i < results.length(); i++) {
                JSONObject resultsJSONObject = results.getJSONObject(i);
                int id = resultsJSONObject.optInt("id");
                String name = resultsJSONObject.optString("title");
                String overView = resultsJSONObject.optString("overview");
                double rating = resultsJSONObject.optDouble("vote_average");
                String poster = resultsJSONObject.optString("poster_path");
                String date = resultsJSONObject.getString("release_date");
                Movie m = new Movie(id, name, poster, overView, rating, date);
                movie.add(m);

            }
            return movie;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<Review> parseMovieReview(String json) {
        try {
            JSONObject movies = new JSONObject(json);
            JSONArray results = movies.getJSONArray("results");
            ArrayList<Review> reviews = new ArrayList<>();
            for (int i = 0; i < results.length(); i++) {
                JSONObject resultsJSONObject = results.getJSONObject(i);
                String author = resultsJSONObject.optString("author");
                String content = resultsJSONObject.optString("content");
                Review r = new Review(author, content);
                reviews.add(r);

            }
            return reviews;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Trailer> parseMovieTrailer(String json) {
        try {
            JSONObject movies = new JSONObject(json);
            JSONArray results = movies.getJSONArray("results");
            ArrayList<Trailer> trailers = new ArrayList<>();
            for (int i = 0; i < results.length(); i++) {
                JSONObject resultsJSONObject = results.getJSONObject(i);
                String name = resultsJSONObject.optString("name");
                String key = resultsJSONObject.optString("key");
                Trailer t = new Trailer(name, key);
                trailers.add(t);

            }
            return trailers;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}