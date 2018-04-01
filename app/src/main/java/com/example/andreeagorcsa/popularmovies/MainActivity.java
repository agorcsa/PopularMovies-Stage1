package com.example.andreeagorcsa.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.andreeagorcsa.popularmovies.Adapters.MovieAdapter;
import com.example.andreeagorcsa.popularmovies.Models.Movie;
import com.example.andreeagorcsa.popularmovies.Utils.JsonUtils;


import org.parceler.Parcels;

import java.io.IOException;
import java.util.List;


public class MainActivity extends AppCompatActivity implements MovieAdapter.ItemClickHandler {

    public static final String LOG_TAG = MainActivity.class.getName();
    public static final String MOVIE_OBJECT_FOR_PARCEL = "movie_object";

    private static final String MOVIE_STATE_KEY = "movie_list";
    private static final String SHARED_PREFERENCES_KEY = "shared_preferences_key";
    private static final String SORT_KEY = "sort_key";
    private static final String TOP_RATED = "top_rated";
    private static final String POPULAR = "popular";
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private List<Movie> mMovieList;
    private String sortType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));

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
        switch (item.getItemId()) {
            case R.id.sort_highest_rated:
                sortType = TOP_RATED;
                new MovieAsyncTask().execute(TOP_RATED);
                mMovieAdapter.notifyDataSetChanged();
                item.setTitle(getResources().getString(R.string.sort_action_highest_rated));
                return true;
            case R.id.sort_most_popular:
                sortType = POPULAR;
                new MovieAsyncTask().execute(POPULAR);
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
                //take the index 0 value of the String ellipse parameter no matter if it is "popular" or "top-rated"
                String moviesUrl = JsonUtils.buildUrl(url[0]);
                mMovieList = JsonUtils.fetchMovieData(moviesUrl);

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

