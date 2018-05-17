package com.example.andreeagorcsa.popularmovies.TabFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.andreeagorcsa.popularmovies.R;

public class Tab2 extends BaseFragment {

    public Tab2 () {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View tab2View = inflater.inflate(R.layout.fragment_tab2, container, false);

        ImageView tab2ImageView = tab2View.findViewById(R.id.image_view_tab2_fragment);

        return  tab2View;
    }
}
