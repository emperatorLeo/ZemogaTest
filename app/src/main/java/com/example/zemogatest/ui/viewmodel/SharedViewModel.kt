package com.example.zemogatest.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zemogatest.core.ComplementaryInfo
import com.example.zemogatest.core.Post
import com.example.zemogatest.domain.GetAllPostsUseCase
import com.example.zemogatest.domain.GetCommentsUseCase
import com.example.zemogatest.domain.GetUserInfoUseCase
import com.example.zemogatest.ui.UiState
import com.example.zemogatest.ui.UiState.*
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.LinkedList
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val posts: GetAllPostsUseCase,
    private val userInfo: GetUserInfoUseCase,
    private val comments: GetCommentsUseCase
) : ViewModel() {
    private val _postList = MutableLiveData<LinkedList<Post>>()
    val postList: LiveData<LinkedList<Post>> = _postList
    lateinit var  post: Post
    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    fun getPosts() {
        viewModelScope.launch {
            _uiState.value = Loading(true)
            val postResponse = posts.invoke()
            if (postResponse.isSuccessful) {
                _uiState.value = Loading(false)
                _postList.value = postResponse.body()
                _uiState.value = Success(postResponse.body())
            }
        }
    }

    fun addingPost(post: Post){
        this.post = post
    }

    fun deleteAllPost(){
        _postList.value?.removeIf { post -> !post.isFavorite }
    }

    fun getUserInfoAndComments(){
        _uiState.value = Loading(true)
        viewModelScope.launch {
            val userResponse = async { userInfo.invoke(post.userId) }
            val listOfCommentsResponse = async { comments.invoke(post.id) }

            val user = userResponse.await()
            val listOfComments = listOfCommentsResponse.await()

            if (user.isSuccessful && listOfComments.isSuccessful) {
                _uiState.value = Loading(false)
                _uiState.value = Success(
                    ComplementaryInfo(
                        user = user.body(),
                        commentList = listOfComments.body()
                    )
                )
            }
        }
    }
}