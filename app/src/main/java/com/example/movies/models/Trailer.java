package com.example.movies.models;

import android.os.Parcel;
import android.os.Parcelable;

//https://www.youtube.com/watch?v=
public class Trailer implements Parcelable {
    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };
    public String name;
    public String key;

    private Trailer(Parcel in) {
        name = in.readString();
        key = in.readString();
    }

    public Trailer(String name, String key) {
        this.name = name;
        this.key = key;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(key);
    }
}
