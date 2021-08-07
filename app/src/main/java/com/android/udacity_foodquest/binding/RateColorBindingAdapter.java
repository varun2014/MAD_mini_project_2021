package com.android.udacity_foodquest.binding;

import androidx.databinding.BindingAdapter;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.TextView;

public class RateColorBindingAdapter {

    @BindingAdapter("rateColor")
    public static void setColor(TextView textView, String color) {
        GradientDrawable bgShape = (GradientDrawable) textView.getBackground();
        bgShape.setColor(Color.parseColor("#" + color));
    }
}
