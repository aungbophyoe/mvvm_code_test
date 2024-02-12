package com.aungbophyoe.codingtestmvvm.binding

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.aungbophyoe.codingtestmvvm.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@BindingAdapter("bindNewsImage")
fun bindNewsImage(imageView: ImageView, url : String?) {
    if (url == null) {
        Glide.with(imageView.context)
            .load(R.drawable.ic_try_again)
            .into(imageView)
    } else {
        Log.d("image_url","$url")
        Glide.with(imageView.context)
            .load(url)
            .thumbnail(Glide.with(imageView.context).load(R.drawable.gif_loading))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }
}