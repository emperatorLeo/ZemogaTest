package com.example.zemogatest.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zemogatest.data.network.JsonHolderApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(private val api: JsonHolderApiClient): ViewModel() {

    fun testingApi(){
        viewModelScope.launch {
            val response = api.getUser(2).body()
            response?.let {
                    Log.d("Leo","title: ${it.name}")
            }
        }
    }
}