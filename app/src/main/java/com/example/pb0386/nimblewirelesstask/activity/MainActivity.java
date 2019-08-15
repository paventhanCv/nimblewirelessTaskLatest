package com.example.pb0386.nimblewirelesstask.activity;

import android.app.Application;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.pb0386.nimblewirelesstask.Database.MovieList;
import com.example.pb0386.nimblewirelesstask.Database.User;
import com.example.pb0386.nimblewirelesstask.Database.UserDao;
import com.example.pb0386.nimblewirelesstask.Database.UserDatabase;
import com.example.pb0386.nimblewirelesstask.Models.Movie;
import com.example.pb0386.nimblewirelesstask.Models.MovieViewModel;
import com.example.pb0386.nimblewirelesstask.Models.MoviesResponse;
import com.example.pb0386.nimblewirelesstask.R;
import com.example.pb0386.nimblewirelesstask.adapter.MovieAdapter;
import com.example.pb0386.nimblewirelesstask.rest.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final static String API_KEY = "8d757b4dfd0776e8ba9a3c3b473f5d51";
    private RecyclerView recyclerView;
    private Paint p = new Paint();
    ApiClient apiClient;
    private UserDao userDao;
    ProgressDialog progressDialog;
    List<MovieList> movieLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            userDao = Room.databaseBuilder(this, UserDatabase.class, "nimble-database.db")
                    .allowMainThreadQueries()
                    .build()
                    .getUserDao();


            apiClient = new ApiClient();
            recyclerView = findViewById(R.id.movies_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);

            new InsertDataAsynctask().execute();

        } catch (Exception e) {
            e.toString();
        }
    }

    public void showRecyclerviewAfterInsertingData() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });


    }

    public class InsertDataAsynctask extends AsyncTask<Void, String, String> {

        Call<MoviesResponse> call;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Loading");
            progressDialog.setMessage("Please wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {

            call = ApiClient.getInsance().getApi().getTopRatedMovies(API_KEY,1);
            String pav="";

            return null;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);

            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response <MoviesResponse> response) {
                    if (response.isSuccessful()){
                        if (response != null){
                            List<Movie> moviesList = response.body().getResults();
                            for (int i = 0; i < moviesList.size(); i++) {
                                String title = moviesList.get(i).getTitle();
                                String releasedate = moviesList.get(i).getReleaseDate();
                                String overview = moviesList.get(i).getOverview();
                                Double voteverage = moviesList.get(i).getVoteAverage();

                                MovieList movieList = new MovieList(title, releasedate, overview, voteverage);

                                if (movieList != null)
                                {
                                    userDao.insert(movieList);
                                }

                            }

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    new UpdateUiAsynctask().execute();
                                    progressDialog.dismiss();
                                }
                            }, 3000);

                        }
                    }
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    String pavu = "";
                    progressDialog.dismiss();
                }
            });

        }
    }


    public class UpdateUiAsynctask extends AsyncTask<Void, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {

            return null;
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);

            MovieViewModel viewModel = ViewModelProviders.of(MainActivity.this,
                    new MovieViewModel.MovieViewModelFactory(MainActivity.this.getApplication()))
                    .get(MovieViewModel.class);

            final MovieAdapter adapter = new MovieAdapter();
            recyclerView.setAdapter(adapter);

            viewModel.movielist.observe(MainActivity.this, new Observer<PagedList<MovieList>>() {
                @Override
                public void onChanged(@Nullable PagedList<MovieList> items) {
                    adapter.submitList(items);
                }
            });

            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

            DividerItemDecoration dividerItemDecoration =
                    new DividerItemDecoration(recyclerView.getContext(),
                            LinearLayoutManager.VERTICAL);
            recyclerView.addItemDecoration(dividerItemDecoration);

            initSwipe();
            progressDialog.dismiss();
        }
    }

    private void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {

                    progressDialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            MovieList user = new MovieList();
                            user.setId(position);
                            userDao.delete(user);

                        }
                    }, 1000);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new UpdateUiAsynctask().execute();
                            progressDialog.dismiss();
                        }
                    }, 1000);
                } else {
                    shareIntent();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX > 0) {
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.share);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    public void shareIntent() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        startActivity(Intent.createChooser(share, "Share link!"));
        finish();
    }

}
