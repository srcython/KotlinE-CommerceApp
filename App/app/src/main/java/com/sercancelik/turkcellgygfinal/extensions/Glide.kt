package com.sercancelik.turkcellgygfinal.extensions

import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImageWithGlide(imageUrl: String?, width: Int = 300, height: Int = 350) {
    val circularProgressDrawable = CircularProgressDrawable(this.context).apply {
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }

    val requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(circularProgressDrawable)
        .error(android.R.drawable.stat_notify_error)

    Glide.with(this.context)
        .load(imageUrl)
        .apply(requestOptions)
        .into(this)
}
