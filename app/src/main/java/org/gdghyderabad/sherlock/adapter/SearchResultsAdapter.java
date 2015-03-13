package org.gdghyderabad.sherlock.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.gdghyderabad.sherlock.R;
import org.gdghyderabad.sherlock.model.Book;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Mustafa Ali on 11/03/15.
 */
public class SearchResultsAdapter extends ArrayAdapter<Book> {

    private List<Book> mList;

    public SearchResultsAdapter(Context context, int resource, List<Book> objects) {
        super(context, resource, objects);
        mList = objects;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.row_search_result, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        Book book = mList.get(position);
        if (book.volumeInfo != null && book.volumeInfo.imageLinks != null && book.volumeInfo.imageLinks.smallThumbnail != null) {
            Picasso.with(getContext())
                    .load(book.volumeInfo.imageLinks.thumbnail)
                    .into(holder.thumbnailImageView);
        }
        holder.titleTextView.setText(book.volumeInfo.title);
        holder.authorTextView.setText(getAuthors(book.volumeInfo.authors));
        holder.publisherTextView.setText(book.volumeInfo.publisher);

        return view;
    }

    private String getAuthors(List<String> authorsList) {
        StringBuilder authors = new StringBuilder();
        if (authorsList != null && !authorsList.isEmpty()) {
            if (authorsList.size() > 0) {
                authors.append(authorsList.get(0));
            }
            if (authorsList.size() > 1) {
                for (int i = 1; i < authorsList.size(); i++) {
                    authors.append(i == authorsList.size() - 1 ? " & " : ", ");
                    authors.append(authorsList.get(i));
                }
            }
        }
        return authors.toString();
    }

    static class ViewHolder {
        @InjectView(R.id.book_thumbnail_imageview)
        ImageView thumbnailImageView;
        @InjectView(R.id.book_title_textview)
        TextView titleTextView;
        @InjectView(R.id.book_authors_textview)
        TextView authorTextView;
        @InjectView(R.id.book_publisher_textview)
        TextView publisherTextView;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
