package com.example.andreeagorcsa.popularmovies.TabFragments;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.io.IOException;
import java.util.List;

public class BaseFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private List<Movie> mMovieList;

    // Empty mandatory constructor for instantiating the fragment
    public BaseFragment() {}


    /**
     * Inflates the fragment layout
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the fragment layout
        View rootView = inflater.inflate(R.layout.movie_item, container, false);

        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        // Get reference to the ImageView from the fragment layout
        ImageView imageView = rootView.findViewById(R.id.movie_item_border);

        // Return the View that we have just created
        return rootView;
    }

    /**
     * Runs the fetchMovieData(moviesUrl) method at the background thread
     */
    public class MovieAsyncTask extends AsyncTask<String, Void, List<Movie>> {

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
                Toast.makeText(getContext(), "Your movie list is empty", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
