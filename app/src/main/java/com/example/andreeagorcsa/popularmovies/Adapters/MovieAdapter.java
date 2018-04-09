package com.example.andreeagorcsa.popularmovies.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.andreeagorcsa.popularmovies.Models.Movie;
import com.example.andreeagorcsa.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreeagorcsa on 2018. 03. 10..
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieItemViewHolder> {
    private final static String TAG = MovieAdapter.class.getSimpleName();
    private List<Movie> mMovieList;
    private ItemClickHandler itemClickHandler;

    /**
     * Constructor for the MovieAdapter
     * @param itemClickHandler
     */
    public MovieAdapter(ItemClickHandler itemClickHandler) {
        this.itemClickHandler = itemClickHandler;
        mMovieList = new ArrayList<Movie>();
    }

    /**
     * Inflates the layout, and creates the ViewHolder object required from the MovieAdapter
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MovieItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieItemViewHolder(v);
    }

    /**
     * updates the contents of the ItemView to reflect the movie in the given position.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MovieItemViewHolder holder, int position) {
        holder.bind(getMovieList().get(position));
    }

    /**
     * 
     * @return
     */
    @Override
    public int getItemCount() {
        return (mMovieList != null) ? mMovieList.size() : 0;
    }

    // Getter method for the mMovieList
    private List<Movie> getMovieList() {
        return mMovieList;
    }

    // Setter method for the mMovieList
    public void setmMovieList(List<Movie> mMovieList) {
        this.mMovieList = mMovieList;
        notifyDataSetChanged();
    }

    public interface ItemClickHandler {
        void onItemClick(Movie movie);
    }

    public class MovieItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView movieItemImageView;

        public MovieItemViewHolder(View itemView) {
            super(itemView);
            movieItemImageView = itemView.findViewById(R.id.movie_item_border);
            itemView.setOnClickListener(this);
        }

        void bind(Movie movie) {
            Picasso.with(itemView.getContext())
                    .load(movie.getPosterPath())
                    .placeholder(R.drawable.cinema_poster)
                    .into(movieItemImageView);
        }

        @Override
        public void onClick(View v) {
            itemClickHandler.onItemClick(mMovieList.get(getAdapterPosition()));
        }
    }
}

