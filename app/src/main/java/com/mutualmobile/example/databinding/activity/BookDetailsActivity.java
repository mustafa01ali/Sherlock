package com.mutualmobile.example.databinding.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mutualmobile.example.databinding.R;
import com.mutualmobile.example.databinding.app.DataBindingApplication;
import com.mutualmobile.example.databinding.databinding.ActivityBookDetailsBinding;
import com.mutualmobile.example.databinding.model.BookDetails;



public class BookDetailsActivity extends AppCompatActivity {
    public static final String BOOK_POSITION = "BOOK_POSITION";
    private BookDetails mBookDetails = new BookDetails();
    private int mBookPosition;
    private ActivityBookDetailsBinding mBindding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        mBindding = DataBindingUtil.setContentView(this, R.layout.activity_book_details);
        mBookPosition = getIntent().getIntExtra(BOOK_POSITION, 0);
        mBookDetails.setVolumeInfo(((DataBindingApplication) getApplication()).getBooks().get(mBookPosition).getVolumeInfo());
        mBookDetails.setIndex(mBookPosition);
        mBindding.setBookDetails(mBookDetails);
    }

    public void onShowNextBook(View view) {
        mBookDetails.setIndex(++mBookPosition);
        mBookDetails.setVolumeInfo(((DataBindingApplication) getApplication()).getBooks().get(mBookDetails.getIndex()).volumeInfo);
    }

    public void onShowPreviousBook(View view) {
        mBookDetails.setIndex(--mBookPosition);
        mBookDetails.setVolumeInfo(((DataBindingApplication) getApplication()).getBooks().get(mBookDetails.getIndex()).volumeInfo);
    }
}
