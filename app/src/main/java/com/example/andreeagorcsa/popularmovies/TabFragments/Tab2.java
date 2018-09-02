package com.example.andreeagorcsa.popularmovies.TabFragments;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.andreeagorcsa.popularmovies.Adapters.MovieAdapter;
import com.example.andreeagorcsa.popularmovies.Models.Movie;
import com.example.andreeagorcsa.popularmovies.R;
import com.example.andreeagorcsa.popularmovies.Utils.JsonUtils;

import java.util.List;

public class Tab2 extends BaseFragment {

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private List<Movie> mMovieList;

    private String sortType;

    public Tab2() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Add a GridlayoutManager to the RecyclerView
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 2 : 4) );

        // Add the MovieAdapter
        mMovieAdapter = new MovieAdapter((MovieAdapter.ItemClickHandler) Tab2.this);
        mRecyclerView.setAdapter(mMovieAdapter);
        mMovieAdapter.setmMovieList(mMovieList);

        // Checking for Internet connection
        ConnectivityManager connectivityManager =  (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected == true) {
            // Running a new AsyncTask with the key word POPULARITY
            new MovieAsyncTask().execute(JsonUtils.TOP_RATED);
        } else {
            Toast.makeText(getActivity(), "No Internet connection", Toast.LENGTH_LONG).show();
        }
    }
}
