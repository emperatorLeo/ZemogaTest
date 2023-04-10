package com.example.zemogatest.core

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    @SerializedName("body") val post: String
): Parcelable {
    @IgnoredOnParcel
    var isFavorite: Boolean = false
}