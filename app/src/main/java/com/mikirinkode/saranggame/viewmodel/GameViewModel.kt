package com.mikirinkode.saranggame.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mikirinkode.saranggame.data.remote.RemoteDataSource
import com.mikirinkode.saranggame.data.remote.response.Game
import com.mikirinkode.saranggame.utils.ApiResponse
import com.mikirinkode.saranggame.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class GameViewModel(private val remoteDataSource: RemoteDataSource) : ViewModel() {

    private val _gameDetailState: MutableStateFlow<UiState<Game>> =
        MutableStateFlow(UiState.Loading)
    val gameDetailState: StateFlow<UiState<Game>>
        get() = _gameDetailState

    private val _listState: MutableStateFlow<UiState<List<Game>>> =
        MutableStateFlow(UiState.Loading)
    val listState: StateFlow<UiState<List<Game>>>
        get() = _listState


    fun getGameDetail(gameId: String) {
        viewModelScope.launch {
            remoteDataSource.getDetailGame(gameId)
                .catch {
                    _gameDetailState.value = UiState.Error(it.message.toString())
                }
                .collect { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            _gameDetailState.value = UiState.Success(response.data)
                            Log.e("GameViewModel", response.data.toString())
                        }
                        is ApiResponse.Empty -> {
                            Log.e("GameViewModel", response.data.toString())
                            _gameDetailState.value = UiState.Success(response.data)
                        }
                        is ApiResponse.Error -> {
                            _gameDetailState.value = UiState.Error(response.errorMessage)
                        }
                    }
                }
        }
    }

    fun getGameList(){
        viewModelScope.launch{
            remoteDataSource.getGameList()
                .catch {
                    _listState.value = UiState.Error(it.message.toString())
                }
                .collect { response ->
                    when (response) {
                        is ApiResponse.Success -> {
                            _listState.value = UiState.Success(response.data.results)
                            Log.e("GameViewModel", response.data.toString())
                        }
                        is ApiResponse.Empty -> {
                            _listState.value = UiState.Success(response.data.results)
                            Log.e("GameViewModel", response.data.toString())
                        }
                        is ApiResponse.Error -> {
                            _listState.value = UiState.Error(response.errorMessage)
                        }
                    }
                }
        }
    }
}