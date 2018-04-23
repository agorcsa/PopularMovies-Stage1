package com.example.andreeagorcsa.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andreeagorcsa.popularmovies.Adapters.ReviewAdapter;
import com.example.andreeagorcsa.popularmovies.Adapters.TrailerAdapter;
import com.example.andreeagorcsa.popularmovies.Models.Movie;
import com.example.andreeagorcsa.popularmovies.Models.Review;
import com.example.andreeagorcsa.popularmovies.Models.Trailer;
import com.example.andreeagorcsa.popularmovies.Utils.JsonUtils;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andreeagorcsa on 2018. 03. 05..
 */

public class DetailActivity extends AppCompatActivity implements TrailerAdapter.ItemClickListener{

    public final static String LOG_TAG = DetailActivity.class.getSimpleName();

    private String mId;

    List<Review> reviewList;
    List<Trailer> trailerList;

    ReviewAdapter mReviewAdapter;
    TrailerAdapter mTrailerAdapter;

    @BindView(R.id.reviewRecycleView)
    RecyclerView mReviewRecyclerView;

    @BindView(R.id.trailerRecyclerView)
    RecyclerView mTrailerRecyclerView;

    // Binding Views with ButterKnife
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

    @BindView(R.id.reviewAuthor)
    TextView reviewAuthor;
    @BindView(R.id.reviewContent)
    TextView reviewContent;

    @BindView(R.id.trailerKey)
    TextView trailerKey;
    @BindView(R.id.trailerName)
    TextView trailerName;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        ButterKnife.bind(this);

        mReviewRecyclerView.setHasFixedSize(true);
        mReviewAdapter = new ReviewAdapter(context);
        mReviewRecyclerView.setAdapter(mReviewAdapter);
        RecyclerView.LayoutManager layoutManagerReview = new LinearLayoutManager(context);
        mReviewRecyclerView.setLayoutManager(layoutManagerReview);

        mTrailerRecyclerView.setHasFixedSize(true);
        mTrailerAdapter = new TrailerAdapter(context);
        mTrailerRecyclerView.setAdapter(mTrailerAdapter);
        RecyclerView.LayoutManager layoutManagerTrailer = new LinearLayoutManager(context);
        mTrailerRecyclerView.setLayoutManager(layoutManagerTrailer);


        // Get the Movie Object from the Parcel
        Movie currentMovie = Parcels.unwrap(getIntent().getParcelableExtra(MainActivity.MOVIE_OBJECT_FOR_PARCEL));


        // if the movie object is null, finish and display a toast message
        if (currentMovie == null) {
            closeOnError();
        }

        // Get the movieId from the Movie object
        final int movieId = currentMovie.getMovieId();
        final String id = Integer.toString(movieId);
        mId = id;


        // Display movie poster using Picasso
        populateMovie(currentMovie);
        Picasso.with(this)
                .load(currentMovie.getPosterPath())
                .into(moviePoster);

        //populateReview(movieId);
        //populateTrailer(movieId);

        // Checking for Internet connection
        ConnectivityManager connectivityManager =  (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected == true) {
            new TrailerAsyncTask().execute(mId);
            new ReviewAsyncTask().execute(mId);
        }
    }

    @Override
    public void onItemClick(String key) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + key) );
        try {
            startActivity(intent);
        }catch (ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "Could not play trailer", Toast.LENGTH_SHORT).show();
        }
    }


    // AsyncTask for extracting the Json data for the Reviews
    class ReviewAsyncTask extends AsyncTask<String, Void, List<Review>> {

        @Override
        protected void onPreExecute() {
            mReviewAdapter = (ReviewAdapter)mReviewRecyclerView.getAdapter();
        }

        @Override
        protected List<Review> doInBackground(String... url) {
            try {
                String reviewUrl = JsonUtils.buildReviewUrl(mId);
                reviewList = JsonUtils.fetchReviewData(reviewUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return reviewList;
        }

        @Override
        protected void onPostExecute(List<Review> reviews) {
            reviewList = reviews;
            mReviewAdapter.setReviewList(reviews);
        }
    }

    class TrailerAsyncTask extends AsyncTask<String, Void, List<Trailer>> {

        @Override
        protected void onPreExecute() {
            mTrailerAdapter = (TrailerAdapter)mTrailerRecyclerView.getAdapter();
        }

        @Override
        protected List<Trailer> doInBackground(String... url) {
            try {
                String trailerUrl = JsonUtils.buildTrailerUrl(mId);
                trailerList = JsonUtils.fetchTrailerData(trailerUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return trailerList;
        }

        @Override
        protected void onPostExecute(List<Trailer> trailers) {
           trailerList = trailers;
           mTrailerAdapter.setmTrailerList(trailerList);
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
    }

    /**
     * Set the text parsed from Json into the corresponding TextView
     *
     * @param movie
     */
    private void populateMovie(Movie movie) {

        if (movie == null) {
            return;
        }
        originalTitle.setText(movie.getOriginalTitle());
        plotSynopsis.setText(movie.getOverview());
        userRating.setText(movie.getVoteAverage());
        releaseDate.setText(movie.getReleaseDate());
    }

    private void populateReview(Review review) {
        if (review == null) {
            return;
        }
        reviewAuthor.setText(review.getAuthor());
        reviewContent.setText(review.getContent());
    }

    private void populateTrailer(Trailer trailer) {
        if (trailer == null) {
            return;
        }
        trailerName.setText(trailer.getName());
        trailerKey.setText(trailer.getKey());
    }

}

