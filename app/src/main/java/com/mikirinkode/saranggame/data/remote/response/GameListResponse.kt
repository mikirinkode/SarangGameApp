package com.mikirinkode.saranggame.data.remote.response

import com.google.gson.annotations.SerializedName

data class GameListResponse(
	@field:SerializedName("results")
	val results: List<GameDetailResponse>,
)