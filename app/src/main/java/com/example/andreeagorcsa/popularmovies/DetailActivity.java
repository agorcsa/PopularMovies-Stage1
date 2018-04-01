package com.example.andreeagorcsa.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andreeagorcsa.popularmovies.Models.Movie;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andreeagorcsa on 2018. 03. 05..
 */

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.originalTitle)
    TextView originalTitle;
    @BindView(R.id.moviePoster)
    ImageView moviePoster;
    @BindView(R.id.plotSynopsis)
    TextView plotSynopsis;
    @BindView(R.id.userRating)
    TextView userRating;
    @BindView(R.id.releaseDate)
    TextView releaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        Movie currentMovie = Parcels.unwrap(getIntent().getParcelableExtra(MainActivity.MOVIE_OBJECT_FOR_PARCEL));

        if (currentMovie == null) {
            closeOnError();
        }

        populateDetailActivity(currentMovie);
        Picasso.with(this)
                .load(currentMovie.getPosterPath())
                .into(moviePoster);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
    }

    private void populateDetailActivity(Movie movie) {

        if (movie == null) {
            return;
        }
        originalTitle.setText(movie.getOriginalTitle());
        plotSynopsis.setText(movie.getOverview());
        userRating.setText(movie.getVoteAverage());
        releaseDate.setText(movie.getReleaseDate());
    }
}

