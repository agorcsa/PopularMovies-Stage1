package com.example.andreeagorcsa.popularmovies.Models;

import com.example.andreeagorcsa.popularmovies.Utils.JsonUtils;

import org.parceler.Parcel;

/**
 * Created by andreeagorcsa on 2018. 03. 07..
 */
@Parcel
public class Movie {
    //Declaration of the Movie variables
    public int movieId;
    public String originalTitle;
    public String moviePoster;
    public String finalUrl;
    public String plotSynopsis;
    public double userRating;
    public double popularity;
    public String releaseDate;

    // Empty constructor for Parcel
    public Movie() {
    }

    /**
     * Movie constructor
     *
     * @param originalTitle
     * @param moviePoster
     * @param plotSynopsis
     * @param userRating
     * @param releaseDate
     */
    public Movie(int movieId, String originalTitle, String moviePoster, String plotSynopsis, double userRating, double popularity,String releaseDate) {
        this.movieId = movieId;
        this.originalTitle = originalTitle;
        this.moviePoster = moviePoster;
        this.plotSynopsis = plotSynopsis;
        this.userRating = userRating;
        this.popularity = popularity;
        this.releaseDate = releaseDate;
    }

    /**
     * Creates the Sring URL for the movie poster
     *
     * @return finalUrl
     */
    public String createFinalMoviePosterUrl() {
        finalUrl = JsonUtils.BASE_URL.concat(JsonUtils.POSTER_SIZE).concat(JsonUtils.POSTER_PATH);
        return finalUrl;
    }

    // Getter and Setter methods for the Movie parameters
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterPath() {
        return moviePoster;
    }

    public void setPosterPoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getOverview() {
        return "Plot Synopsis: " + "\n" + plotSynopsis;
    }

    public void setOverview(String overview) {
        this.plotSynopsis = plotSynopsis;
    }

    public String getVoteAverage() {
        return "User Rating: " + userRating;
    }

    public void setVoteAverage(double voteAverage) {
        this.userRating = voteAverage;
    }

    public String getReleaseDate() {
        return "Release Date: " + releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * @return String representation of the Movie object
     */
    @Override
    public String toString() {
        return "Movie{" +
                "poster_path='" + finalUrl + '\'' +
                ", overview='" + JsonUtils.OVERVIEW + '\'' +
                ", release_date='" + JsonUtils.RELEASE_DATE + '\'' +
                ", original_title='" + JsonUtils.ORIGINAL_TITLE + '\'' +
                ", vote_average='" + JsonUtils.VOTE_AVERAGE + '\'' +
                '}';
    }
}
