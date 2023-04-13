package com.example.zemogatest.data.network

import com.example.zemogatest.core.Comment
import com.example.zemogatest.core.Post
import com.example.zemogatest.core.User
import java.util.LinkedList
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class JsonHolderService @Inject constructor(private val api: JsonHolderApiClient) {

    suspend fun getAllPost(): Response<LinkedList<Post>> {
        return withContext(Dispatchers.IO) {
             api.getAllPosts()
        }
    }

    suspend fun getUserInfo(userId: Int): Response<User> {
        return withContext(Dispatchers.IO) {
             api.getUser(userId)
        }
    }

    suspend fun getComments(postId: Int): Response<List<Comment>> {
        return withContext(Dispatchers.IO){
             api.getComments(postId)
        }
    }

}