package com.mutualmobile.example.databinding.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mutualmobile.example.databinding.BuildConfig;
import com.mutualmobile.example.databinding.R;
import com.mutualmobile.example.databinding.adapter.BooksRecyclerAdapter;
import com.mutualmobile.example.databinding.api.GoogleBooksService;
import com.mutualmobile.example.databinding.app.DataBindingApplication;
import com.mutualmobile.example.databinding.databinding.ActivityMainBinding;
import com.mutualmobile.example.databinding.listener.RecyclerItemClickListener;
import com.mutualmobile.example.databinding.model.SearchResults;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.search_edit_text)
    protected EditText mSearchEditText;

    @BindView(R.id.progress_bar)
    protected ProgressBar mProgressBar;

    @BindView(R.id.search_results_recycler_view)
    protected RecyclerView mSearchResultsRecyclerView;

    private GoogleBooksService mGoogleBooksService;

    private BooksRecyclerAdapter mBooksRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ButterKnife.bind(this);
        mSearchResultsRecyclerView = (RecyclerView) findViewById(R.id.search_results_recycler_view);
        mSearchResultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSearchResultsRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position, float x, float y) {
                Intent intent = new Intent(MainActivity.this, BookDetailsActivity.class);
                intent.putExtra(BookDetailsActivity.BOOK_POSITION, position);
                startActivity(intent);
            }
        }));
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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com")
                .client(provideOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mGoogleBooksService = retrofit.create(GoogleBooksService.class);
    }

    private OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }
        return builder.build();
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
        mGoogleBooksService.search(query).enqueue(new Callback<SearchResults>() {
            @Override
            public void onResponse(Call<SearchResults> call, Response<SearchResults> response) {
                displayResults(response.body());
            }

            @Override
            public void onFailure(Call<SearchResults> call, Throwable t) {
                displayError(t);
            }
        });
    }

    private void displayResults(SearchResults searchResults) {
        Timber.d("Total search results: " + searchResults.totalItems);
        Timber.d("Got results: " + searchResults.books.size());

        displayProgress(false);

        if (mBooksRecyclerAdapter == null) {
            mBooksRecyclerAdapter = new BooksRecyclerAdapter(searchResults.books);
            mSearchResultsRecyclerView.setAdapter(mBooksRecyclerAdapter);
            //Without Binder Adapter
            //mSearchResultsRecyclerView.setAdapter(new BooksRecyclerWithoutBinderAdapter(searchResults.books));
            ((DataBindingApplication) getApplication()).setBooks(searchResults.books);
        } else {
            ((DataBindingApplication) getApplication()).getBooks().clear();
            ((DataBindingApplication) getApplication()).getBooks().addAll(searchResults.books);
            mBooksRecyclerAdapter.notifyDataSetChanged();
        }
    }

    private void displayError(Throwable error) {
        Timber.e("Search failed with error: " + error.getMessage());

        displayProgress(false);

        if (((DataBindingApplication) getApplication()).getBooks() != null) {
            ((DataBindingApplication) getApplication()).getBooks().clear();
            if (mBooksRecyclerAdapter != null) {
                mBooksRecyclerAdapter.notifyDataSetChanged();
            }
        }
        Toast.makeText(this, getString(R.string.msg_error_generic), Toast.LENGTH_LONG).show();
    }

    private void displayProgress(boolean show) {
        if (show) {
            mSearchResultsRecyclerView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mSearchResultsRecyclerView.setVisibility(View.VISIBLE);
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
