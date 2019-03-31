package com.example.movies.utilities;


import com.example.movies.Movie;

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
                String name = resultsJSONObject.optString("title");
                String overView = resultsJSONObject.optString("overview");
                double rating = resultsJSONObject.optDouble("vote_average");
                String poster = resultsJSONObject.optString("poster_path");
                String date = resultsJSONObject.getString("release_date");
                Movie m = new Movie(name, poster, overView, rating, date);
                movie.add(m);

            }
            return movie;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
