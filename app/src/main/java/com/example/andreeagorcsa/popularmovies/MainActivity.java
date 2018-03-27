package com.example.andreeagorcsa.popularmovies;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.andreeagorcsa.popularmovies.MovieAdapter;


import org.parceler.Parcels;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MainActivity extends AppCompatActivity implements MovieAdapter.ItemClickHandler {

    public static final String LOG_TAG = MainActivity.class.getName();

    public static final String MOVIE_OBJECT_FOR_PARCEL = "movie_object";

    private static final String SHARED_PREFERENCES_KEY = "shared_preferences_key";
    private static final String SORT_KEY = "sort_key";

    private RecyclerView mRecyclerView;

    private MovieAdapter mMovieAdapter;
    private List<Movie> mMovieList;

    private String sortType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycleView);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 2 : 4));

        mMovieAdapter = new MovieAdapter(MainActivity.this);
        mRecyclerView.setAdapter(new MovieAdapter(MainActivity.this));
        mMovieAdapter.setmMovieList(mMovieList);

        sortType = getSharedPreferences(SHARED_PREFERENCES_KEY, 0).getString(SORT_KEY, JsonUtils.POPULARITY);
        new MovieAsyncTask().execute(JsonUtils.POPULARITY);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    /*
    * Callback interface
    * Invoked when movie item from rcv is clicked to start DetailsActivity and pass in movie object as a parcel
    * */
    @Override
    public void onItemClick(Movie movie) {
        Intent i = new Intent(MainActivity.this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(MOVIE_OBJECT_FOR_PARCEL, Parcels.wrap(movie));
        i.putExtras(bundle);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        //set menu item title based on sort key
        if (sortType.equals(JsonUtils.USER_RATING)) {
            menu.getItem(0).setTitle(getResources().getString(R.string.sort_action_highest_rated));
        } else {
            menu.getItem(0).setTitle(getResources().getString(R.string.sort_action_most_popular));
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //fetch movies list based on selected sort key
        switch (item.getItemId()) {
            case R.id.highest_rated:
                sortType = JsonUtils.USER_RATING;
                JsonUtils.fetchMovieData(sortType);
                mMovieAdapter.notifyDataSetChanged();
                item.setTitle(getResources().getString(R.string.sort_action_highest_rated));
                return true;
            case R.id.most_popular:
                sortType = JsonUtils.POPULARITY;
                JsonUtils.fetchMovieData(sortType);
                mMovieAdapter.notifyDataSetChanged();
                item.setTitle(getResources().getString(R.string.sort_action_most_popular));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class MovieAsyncTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected void onPreExecute() {
            mMovieAdapter = (MovieAdapter) mRecyclerView.getAdapter();
        }

        @Override
        protected List<Movie> doInBackground(String... url) {
            try {
                String popularityUrl = JsonUtils.buildUrl(JsonUtils.POPULARITY);
                mMovieList = JsonUtils.fetchMovieData(popularityUrl);

                Thread.sleep(1000);
                } catch (IOException e) {
                 e.printStackTrace();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return mMovieList;
        }


        @Override
        protected void onPostExecute(List<Movie> movies) {
            mMovieList = movies;
            mMovieAdapter.setmMovieList(movies);
        }
    }
}

