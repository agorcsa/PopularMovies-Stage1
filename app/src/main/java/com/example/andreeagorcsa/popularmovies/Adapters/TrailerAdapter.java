package com.example.andreeagorcsa.popularmovies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andreeagorcsa.popularmovies.MainActivity;
import com.example.andreeagorcsa.popularmovies.Models.Review;
import com.example.andreeagorcsa.popularmovies.Models.Trailer;
import com.example.andreeagorcsa.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by andreeagorcsa on 2018. 04. 07..
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>{

    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    private Context context;
    private List<Trailer> trailerList;
    private ItemClickListener itemClickListener;

    // TrailerAdapter constructor;
    public TrailerAdapter(Context context) {
        this.context = context;
        itemClickListener =(ItemClickListener)context;
        trailerList = new ArrayList<>();
    }

    // TrailerList getter method
    public List<Trailer> getmTrailerList() {
        return trailerList;
    }

    // TrailerList setter method
    public void setmTrailerList(List<Trailer> trailers) {
        trailerList = trailers;
        this.notifyDataSetChanged();
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent, false);
        TrailerViewHolder trailerViewHolder = new TrailerViewHolder(view);
        return  trailerViewHolder;
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
    holder.bind(trailerList.get(position).getName(), trailerList.get(position).getKey());
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    public interface ItemClickListener {
        void onItemClick(String key);
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.playTrailer)
        ImageView trailerIcon;
        @BindView((R.id.trailerName))
        TextView trailerName;
        @BindView(R.id.trailerKey)
        TextView trailerKey;

        public TrailerViewHolder(View itemView) {
          super(itemView);
          ButterKnife.bind(this, itemView);
          itemView.setOnClickListener(this);
        }

        public void bind(String name, String key) {
            trailerName.setText(name);
            trailerKey.setText(key);
            Picasso.with(itemView.getContext())
                    .load(R.drawable.ic_play_arrow_black_24dp)
                    .into(trailerIcon);
        }


        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(trailerList.get(getAdapterPosition()).getKey());
        }
    }
}
