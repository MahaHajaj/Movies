package com.example.movies.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.movies.models.Movie;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface MoviesDAO {

    @Query("SELECT * FROM Movie")
    LiveData<List<Movie>> getAllMovies();

    @Insert(onConflict = REPLACE)
    void insertMovie(Movie movie);

    @Query("DELETE FROM Movie WHERE movieId = :id")
    void deleteMovie(String id);


    @Query("SELECT * FROM Movie WHERE movieId = :id")
    Movie getMovie(int id);

    @Query("SELECT movieId FROM Movie")
    List<Integer> getIDs();

}
