package com.example.andreeagorcsa.popularmovies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andreeagorcsa.popularmovies.Models.Review;
import com.example.andreeagorcsa.popularmovies.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andreeagorcsa on 2018. 04. 07..
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {


    private final static String LOG_TAG = ReviewAdapter.class.getSimpleName();

    private Context context;
    private List<Review> reviewList;


    // ReviewAdapter constructor
    public ReviewAdapter(Context context) {
        this.context = context;
        reviewList = new ArrayList<>();
    }

    // ReviewList getter method
    public List<Review> getReviewList() {
        return reviewList;
    }

    // ReviewList setter method
    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
        this.notifyDataSetChanged();
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        ReviewViewHolder reviewViewHolder = new ReviewViewHolder(view);
        return reviewViewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.bindReview(reviewList.get(position).getAuthor(), reviewList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }


    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.reviewAuthor)
        TextView mReviewAuthor;
        @BindView(R.id.reviewContent)
        TextView mReviewContent;


        public ReviewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindReview(String author, String content) {
            mReviewAuthor.setText(author);
            mReviewContent.setText(content);
        }
    }
}

