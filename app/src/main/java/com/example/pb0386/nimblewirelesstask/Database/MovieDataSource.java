package com.example.pb0386.nimblewirelesstask.Database;

import android.app.Application;
import android.arch.paging.PageKeyedDataSource;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.pb0386.nimblewirelesstask.Models.Movie;
import com.example.pb0386.nimblewirelesstask.Models.MoviesResponse;
import com.example.pb0386.nimblewirelesstask.rest.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDataSource extends PageKeyedDataSource<Integer, MovieList> {

    private UserDao userDao;

    public MovieDataSource(Context ctx){
        userDao = Room.databaseBuilder(ctx, UserDatabase.class, "nimble-database.db")
                .allowMainThreadQueries()
                .build()
                .getUserDao();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, MovieList> callback) {
        List<MovieList> cpns = userDao.getallMovie(0, params.requestedLoadSize);

        int noOfTryies = 0;
        while(cpns.size() == 0){
            cpns = userDao.getallMovie(0, params.requestedLoadSize);
            noOfTryies++;
            if(noOfTryies == 6){
                break;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {  }
        }

        callback.onResult(cpns,null, cpns.size()+1);
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, MovieList> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, MovieList> callback) {

        List<MovieList> cpns = userDao.getallMovie( params.key, params.requestedLoadSize);
        int nextKey = params.key+cpns.size();
        callback.onResult(cpns, nextKey);

    }
}
