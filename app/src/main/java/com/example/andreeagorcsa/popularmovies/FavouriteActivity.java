package com.example.andreeagorcsa.popularmovies;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.andreeagorcsa.popularmovies.Adapters.MovieAdapter;
import com.example.andreeagorcsa.popularmovies.Data.MovieContract;
import com.example.andreeagorcsa.popularmovies.Models.Movie;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by andreeagorcsa on 2018. 05. 03..
 */
public class FavouriteActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, MovieAdapter.ItemClickHandler{

    public static final String LOG_TAG = FavouriteActivity.class.getSimpleName();
    public static final int LOADER_ID = 1000;

    public static final String EXTRA_OBJECT ="movie_object";
    public static final String MOVIES_STATE_KEY = "movies_list";

    @BindView(R.id.favouriteRecyclerView)
    RecyclerView recyclerView;

    MovieAdapter movieAdapter;
    List<Movie> movieList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this,
                (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 2 : 4));
        movieAdapter = new MovieAdapter(this);
        recyclerView.setAdapter(movieAdapter);
        movieList = Parcels.unwrap(savedInstanceState.getParcelable(MOVIES_STATE_KEY));
        movieAdapter.setmMovieList(movieList);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MOVIES_STATE_KEY, Parcels.wrap(movieList));
    }


    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(FavouriteActivity.this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_OBJECT, Parcels.wrap(movie));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                MovieContract.MovieEntry.COLUMN_MOVIE_FAV + "=?",
                new String[]{"1"},
                null);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        movieAdapter.setmMovieList(null);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        movieList = parseCursor(data);
        movieAdapter.setmMovieList(movieList);
    }

    private List<Movie> parseCursor(Cursor cursor){
        if (cursor == null) return null;
        List<Movie> movieList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Movie movie = new Movie();
            movie.setMovieId(cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID)));
            movie.setOriginalTitle(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE)));
            movie.setOverview(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW)));
            movie.setPosterPoster(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH)));
            movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE)));
            movie.setVoteAverage(cursor.getDouble(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE)));

            movieList.add(movie);

        }
        return movieList;
    }
}
