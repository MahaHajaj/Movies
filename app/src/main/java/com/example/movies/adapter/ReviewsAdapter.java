package com.example.movies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.movies.R;
import com.example.movies.models.Review;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {

    private final Context context;
    private ArrayList<Review> reviews;

    public ReviewsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int layoutIdForListItem = R.layout.reviews;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder reviewViewHolder, int i) {
        Review review = reviews.get(i);
        reviewViewHolder.auther.setText(review.auther);
        reviewViewHolder.content.setText(review.review);

    }

    @Override
    public int getItemCount() {
        if (null == reviews) return 0;
        return reviews.size();
    }

    public void setReviewsData(ArrayList<Review> review) {
        reviews = review;
        notifyDataSetChanged();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.auther)
        TextView auther;
        @BindView(R.id.content)
        TextView content;

        private ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
