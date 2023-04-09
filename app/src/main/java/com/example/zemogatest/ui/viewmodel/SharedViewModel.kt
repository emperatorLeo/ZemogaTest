package com.example.zemogatest.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zemogatest.core.Post
import com.example.zemogatest.core.User
import com.example.zemogatest.domain.GetAllPostsUseCase
import com.example.zemogatest.domain.GetCommentsUseCase
import com.example.zemogatest.domain.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val posts: GetAllPostsUseCase,
    private val userInfo: GetUserInfoUseCase,
    private val comments: GetCommentsUseCase
) : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user
    private val _postList = MutableLiveData<List<Post>>()
    val postList: LiveData<List<Post>> = _postList

    fun getPosts() {
        viewModelScope.launch {
            _postList.postValue(posts.invoke())
        }
    }
}