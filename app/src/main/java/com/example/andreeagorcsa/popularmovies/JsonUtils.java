package com.example.andreeagorcsa.popularmovies;

import android.net.Uri;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;


/**
 * Created by andreeagorcsa on 2018. 03. 07..
 */

public class JsonUtils {

    public static final String LOG_TAG = JsonUtils.class.getName();

    public static final String BASE_URL = "https://api.themoviedb.org/3";
    public static final String QUERY_PARAM = "api_key";
    public static final String API_KEY = "";

    public static final String RESULTS = "results";
    public static final String ORIGINAL_TITLE = "original_title";
    public static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    public static final String POSTER_SIZE = "w185";
    public static final String POSTER_PATH = "poster_path";
    public static final String PLOT_SYNOPSIS = "overview";

    public static final String POPULARITY = "popularity";
    public static final String USER_RATING = "vote_average";

    public static final String RELEASE_DATE = "release_date";


    public static String buildPopularityUrl() throws IOException {
        Uri.Builder popularityBuilder = new Uri.Builder();
        popularityBuilder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(POPULARITY)
                .appendQueryParameter(QUERY_PARAM, API_KEY)
                .build();
        String popularityUrl = popularityBuilder.build().toString();
        return popularityUrl;
    }

    public static String buildRatingUrl() throws IOException {
        Uri.Builder ratingBuilder = new Uri.Builder();
        ratingBuilder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(USER_RATING)
                .appendQueryParameter(QUERY_PARAM, API_KEY)
                .build();
        String ratingUrl = ratingBuilder.build().toString();
        return ratingUrl;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(MainActivity.LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(MainActivity.LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(MainActivity.LOG_TAG, "Problem retrieving the movies JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    public static List<Movie> parseMovieJson(String json) {

        // Checking if the json is empty
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        List<Movie> movieList = new ArrayList<>();

        try {
            JSONObject rootObject = new JSONObject(json);
            JSONArray resultsArray = rootObject.optJSONArray(RESULTS);

            // Looping in the resultsArray
            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject movieObject = resultsArray.optJSONObject(i);

                String originalTitle = movieObject.optString(ORIGINAL_TITLE);

                String moviePoster = movieObject.optString(POSTER_PATH);

                String plotSynopsis = movieObject.optString(PLOT_SYNOPSIS);

                double rating = movieObject.optDouble(USER_RATING);

                double popularity = movieObject.optDouble(POPULARITY);

                String releaseDate = movieObject.optString(RELEASE_DATE);

                Movie movie = new Movie(originalTitle, POSTER_BASE_URL + POSTER_SIZE + moviePoster, plotSynopsis, rating, popularity, releaseDate);
                movieList.add(movie);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieList;
    }

    /**
     * Query the themovieDB API and return a list of {@link Movie} objects.
     */
    public static List<Movie> fetchMovieData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);
        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Extract relevant fields from the JSON response and create a list of {@link News}s
        List<Movie> movies = parseMovieJson(jsonResponse);
        // Return the list of {@link News}s
        return movies;
    }

}
