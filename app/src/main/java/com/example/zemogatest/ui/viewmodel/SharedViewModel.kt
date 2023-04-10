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
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val posts: GetAllPostsUseCase,
    private val userInfo: GetUserInfoUseCase,
    private val comments: GetCommentsUseCase
) : ViewModel() {
    private val _postList = MutableLiveData<List<Post>>()
    val postList: LiveData<List<Post>> = _postList
    val post = MutableLiveData<Post>()
    private val _complementaryInfo = MutableLiveData<ComplementaryInfo>()
    val complementaryInfo: LiveData<ComplementaryInfo> = _complementaryInfo

    fun getPosts() {
        viewModelScope.launch {
            _postList.postValue(posts.invoke())
        }
    }

    fun addingPost(post: Post){
        this.post.value = post
    }

    fun getUserInfoAndComments(){
        viewModelScope.launch {
            val userResponse = async { userInfo.invoke(post.value!!.userId) }
            val listOfCommentsResponse = async { comments.invoke(post.value!!.id) }

            val user = userResponse.await()
            val listOfComments = listOfCommentsResponse.await()

            _complementaryInfo.postValue(ComplementaryInfo(user = user, commentList = listOfComments))
        }
    }
}