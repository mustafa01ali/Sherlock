package org.gdghyderabad.sherlock.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.gdghyderabad.sherlock.Binding.BookDetails;
import org.gdghyderabad.sherlock.R;
import org.gdghyderabad.sherlock.app.SherlockApplication;
import org.gdghyderabad.sherlock.databinding.ActivityBookDetailsBinding;

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
        mBookDetails.setVolumeInfo(((SherlockApplication) getApplication()).getBooks().get(mBookPosition).getVolumeInfo());
        mBookDetails.setIndex(mBookPosition);
        mBindding.setBookDetails(mBookDetails);
    }

    public void onShowNextBook(View view) {
        mBookDetails.setIndex(++mBookPosition);
        mBookDetails.setVolumeInfo(((SherlockApplication) getApplication()).getBooks().get(mBookDetails.getIndex()).volumeInfo);
    }

    public void onShowPreviousBook(View view) {
        mBookDetails.setIndex(--mBookPosition);
        mBookDetails.setVolumeInfo(((SherlockApplication) getApplication()).getBooks().get(mBookDetails.getIndex()).volumeInfo);
    }
}
