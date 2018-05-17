package com.example.andreeagorcsa.popularmovies.TabFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.andreeagorcsa.popularmovies.R;

public class Tab3 extends BaseFragment {

    public Tab3 () {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View tab3View = inflater.inflate(R.layout.fragment_tab3, container, false);

        ImageView tab3ImageView = tab3View.findViewById(R.id.image_view_tab3_fragment);

        return  tab3View;
    }
}
