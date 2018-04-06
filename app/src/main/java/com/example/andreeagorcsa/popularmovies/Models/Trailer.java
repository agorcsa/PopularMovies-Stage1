package com.example.andreeagorcsa.popularmovies.Models;

import org.parceler.Parcel;

/**
 * Created by andreeagorcsa on 2018. 04. 06..
 */
@Parcel
public class Trailer {
    // Declaration of the trailer constants
    private String key;
    private String name;

    public Trailer() {}


    public Trailer( String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
