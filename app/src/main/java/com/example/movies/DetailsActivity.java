package com.example.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {
    @BindView(R.id.movie_poster)
    ImageView moviePoster;
    @BindView(R.id.movie_title)
    TextView movieTitle;
    @BindView(R.id.movie_rate)
    TextView movieRate;
    @BindView(R.id.movie_release_date)
    TextView movieReleaseDate;
    @BindView(R.id.movie_overview)
    TextView movieOverView;

    private static final String POSTER_URL =
            "https://image.tmdb.org/t/p/w185/";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
        ButterKnife.bind(this);

        ArrayList<Movie> movies = getIntent().getParcelableArrayListExtra("Movie");
        int adapterPosition = getIntent().getIntExtra(Intent.EXTRA_TEXT, 0);

        String stringDouble = Double.toString(movies.get(adapterPosition).rating);
        movieTitle.setText(movies.get(adapterPosition).movieName);
        movieRate.setText(stringDouble);
        movieReleaseDate.setText(movies.get(adapterPosition).releaseDate);
        movieOverView.setText(movies.get(adapterPosition).overView);
        Picasso.with(this).
                load(POSTER_URL + movies.get(adapterPosition).moviePoster).
                into(moviePoster);


    }


}
