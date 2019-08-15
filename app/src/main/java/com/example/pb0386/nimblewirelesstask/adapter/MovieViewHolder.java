package com.example.pb0386.nimblewirelesstask.adapter;

import android.arch.lifecycle.ViewModel;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pb0386.nimblewirelesstask.Database.MovieList;
import com.example.pb0386.nimblewirelesstask.R;

public class MovieViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout moviesLayout;
    public TextView movieTitle;
    public TextView data;
    public TextView movieDescription;
    public TextView rating;

    public MovieViewHolder(View view) {
        super(view);

        moviesLayout = (LinearLayout) view.findViewById(R.id.movies_layout);
        movieTitle = (TextView) view.findViewById(R.id.title);
        data = (TextView) view.findViewById(R.id.subtitle);
        movieDescription = (TextView) view.findViewById(R.id.description);
        rating = (TextView) view.findViewById(R.id.rating);
    }

    public void bindTO(MovieList movies){
        movieTitle.setText(movies.getTitle());
        data.setText(movies.getRelease_date());
        movieDescription.setText(movies.getOverview());
        rating.setText(movies.getVote_average().toString());
    }

}