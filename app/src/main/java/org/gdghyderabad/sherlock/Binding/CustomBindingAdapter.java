package org.gdghyderabad.sherlock.Binding;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by phanirajabhandari on 7/8/15.
 */
public class CustomBindingAdapter {


    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String url) {
        Picasso.with(imageView.getContext()).load(url).into(imageView);
    }

    @BindingAdapter("bind:visibilityOnIndex")
    public static void toggleVisibility(View view, int index) {
        view.setVisibility(index > 0 ? View.VISIBLE : View.GONE);
    }
}
