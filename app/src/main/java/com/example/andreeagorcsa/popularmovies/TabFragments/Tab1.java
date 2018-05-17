package com.example.andreeagorcsa.popularmovies.TabFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.andreeagorcsa.popularmovies.R;

public class Tab1 extends BaseFragment {

  public Tab1() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View tab1View = inflater.inflate(R.layout.fragment_tab1, container, false);

        ImageView tab1ImageView = tab1View.findViewById(R.id.image_view_tab1_fragment);

        return tab1View;
    }
}
