package com.mikirinkode.saranggame.data.remote

import com.mikirinkode.saranggame.data.remote.response.Game
import com.mikirinkode.saranggame.data.remote.response.GameList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("games")
    suspend fun getGameList(
        @Query("key") key: String
    ): GameList

    @GET("games/{id}")
    suspend fun getGameDetail(
        @Path("id") id: String,
        @Query("key") key: String
    ): Game
}