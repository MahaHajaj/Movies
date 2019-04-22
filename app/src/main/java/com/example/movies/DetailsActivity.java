package com.example.movies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movies.adapter.ReviewsAdapter;
import com.example.movies.adapter.TrailersAdapter;
import com.example.movies.data.DataBase;
import com.example.movies.models.Movie;
import com.example.movies.models.Review;
import com.example.movies.models.Trailer;
import com.example.movies.utilities.MovieJsonUtils;
import com.example.movies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements TrailersAdapter.TrailerAdapterOnClickHandler {
    private static final String POSTER_URL =
            "https://image.tmdb.org/t/p/w185/";
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
    @BindView(R.id.star)
    ImageView star;
    @BindView(R.id.trailers)
    RecyclerView trailer;
    @BindView(R.id.reviews)
    RecyclerView reviews;
    int id;
    Movie mMovies;
    ReviewsAdapter reviewsAdapter;
    TrailersAdapter trailersAdapter;
    private ArrayList<Review> mReviews;
    private ArrayList<Trailer> mTrailers;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
        ButterKnife.bind(this);

        reviews.setLayoutManager(new GridLayoutManager(this, 1));
        reviews.setHasFixedSize(true);
        reviewsAdapter = new ReviewsAdapter(this);
        reviews.setAdapter(reviewsAdapter);
        trailer.setLayoutManager(new GridLayoutManager(this, 1));
        trailer.setHasFixedSize(true);
        trailersAdapter = new TrailersAdapter(this, this);
        trailer.setAdapter(trailersAdapter);

        mMovies = getIntent().getParcelableExtra("Movie");
        id = mMovies.movieId;

        String stringDouble = Double.toString(mMovies.rating);
        movieTitle.setText(mMovies.movieName);
        movieRate.setText(stringDouble);
        movieReleaseDate.setText(mMovies.releaseDate);
        movieOverView.setText(mMovies.overView);
        Picasso.with(this).
                load(POSTER_URL + mMovies.moviePoster).
                into(moviePoster);
        new FetchTrailer().execute();
        new FetchReview().execute();

        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        new MovieAsyncTask().execute();
                    }
                });


            }
        });

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                DataBase database = DataBase.getDatabase(getApplicationContext());
                Movie movie = database.moviesDAO().getMovie(mMovies.movieId);
                if (movie != null) {
                    star.setImageResource(R.drawable.ic_star_24dp);
                } else {
                    star.setImageResource(R.drawable.ic_star_border_24dp);

                }
            }
        });
    }

    @Override
    public void onClick(int adapterPosition) {
        Uri uri = Uri.parse("https://www.youtube.com/watch?v=" + mTrailers.get(adapterPosition).key);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getPackageManager()) != null){
        startActivity(intent);
        }
    }

    private class MovieAsyncTask extends AsyncTask<Movie, Void, Movie> {

        @Override
        protected Movie doInBackground(Movie... movies) {
            String ID = Integer.toString(id);
            DataBase database = DataBase.getDatabase(getApplicationContext());
            Movie movie = database.moviesDAO().getMovie(id);
            List<Integer> IDs = database.moviesDAO().getIDs();
            if (IDs.isEmpty()) {
                database.moviesDAO().insertMovie(mMovies);
                star.setImageResource(R.drawable.ic_star_24dp);
            }
            if (!(IDs.contains(id))) {
                database.moviesDAO().insertMovie(mMovies);
                star.setImageResource(R.drawable.ic_star_24dp);
            } else {
                database.moviesDAO().deleteMovie(ID);
                star.setImageResource(R.drawable.ic_star_border_24dp);

            }
            return movie;
        }

        @Override
        protected void onPostExecute(Movie movie) {
            super.onPostExecute(movie);
        }
    }

    private class FetchTrailer extends AsyncTask<String, Void, ArrayList<Trailer>> {

        @Override
        protected ArrayList<Trailer> doInBackground(String... strings) {

            URL trailersRequestUrl = NetworkUtils.buildUrl(id, "videos");
            try {
                String jsonTrailerResponse = NetworkUtils.getResponseFromHttpUrl(trailersRequestUrl);


                mTrailers = MovieJsonUtils.parseMovieTrailer(jsonTrailerResponse);
                return mTrailers;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Trailer> trailers) {
            super.onPostExecute(trailers);
            if (trailers != null) {
                trailersAdapter.setTrailersData(trailers);
            }
        }
    }

    private class FetchReview extends AsyncTask<String, Void, ArrayList<Review>> {

        @Override
        protected ArrayList<Review> doInBackground(String... strings) {

            URL reviewsRequestUrl = NetworkUtils.buildUrl(id, "reviews");
            try {
                String jsonReviewResponse = NetworkUtils.getResponseFromHttpUrl(reviewsRequestUrl);


                mReviews = MovieJsonUtils.parseMovieReview(jsonReviewResponse);
                return mReviews;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Review> reviews) {
            super.onPostExecute(reviews);
            if (reviews != null) {
                reviewsAdapter.setReviewsData(reviews);
            }
        }
    }
}
