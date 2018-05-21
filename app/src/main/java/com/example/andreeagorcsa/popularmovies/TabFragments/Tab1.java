package com.example.andreeagorcsa.popularmovies.TabFragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.andreeagorcsa.popularmovies.Adapters.MovieAdapter;
import com.example.andreeagorcsa.popularmovies.Models.Movie;
import com.example.andreeagorcsa.popularmovies.R;

import java.util.List;

public class Tab1 extends BaseFragment {

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private List<Movie> mMovieList;

    // Empty mandatory constructor for instantiation Tab1
    public Tab1() {}

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mMovieAdapter = new MovieAdapter((MovieAdapter.ItemClickHandler) Tab1.this);
        mRecyclerView.setAdapter(mMovieAdapter);




    }


}
