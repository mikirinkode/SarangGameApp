package com.mikirinkode.saranggame.data.remote

import android.util.Log
import com.mikirinkode.saranggame.data.remote.response.Game
import com.mikirinkode.saranggame.data.remote.response.GameList
import com.mikirinkode.saranggame.utils.ApiResponse
import com.mikirinkode.saranggame.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class RemoteDataSource(private val api: ApiService) {

    private val apiKey = Constants.API_KEY

    suspend fun getGameList(): Flow<ApiResponse<GameList>> {
        return flow {
            try {
                val response = api.getGameList(apiKey)
                val gameList = response.results
                if (!gameList.isNullOrEmpty()) {
                    emit(ApiResponse.Success(response))
                } else if (gameList != null && gameList.isEmpty()) {
                    emit(
                        ApiResponse.Empty(response)
                    )
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", "Failed to Get Game List")
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun searchGame(query: String): Flow<ApiResponse<GameList>> {
        return flow {
            try {
                val response = api.searchGame(apiKey, query)
                val gameList = response.results
                if (!gameList.isNullOrEmpty()) {
                    emit(ApiResponse.Success(response))
                } else if (gameList != null && gameList.isEmpty()) {
                    emit(
                        ApiResponse.Empty(response)
                    )
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", "Failed to Get Game List")
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getDetailGame(id: String): Flow<ApiResponse<Game>> {
        return flow {
            try {
                val response = api.getGameDetail(id, apiKey)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", "Failed to Get Game Detail")
            }
        }.flowOn(Dispatchers.IO)
    }


    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(api: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
                RemoteDataSource(api).apply {
                    instance = this
                }
            }
    }
}