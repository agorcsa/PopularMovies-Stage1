package com.example.andreeagorcsa.popularmovies;

import org.parceler.Parcel;

/**
 * Created by andreeagorcsa on 2018. 03. 07..
 */
@Parcel
public class Movie {

    private String originalTitle;
    private String posterPath;
    private String finalUrl;
    private String overview;
    private double voteAverage;
    private double popularity;
    private String releaseDate;

    public Movie() {}

    // Movie constructor
    public Movie(String originalTitle, String posterPath, String overview, double voteAverage, double popularity, String releaseDate) {
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
        this.releaseDate = releaseDate;
    }

    public String createFinalMoviePosterUrl() {
        finalUrl = JsonUtils.BASE_URL.concat(JsonUtils.POSTER_SIZE).concat(JsonUtils.POSTER_PATH);
        return finalUrl;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPoster(String moviePoster) {
        this.posterPath = moviePoster;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVoteAverage() {
        return "Rating: " + voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPopularity() {
        return "Popularity: " + popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    // Create a String representation of the Movie object
    @Override
    public String toString() {
        return "Movie{" +
                "poster_path='" + finalUrl + '\'' +
                ", overview='" + JsonUtils.PLOT_SYNOPSIS + '\'' +
                ", release_date='" + JsonUtils.RELEASE_DATE + '\'' +
                ", original_title='" + JsonUtils.ORIGINAL_TITLE + '\'' +
                ", popularity='" + JsonUtils.POPULARITY + '\'' +
                ", vote_average='" + JsonUtils.USER_RATING + '\'' +
                '}';
    }
}
