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

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.originalTitleLabel)
    TextView originalTitleLabel;
    @BindView(R.id.originalTitle)
    TextView originalTitle;
    @BindView(R.id.moviePoster)
    TextView moviePoster;
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

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String movies = JsonUtils.BASE_URL + JsonUtils.POSTER_SIZE + moviePoster;
        // Move from one sandwich to the other
        String json = movies[position];
        Movie movie = (Movie) (JsonUtils.parseMovieJson(json);
        if (movie == null) {
            // Movie data unavailable
            closeOnError();
            return;
        }

        populateDetailActivity(movie);
        Picasso.with(this)
                .load(movie.getPosterPath())
                .into((Target) moviePoster);

        setTitle(movie.getOriginalTitle());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
    }

    private void populateDetailActivity(Movie movie) {

        if (movie == null) {
            return;
        }

        if (movie.getOriginalTitle() == null) {
            originalTitleLabel.setVisibility(View.GONE);
            originalTitle.setVisibility(View.GONE);
        } else {
            originalTitle.setText(movie.getOriginalTitle());
        }

        if (movie.getPosterPath() == null) {
            moviePoster.setVisibility(View.GONE);
        } else {
            moviePoster.setText(movie.getPosterPath());
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

