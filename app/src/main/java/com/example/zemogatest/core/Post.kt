package com.example.zemogatest.core

import com.google.gson.annotations.SerializedName


data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    @SerializedName("body") val post: String
) {
    var isFavorite: Boolean = false
}