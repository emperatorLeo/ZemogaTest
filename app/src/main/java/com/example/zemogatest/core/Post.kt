package com.example.zemogatest.core

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    @SerializedName("body") val post: String
): Serializable {
    var isFavorite: Boolean = false
}