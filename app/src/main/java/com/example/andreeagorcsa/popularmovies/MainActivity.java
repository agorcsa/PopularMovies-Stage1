package com.example.andreeagorcsa.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.andreeagorcsa.popularmovies.Adapters.MovieAdapter;
import com.example.andreeagorcsa.popularmovies.Adapters.MoviesPagerAdapter;
import com.example.andreeagorcsa.popularmovies.Models.Movie;
import com.example.andreeagorcsa.popularmovies.TabFragments.BaseFragment;
import com.example.andreeagorcsa.popularmovies.TabFragments.BaseFragment.MovieAsyncTask;
import com.example.andreeagorcsa.popularmovies.Utils.JsonUtils;


import org.parceler.Parcels;

import java.util.List;


public class MainActivity extends AppCompatActivity implements MovieAdapter.ItemClickHandler {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add a Toolbar
        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));


        // Add TabLayout (POPULAR, TOP RATED, FAVOURITE)
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);

        // Add ViewPager
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

        // Create a new BaseFragment instance and display it using the FragmentManager
        BaseFragment baseFragment = new BaseFragment();
        // Use a FragmentManager and a FragmentManager to add the fragment on the screen
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, baseFragment)
                .commit();

        // Sort type is set to "popularity"
        sortType = getSharedPreferences(SHARED_PREFERENCES_KEY, 0).getString(SORT_KEY, JsonUtils.POPULARITY);

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

}

