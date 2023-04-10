package com.example.zemogatest.data.network

import com.example.zemogatest.core.Comment
import com.example.zemogatest.core.Post
import com.example.zemogatest.core.User
import java.util.LinkedList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface JsonHolderApiClient {

    @GET("/posts")
    suspend fun getAllPosts(): Response<LinkedList<Post>>

    @GET("users/{user_id}")
    suspend fun getUser(@Path(value = "user_id") userId: Int): Response<User>

    @GET("posts/{post_id}/comments")
    suspend fun getComments(@Path(value = "post_id") postId: Int): Response<List<Comment>>

}