package com.example.pb0386.nimblewirelesstask.Database;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;
import android.content.Context;

import com.example.pb0386.nimblewirelesstask.Models.Movie;

public class MovieDataSourceFactory extends DataSource.Factory<Integer, MovieList>  {
    private Context ctx;
    private MovieDataSource couponsDataSource;

    public MovieDataSourceFactory(Context ctx){
        this.ctx = ctx;
    }
    @Override
    public DataSource<Integer, MovieList> create()
    {
        if(couponsDataSource == null){
            couponsDataSource = new MovieDataSource(ctx);
        }
        return couponsDataSource;
    }
}