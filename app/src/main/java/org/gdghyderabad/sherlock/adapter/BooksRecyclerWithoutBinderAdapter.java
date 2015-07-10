package org.gdghyderabad.sherlock.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.gdghyderabad.sherlock.R;
import org.gdghyderabad.sherlock.model.Book;

import java.util.List;

/**
 * Created by phanirajabhandari on 7/6/2015.
 */
public class BooksRecyclerWithoutBinderAdapter extends RecyclerView.Adapter<BooksRecyclerWithoutBinderAdapter.ViewHolder> {
    private List<Book> mBooks;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView author;
        private TextView publisher;
        private ImageView thumbnail;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.book_title_textview);
            author = (TextView) v.findViewById(R.id.book_authors_textview);
            publisher = (TextView) v.findViewById(R.id.book_publisher_textview);
            thumbnail = (ImageView) v.findViewById(R.id.book_thumbnail_imageview);
        }
    }

    public BooksRecyclerWithoutBinderAdapter(List<Book> recyclerUsers) {
        this.mBooks = recyclerUsers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_result_without_binder, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Book book = mBooks.get(position);
        holder.author.setText(book.getVolumeInfo().authors.get(0));
        holder.title.setText(book.getVolumeInfo().title);
        holder.publisher.setText(book.getVolumeInfo().publisher);
        Picasso.with(holder.thumbnail.getContext())
                .load(book.getVolumeInfo().imageLinks.thumbnail)
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }
}