package com.example.movies;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.movies.data.DataBase;
import com.example.movies.models.Movie;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> movies;

    public MainViewModel(@NonNull Application application) {
        super(application);
        DataBase appDatabase = DataBase.getDatabase(this.getApplication());
        movies = appDatabase.moviesDAO().getAllMovies();
    }

    public LiveData<List<Movie>> getMovies() {

        return movies;
    }
}
