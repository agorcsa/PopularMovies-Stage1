package com.example.andreeagorcsa.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.w3c.dom.Text;

import java.lang.reflect.Array;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andreeagorcsa on 2018. 03. 05..
 */

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.originalTitleLabel)
    TextView originalTitleLabel;
    @BindView(R.id.originalTitle)
    TextView originalTitle;
    @BindView(R.id.moviePoster)
    ImageView moviePoster;
    @BindView(R.id.plotSynopsisLabel)
    TextView plotSynopsisLabel;
    @BindView(R.id.plotSynopsis)
    TextView plotSynopsis;
    @BindView(R.id.userRatingLabel)
    TextView userRatingLabel;
    @BindView(R.id.userRating)
    TextView userRating;
    @BindView(R.id.releaseDateLabel)
    TextView releaseDateLabel;
    @BindView(R.id.releaseDate)
    TextView releaseDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        Movie currentMovie = bundle.getParcelable(MainActivity.MOVIE_OBJECT_FOR_PARCEL);
        if (currentMovie == null) {
            closeOnError();
        }

        populateDetailActivity(currentMovie);
        Picasso.with(this)
                .load(JsonUtils.POSTER_BASE_URL + JsonUtils.POSTER_SIZE + currentMovie.getPosterPath())
                .into(moviePoster);

        setTitle(currentMovie.getOriginalTitle());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
    }

    private void populateDetailActivity(Movie movie) {

        if (movie == null) {
            return;
        }

        if (movie.getOverview() == null) {
            plotSynopsisLabel.setVisibility(View.GONE);
            plotSynopsis.setVisibility(View.GONE);
        } else {
            plotSynopsis.setText(movie.getOverview());
        }

        if (movie.getVoteAverage() == null) {
            userRatingLabel.setVisibility(View.GONE);
            userRating.setVisibility(View.GONE);
        } else {
            userRating.setText(movie.getVoteAverage());
        }

        if (movie.getReleaseDate() == null) {
            releaseDateLabel.setVisibility(View.GONE);
            releaseDate.setVisibility(View.GONE);
        } else {
            releaseDate.setText(movie.getReleaseDate());
        }

    }
}

