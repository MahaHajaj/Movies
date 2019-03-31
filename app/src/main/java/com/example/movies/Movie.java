package com.example.movies;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
   final String movieName;
   final String moviePoster;
   final String overView;
   final double rating;
   final String releaseDate;

    public Movie(String movieName, String moviePoster, String overView, double rating, String releaseDate) {
        this.movieName = movieName;
        this.moviePoster = moviePoster;
        this.overView = overView;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }

    private Movie(Parcel in) {
        movieName = in.readString();
        moviePoster = in.readString();
        overView = in.readString();
        rating = in.readDouble();
        releaseDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieName);
        dest.writeString(moviePoster);
        dest.writeString(overView);
        dest.writeDouble(rating);
        dest.writeString(releaseDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
