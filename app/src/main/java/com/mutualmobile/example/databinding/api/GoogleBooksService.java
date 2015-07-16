package com.mutualmobile.example.databinding.api;

import com.mutualmobile.example.databinding.model.SearchResults;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Mustafa Ali on 11/03/15.
 */
public interface GoogleBooksService {
    @GET("/books/v1/volumes")
    void search(@Query("q") String query, Callback<SearchResults> callback);
}
