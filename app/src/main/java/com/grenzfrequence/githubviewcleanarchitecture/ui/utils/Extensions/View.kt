package com.grenzfrequence.githubviewcleanarchitecture.ui.utils.Extensions

import android.support.v4.content.ContextCompat.getDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun ViewGroup.inflate(layoutRes: Int): View =
        LayoutInflater.from(context).inflate(layoutRes, this, false)

fun ImageView.load(imageUrl: String = "", placeholder: Int) {
    if (imageUrl.isBlank()) {
        this.setImageDrawable(getDrawable(this.context, placeholder))
        return
    }
    Glide.with(this.getContext())
            .load(imageUrl)
            .transition(withCrossFade())
            .apply(RequestOptions()
                    .centerCrop()
            )
    .into(this)
}