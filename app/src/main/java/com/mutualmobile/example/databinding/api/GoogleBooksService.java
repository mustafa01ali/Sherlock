package com.mutualmobile.example.databinding.api;

import com.mutualmobile.example.databinding.model.SearchResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by Mustafa Ali on 11/03/15.
 */
public interface GoogleBooksService {
    @GET("/books/v1/volumes")
    Call<SearchResults> search(@Query("q") String query);
}
