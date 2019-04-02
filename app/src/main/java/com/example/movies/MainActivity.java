package com.example.movies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.movies.utilities.MovieJsonUtils;
import com.example.movies.utilities.NetworkUtils;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements PosterAdapter.PosterAdapterOnClickHandler {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.error_message)
    TextView mErrorMessageDisplay;
    @BindView(R.id.progress_bar)
    ProgressBar mLoadingIndicator;

    private PosterAdapter posterAdapter;
    private ArrayList<Movie> simpleJsonMoviesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        posterAdapter = new PosterAdapter(this, this);
        recyclerView.setAdapter(posterAdapter);

        loadMoviesData("popular");
    }

    private void loadMoviesData(String sortBy) {
        showMovieDataView();
        new FetchMovie(this).execute(sortBy);
    }

    private void showMovieDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        recyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public void onClick(int adapterPosition) {
        Context context = this;
        Class destinationClass = DetailsActivity.class;
        Intent intentToStartDetailsActivity = new Intent(context, destinationClass);
        intentToStartDetailsActivity.putExtra(Intent.EXTRA_TEXT, adapterPosition);
        intentToStartDetailsActivity.putParcelableArrayListExtra("Movie", simpleJsonMoviesData);

        startActivity(intentToStartDetailsActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.top_rated) {
            posterAdapter.setMoviesData(null);
            loadMoviesData("top_rated");
            return true;
        }
        if (itemId == R.id.popular) {

            posterAdapter.setMoviesData(null);
            loadMoviesData("popular");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static class FetchMovie extends AsyncTask<String, Void, ArrayList<Movie>> {

        private final WeakReference<MainActivity> activityReference;


        FetchMovie(MainActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
            final MainActivity activity = activityReference.get();
            if (activity != null) {
                super.onPreExecute();
                activity.mLoadingIndicator.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... strings) {
            final MainActivity activity = activityReference.get();
            if (activity == null || activity.isFinishing()) {
                return null;
            }

            if (strings.length == 0) {
                return null;
            }
            String sort_by = strings[0];
            URL movieRequestUrl = NetworkUtils.buildUrl(sort_by);
            try {
                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieRequestUrl);

                activity.simpleJsonMoviesData = MovieJsonUtils.
                        parseMovieJson(jsonMovieResponse);

                return activity.simpleJsonMoviesData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            final MainActivity activity = activityReference.get();
            if (activity != null) {
                activity.mLoadingIndicator.setVisibility(View.INVISIBLE);
                if (movies != null) {
                    activity.showMovieDataView();
                    activity.posterAdapter.setMoviesData(movies);
                } else {
                    activity.showErrorMessage();
                }
            }
        }
    }
}

