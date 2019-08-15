package com.example.pb0386.nimblewirelesstask.adapter;

import android.app.Activity;
import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pb0386.nimblewirelesstask.Database.MovieList;
import com.example.pb0386.nimblewirelesstask.Models.Movie;
import com.example.pb0386.nimblewirelesstask.R;
import com.example.pb0386.nimblewirelesstask.activity.MainActivity;

import java.util.List;

public class MovieAdapter extends PagedListAdapter<MovieList, MovieViewHolder> {

    public MovieAdapter() {
        super(DIFF_CALLBACK);
    }

    public static DiffUtil.ItemCallback<MovieList> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<MovieList>() {
                @Override
                public boolean areItemsTheSame(@NonNull MovieList oldCoupon,
                                               @NonNull MovieList newCoupon) {
                    return oldCoupon.getId() == newCoupon.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull MovieList oldCoupon,
                                                  @NonNull MovieList newCoupon) {
                    return oldCoupon.equals(newCoupon);
                }
            };

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View view = li.inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {
        final MovieList movieList = getItem(position);
        if(movieList != null) {
            holder.bindTO(movieList);
        }
    }

}
