package com.example.zemogatest.data.network

import com.example.zemogatest.core.Comment
import com.example.zemogatest.core.Post
import com.example.zemogatest.core.User
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class JsonHolderService @Inject constructor(private val api: JsonHolderApiClient) {

    suspend fun getAllPost(): List<Post> {
        return withContext(Dispatchers.IO) {
            val response = api.getAllPosts()
            response.body() ?: emptyList()
        }
    }

    suspend fun getUserInfo(userId: Int): User? {
        return withContext(Dispatchers.IO) {
            val response = api.getUser(userId)
            response.body()
        }
    }

    suspend fun getComments(postId: Int): List<Comment> {
        return withContext(Dispatchers.IO){
            val response = api.getComments(postId)
            response.body() ?: emptyList()
        }
    }

}