package com.example.weather_app.utils

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.weather_app.R

@SuppressLint("ResourceType")
fun ImageView?.loadImage(
    url: String?,
    @DrawableRes placeholder: Int? = null,
    placeholderDrawable: Drawable? = null,
) {
    this?.context?.let {
        Glide.with(it)
            .load(url)
            .thumbnail(0.2f)
            .placeholder(R.raw.loading)
            .into(this)
    }
}


fun ImageView.loadCircularImage(url: String?, placeholderDrawable: Drawable? = null) {
    Glide.with(this).load(url)
        .transform(MultiTransformation<Bitmap>(CenterCrop(), CircleCrop()))
        .placeholder(placeholderDrawable)
        .into(this)
}
