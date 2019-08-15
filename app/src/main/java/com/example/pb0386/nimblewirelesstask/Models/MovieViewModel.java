package com.example.pb0386.nimblewirelesstask.Models;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;

import com.example.pb0386.nimblewirelesstask.Database.MovieDataSource;
import com.example.pb0386.nimblewirelesstask.Database.MovieDataSourceFactory;
import com.example.pb0386.nimblewirelesstask.Database.MovieList;

public class MovieViewModel extends ViewModel {

    public LiveData<PagedList<MovieList>> movielist;

    public MovieViewModel(Application application){

        MovieDataSourceFactory factory = new MovieDataSourceFactory(application);

        PagedList.Config config = (new PagedList.Config.Builder()).setEnablePlaceholders(true)
                .setInitialLoadSizeHint(20)
                .setPageSize(25).build();

        movielist = new LivePagedListBuilder<>(factory, config).build();
    }

    public static class MovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {
        private Application mApplication;
        public MovieViewModelFactory(Application application) {
            mApplication = application;
        }
        @Override
        public <T extends ViewModel> T create(Class<T> viewModel) {
            return (T) new MovieViewModel(mApplication);
        }
    }
}
