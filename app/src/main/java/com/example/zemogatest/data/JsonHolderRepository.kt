package com.example.zemogatest.data

import com.example.zemogatest.data.network.JsonHolderService
import javax.inject.Inject

class JsonHolderRepository @Inject constructor(private val service: JsonHolderService) {

    suspend fun getAllPosts() = service.getAllPost()

    suspend fun getUserInfo(userId: Int) = service.getUserInfo(userId)

    suspend fun getComments(postId: Int) = service.getComments(postId)
}