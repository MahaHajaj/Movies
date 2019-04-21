package com.example.movies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.movies.R;
import com.example.movies.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.PostersViewHolder> {
    private static final String POSTER_URL =
            "https://image.tmdb.org/t/p/w185/";
    private final PosterAdapterOnClickHandler mClickHandler;
    private final Context context;
    private ArrayList<Movie> mMovies;

    public PosterAdapter(Context context, PosterAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
        this.context = context;
    }

    @NonNull
    @Override
    public PostersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int layoutIdForListItem = R.layout.movie_grid_list;
        LayoutInflater inflater = LayoutInflater.from(context);


        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new PostersViewHolder(view);
    }

    public void onBindViewHolder(@NonNull PostersViewHolder postersViewHolder, int position) {
        Movie movie = mMovies.get(position);
        Picasso.with(context)
                .load(POSTER_URL + movie.moviePoster)
                .into(postersViewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        if (null == mMovies) return 0;
        return mMovies.size();
    }

    public void setMoviesData(ArrayList<Movie> movie) {
        mMovies = movie;
        notifyDataSetChanged();
    }

    public interface PosterAdapterOnClickHandler {
        void onClick(int adapterPosition);
    }

    public class PostersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.movie_grid_poster)
        ImageView imageView;

        private PostersViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);
        }
    }
}