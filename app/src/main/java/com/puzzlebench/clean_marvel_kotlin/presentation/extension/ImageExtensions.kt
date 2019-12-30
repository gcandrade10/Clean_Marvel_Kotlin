package com.puzzlebench.clean_marvel_kotlin.presentation.extension

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.getImageByUrl(url: String) {
    Picasso.with(context).load(useHttps(url)).fit().centerCrop().into(this)
}

fun ImageView.getImageByUrl(url: String, givenContext: Context) {
    Picasso.with(givenContext).load(useHttps(url)).into(this)
}

//TODO remove workaround when marvel endpoint supports https
fun useHttps(http: String) = http.replace("http", "https")

