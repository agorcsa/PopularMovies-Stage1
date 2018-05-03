package com.example.andreeagorcsa.popularmovies.Data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.andreeagorcsa.popularmovies.Models.Movie;

/**
 * Created by andreeagorcsa on 2018. 05. 03..
 */

public class MovieDbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context, name, factory, version, errorHandler);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
                MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieContract.MovieEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL," +
                MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW +" TEXT,"+
                MovieContract.MovieEntry.COLUMN_POSTER_PATH +" TEXT,"+
                MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE +" TEXT,"+
                MovieContract.MovieEntry.COLUMN_RELEASE_DATE+" TEXT,"+
                MovieContract.MovieEntry.COLUMN_MOVIE_FAV + " INTEGER NOT NULL DEFAULT 0" +
                ");";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
    onCreate(db);
    }
}
