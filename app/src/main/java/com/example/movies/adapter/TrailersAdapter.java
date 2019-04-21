package com.example.movies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.movies.R;
import com.example.movies.models.Trailer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder> {

    private final TrailerAdapterOnClickHandler mClickHandler;

    private final Context context;
    private ArrayList<Trailer> trailers;

    public TrailersAdapter(TrailerAdapterOnClickHandler mClickHandler, Context context) {
        this.mClickHandler = mClickHandler;
        this.context = context;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int layoutIdForListItem = R.layout.trailers;
        LayoutInflater inflater = LayoutInflater.from(context);


        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder trailerViewHolder, int i) {
        Trailer trailer = trailers.get(i);
        trailerViewHolder.trailerName.setText(trailer.name);
    }

    @Override
    public int getItemCount() {
        if (null == trailers) return 0;
        return trailers.size();
    }

    public void setTrailersData(ArrayList<Trailer> trailer) {
        trailers = trailer;
        notifyDataSetChanged();
    }

    public interface TrailerAdapterOnClickHandler {
        void onClick(int adapterPosition);
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.trailers_name)
        TextView trailerName;

        private TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);
        }
    }
}
