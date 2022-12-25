package com.mikirinkode.saranggame.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mikirinkode.saranggame.data.remote.ApiConfig
import com.mikirinkode.saranggame.data.remote.RemoteDataSource

class ViewModelFactory(private val remoteDataSource: RemoteDataSource): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(remoteDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(RemoteDataSource.getInstance(ApiConfig.getApiService()))
            }.also { instance = it }
    }
}