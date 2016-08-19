package com.mutualmobile.example.databinding.app;

import android.app.Application;

import com.mutualmobile.example.databinding.BuildConfig;
import com.mutualmobile.example.databinding.model.Book;

import java.util.List;

import timber.log.Timber;

/**
 * Created by Mustafa Ali on 11/03/15.
 */
public class DataBindingApplication extends Application {
    private List<Book> mBooks;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public List<Book> getBooks() {
        return mBooks;
    }

    public void setBooks(List<Book> mResultsist) {
        this.mBooks = mResultsist;
    }
}