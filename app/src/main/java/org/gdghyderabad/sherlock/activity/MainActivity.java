package org.gdghyderabad.sherlock.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.gdghyderabad.sherlock.R;
import org.gdghyderabad.sherlock.adapter.SearchResultsAdapter;
import org.gdghyderabad.sherlock.api.GoogleBooksService;
import org.gdghyderabad.sherlock.model.Book;
import org.gdghyderabad.sherlock.model.SearchResults;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.search_edit_text)
    protected EditText mSearchEditText;

    @InjectView(R.id.progress_bar)
    protected ProgressBar mProgressBar;

    @InjectView(R.id.search_results_list_view)
    protected ListView mSearchResultsListView;

    private GoogleBooksService mGoogleBooksService;
    private SearchResultsAdapter mAdapter;
    private List<Book> mResultsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        init();
    }

    @OnClick(R.id.search_button)
    public void onSearchButtonClicked() {
        handleSearchRequest();
    }

    @OnEditorAction(R.id.search_edit_text)
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            handleSearchRequest();
            return true;
        }
        return false;
    }

    private void init() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://www.googleapis.com")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        mGoogleBooksService = restAdapter.create(GoogleBooksService.class);
    }

    private void handleSearchRequest() {
        String query = mSearchEditText.getText().toString().trim();
        if (TextUtils.isEmpty(query)) {
            mSearchEditText.setError(getString(R.string.required));
        } else {
            searchBooksByQuery(query);
        }
    }

    private void searchBooksByQuery(String query) {
        hideKeyboard();
        displayProgress(true);
        mGoogleBooksService.search(query, mSearchResultsCallback);
    }

    private Callback<SearchResults> mSearchResultsCallback = new Callback<SearchResults>() {
        @Override
        public void success(SearchResults searchResults, Response response) {
            displayResults(searchResults);
        }

        @Override
        public void failure(RetrofitError error) {
            displayError(error);
        }
    };

    private void displayResults(SearchResults searchResults) {
        Timber.d("Total search results: " + searchResults.totalItems);
        Timber.d("Got results: " + searchResults.books.size());

        displayProgress(false);

        if (mAdapter == null) {
            mAdapter = new SearchResultsAdapter(this, 0, searchResults.books);
            mSearchResultsListView.setAdapter(mAdapter);
            mResultsList = searchResults.books;
        } else {
            mResultsList.clear();
            mResultsList.addAll(searchResults.books);
            mAdapter.notifyDataSetChanged();
            mSearchResultsListView.setSelection(0);
        }
    }

    private void displayError(RetrofitError error) {
        Timber.e("Search failed with error: " + error.getKind());

        displayProgress(false);

        if (mResultsList != null) {
            mResultsList.clear();
            if (mAdapter != null) {
                mAdapter.notifyDataSetChanged();
            }
        }
        if (error.getKind().equals(RetrofitError.Kind.NETWORK)) {
            Toast.makeText(this, getString(R.string.msg_error_network), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, getString(R.string.msg_error_generic), Toast.LENGTH_LONG).show();
        }
    }

    private void displayProgress(boolean show) {
        if (show) {
            mSearchResultsListView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mSearchResultsListView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
        }
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = this.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
