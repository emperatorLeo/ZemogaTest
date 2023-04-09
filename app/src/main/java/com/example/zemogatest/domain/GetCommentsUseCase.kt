package com.example.zemogatest.domain

import com.example.zemogatest.data.JsonHolderRepository
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(private val repository: JsonHolderRepository){
    suspend operator fun invoke(postId: Int) = repository.getComments(postId)
}