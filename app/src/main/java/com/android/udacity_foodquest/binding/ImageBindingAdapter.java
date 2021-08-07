package com.android.udacity_foodquest.binding;

import androidx.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.android.udacity_foodquest.util.GlideApp;

public class ImageBindingAdapter {

    @BindingAdapter("imageUrl")
    public static void loadImage(ImageView imageView, String url) {
        if (url != null && !url.equals("")) {
            GlideApp.with(imageView.getContext())
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(imageView);
        }
    }
}
