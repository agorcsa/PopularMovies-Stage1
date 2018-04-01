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
import android.view.MenuInflater;
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
    private static final String MOVIE_STATE_KEY = "movie_list";

    private static final String SHARED_PREFERENCES_KEY = "shared_preferences_key";
    private static final String SORT_KEY = "sort_key";

    private static final String HIGHEST_RATED = "top_rated";
    private static final String POPULAR = "popular";

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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MOVIE_STATE_KEY, Parcels.wrap(mMovieList));
    }

    /*
        * Callback interface
        * Invoked when movie item from recycleview is clicked to start DetailsActivity and pass in movie object as a parcel
        * */
    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(MOVIE_OBJECT_FOR_PARCEL, Parcels.wrap(movie));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        if (sortType.equals(POPULAR)) {
            menu.getItem(0).setTitle(getResources().getString(R.string.sort_action_most_popular));
        } else {
            menu.getItem(1).setTitle(getResources().getString(R.string.sort_action_highest_rated));
        }
        return super.onCreateOptionsMenu(menu);
    }


    private void updateSortKey() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_KEY, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SORT_KEY, sortType);
        editor.apply();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //fetch movies list based on selected sort ket and persist sort key and update menu item text
        switch (item.getItemId()) {
            case R.id.sort_highest_rated:
                    sortType = HIGHEST_RATED;
                try {
                    JsonUtils.fetchMovieData(JsonUtils.buildUrl(sortType));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mMovieAdapter.notifyDataSetChanged();
                    item.setTitle(getResources().getString(R.string.sort_action_highest_rated));
                    return true;
            case R.id.sort_most_popular:
                    sortType = POPULAR;
                try {
                    JsonUtils.fetchMovieData(JsonUtils.buildUrl(sortType));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mMovieAdapter.notifyDataSetChanged();
                    item.setTitle(getResources().getString(R.string.sort_action_most_popular));
                return true;
        }
        updateSortKey();
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
            if (movies != null) {
                mMovieAdapter.setmMovieList(movies);
            } else {
                throw new NullPointerException("Your movie list is empty");
            }
        }
    }
}

