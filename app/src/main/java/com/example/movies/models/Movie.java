package com.example.movies.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity
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
    public final String movieName;
    public final String moviePoster;
    public final String overView;
    public final double rating;
    public final String releaseDate;
    @PrimaryKey(autoGenerate = true)
    public int movieId;


    private Movie(Parcel in) {
        movieId = in.readInt();
        movieName = in.readString();
        moviePoster = in.readString();
        overView = in.readString();
        rating = in.readDouble();
        releaseDate = in.readString();
    }

    public Movie(int movieId, String movieName, String moviePoster, String overView, double rating, String releaseDate) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.moviePoster = moviePoster;
        this.overView = overView;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(movieId);
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
