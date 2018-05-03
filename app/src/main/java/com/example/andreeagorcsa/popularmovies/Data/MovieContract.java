package com.example.andreeagorcsa.popularmovies.Data;

import android.net.Uri;
import android.provider.BaseColumns;

import com.example.andreeagorcsa.popularmovies.Models.Movie;

import java.net.URI;
import java.net.URL;

/**
 * Created by andreeagorcsa on 2018. 05. 03..
 */

public class MovieContract {
    public static final String AUTHORITY = "com.example.andreeagorcsa.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH = "movie";

    private MovieContract(){}

    public static class MovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendEncodedPath(PATH).build();

        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_MOVIE_TITLE = "movie_title";
        public static final String COLUMN_MOVIE_OVERVIEW = "movie_overview";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_VOTE_AVERAGE= "vote_average";
        public static final String COLUMN_MOVIE_FAV = "favourite";
    }
}
