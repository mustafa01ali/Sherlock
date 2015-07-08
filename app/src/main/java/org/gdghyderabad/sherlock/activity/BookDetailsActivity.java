package org.gdghyderabad.sherlock.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import org.gdghyderabad.sherlock.R;
import org.gdghyderabad.sherlock.databinding.ActivityBookDetailsBinding;
import org.gdghyderabad.sherlock.model.Book;

import butterknife.ButterKnife;

public class BookDetailsActivity extends ActionBarActivity {
    public static final String BOOK = "BOOK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBookDetailsBinding activityBookDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_book_details);
        ButterKnife.inject(this);
        Book book = (Book) getIntent().getSerializableExtra(BOOK);
        activityBookDetailsBinding.setBook(book);
    }
}
