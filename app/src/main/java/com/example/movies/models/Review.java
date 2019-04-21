package com.example.movies.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Review implements Parcelable {

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
    public String auther;
    public String review;

    private Review(Parcel in) {
        auther = in.readString();
        review = in.readString();
    }

    public Review(String auther, String review) {
        this.auther = auther;
        this.review = review;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(auther);
        parcel.writeString(review);
    }
}
