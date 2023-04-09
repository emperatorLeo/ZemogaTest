package com.example.zemogatest.core

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("name") val title: String,
    @SerializedName("body") val comment: String
)