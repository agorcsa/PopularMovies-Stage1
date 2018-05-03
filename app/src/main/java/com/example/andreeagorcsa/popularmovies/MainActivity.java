package com.example.andreeagorcsa.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.andreeagorcsa.popularmovies.Adapters.MovieAdapter;
import com.example.andreeagorcsa.popularmovies.Adapters.MoviesPagerAdapter;
import com.example.andreeagorcsa.popularmovies.Models.Movie;
import com.example.andreeagorcsa.popularmovies.TabFragments.Tab1;
import com.example.andreeagorcsa.popularmovies.TabFragments.Tab2;
import com.example.andreeagorcsa.popularmovies.TabFragments.Tab3;
import com.example.andreeagorcsa.popularmovies.Utils.JsonUtils;


import org.parceler.Parcels;

import java.io.IOException;
import java.util.List;


public class MainActivity extends AppCompatActivity implements MovieAdapter.ItemClickHandler, Tab1.OnFragmentInteractionListener, Tab2.OnFragmentInteractionListener, Tab3.OnFragmentInteractionListener{

    public static final String LOG_TAG = MainActivity.class.getName();

    public static final String MOVIE_OBJECT_FOR_PARCEL = "movie_object";

    private static final String MOVIE_STATE_KEY = "movie_list";
    private static final String SHARED_PREFERENCES_KEY = "shared_preferences_key";
    // Declaration of the sort keys as constants
    private static final String SORT_KEY = "sort_key";
    private static final String TOP_RATED = "top_rated";
    private static final String POPULAR = "popular";

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private List<Movie> mMovieList;

    private String sortType;
    private ImageView movieRoll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Add a Toolbar
        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));
        movieRoll = findViewById(R.id.movie_roll);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("POPULAR"));
        tabLayout.addTab(tabLayout.newTab().setText("TOP RATED"));
        tabLayout.addTab(tabLayout.newTab().setText("FAVOURITE"));
        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.viewPager);
        final MoviesPagerAdapter moviesPagerAdapter = new MoviesPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(moviesPagerAdapter);


        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        // Movie RecyclerView, Adapter and LayoutManager
        mRecyclerView = findViewById(R.id.recycleView);
        mRecyclerView.setHasFixedSize(true);
        // Add a GridlayoutManager to the RecyclerView
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 2 : 4));
        mMovieAdapter = new MovieAdapter(MainActivity.this);
        mRecyclerView.setAdapter(new MovieAdapter(MainActivity.this));
        mMovieAdapter.setmMovieList(mMovieList);
        // Sort type is set to "popularity"
        sortType = getSharedPreferences(SHARED_PREFERENCES_KEY, 0).getString(SORT_KEY, JsonUtils.POPULARITY);

        // Checking for Internet connection
        ConnectivityManager connectivityManager =  (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected == true) {
        // Running a new AsyncTask
        new MovieAsyncTask().execute(JsonUtils.POPULARITY);
        } else {
            Toast.makeText(getApplicationContext(), "No Internet connection", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * save the movie list, by wrapping the list of movies into a parcel
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MOVIE_STATE_KEY, Parcels.wrap(mMovieList));
    }

    /**
     * When the movies is being clicked, opens DetailActivity
     * saves movie into a parcel which will be send to DetailActivity
     *
     * @param movie
     */
    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(MOVIE_OBJECT_FOR_PARCEL, Parcels.wrap(movie));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * crete menu options ("most popular" and "highest rated")
     *
     * @param menu
     * @return
     */
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

    /**
     * Updates the sort_key to the current value after the user clicks one menu option
     */
    private void updateSortKey() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_KEY, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SORT_KEY, sortType);
        editor.apply();
    }

    /**
     * Updates and displays the movie list,
     * according to the menu option that has been clicked
     *
     * @param item
     * @return onOptionsItemSelected(item)
     */
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * Runs the fetchMovieData(moviesUrl) method at the background thread
     */
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
                Toast.makeText(getApplicationContext(), "Your movie list is empty", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

