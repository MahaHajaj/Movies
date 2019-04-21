package com.example.movies.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.movies.models.Movie;

@Database(entities = Movie.class, version = 1, exportSchema = false)
public abstract class DataBase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "favorite_Movies";
    private static DataBase dataBase;

    public static DataBase getDatabase(Context context) {
        if (dataBase == null) {
            synchronized (LOCK) {
                dataBase = Room.databaseBuilder(context, DataBase.class, DataBase.DATABASE_NAME).fallbackToDestructiveMigration().build();
            }
        }
        return dataBase;
    }

    public abstract MoviesDAO moviesDAO();
}
