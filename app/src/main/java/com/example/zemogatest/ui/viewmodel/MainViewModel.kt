package com.example.zemogatest.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zemogatest.core.Post
import com.example.zemogatest.core.User
import com.example.zemogatest.data.network.JsonHolderApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(private val api: JsonHolderApiClient): ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user
    private val _postList = MutableLiveData<List<Post>>()
    val postList: LiveData<List<Post>> = _postList

    fun testingApi(){
        viewModelScope.launch {
            /*val response = api.getUser(2).body()
            response?.let {
                    Log.d("Leo","title: ${it.name}")
            }*/
            val listPostResponse = api.getAllPosts()
            if (listPostResponse.isSuccessful){
                Log.d("Leo","call is succesfull")
                _postList.postValue(listPostResponse.body())
            }
        }
    }
}