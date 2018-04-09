package com.example.andreeagorcsa.popularmovies.Models;

import org.parceler.Parcel;

/**
 * Created by andreeagorcsa on 2018. 04. 07..
 */
@Parcel
public class Review {

    private String author;
    private String content;

    public Review() {

    }

    public Review(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
