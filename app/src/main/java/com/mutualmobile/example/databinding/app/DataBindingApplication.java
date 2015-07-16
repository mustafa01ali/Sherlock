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
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    /**
     * A tree which logs important information for crash reporting.
     */
    private static class CrashReportingTree extends Timber.HollowTree {
        @Override
        public void i(String message, Object... args) {
            // TODO e.g., Crashlytics.log(String.format(message, args));
        }

        @Override
        public void i(Throwable t, String message, Object... args) {
            i(message, args); // Just add to the log.
        }

        @Override
        public void e(String message, Object... args) {
            i("ERROR: " + message, args); // Just add to the log.
        }

        @Override
        public void e(Throwable t, String message, Object... args) {
            e(message, args);

            // TODO e.g., Crashlytics.logException(t);
        }
    }

    public List<Book> getBooks() {
        return mBooks;
    }

    public void setBooks(List<Book> mResultsist) {
        this.mBooks = mResultsist;
    }
}
