package com.mikirinkode.saranggame.data.remote.response

import com.google.gson.annotations.SerializedName

data class GameListResponse(
	@field:SerializedName("results")
	val results: List<GameDetailResponse>,
)

data class No(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("updated")
	val updated: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("released")
	val released: String? = null,

	@field:SerializedName("rating")
	val rating: Double? = null,

	@field:SerializedName("playtime")
	val playtime: Int? = null,

	@field:SerializedName("genres")
	val genres: List<GenresItem?>? = null,

	@field:SerializedName("background_image")
	val backgroundImage: String? = null,



	@field:SerializedName("saturated_color")
	val saturatedColor: String? = null,

	@field:SerializedName("dominant_color")
	val dominantColor: String? = null,

//	@field:SerializedName("added")
//	val added: Int? = null,


//	@field:SerializedName("metacritic")
//	val metacritic: Int? = null,


//	@field:SerializedName("short_screenshots")
//	val shortScreenshots: List<ShortScreenshotsItem?>? = null,

//	@field:SerializedName("platforms")
//	val platforms: List<PlatformsItem?>? = null,

//	@field:SerializedName("user_game")
//	val userGame: Any? = null,

//	@field:SerializedName("rating_top")
//	val ratingTop: Int? = null,

//	@field:SerializedName("reviews_text_count")
//	val reviewsTextCount: Int? = null,

//	@field:SerializedName("ratings")
//	val ratings: List<RatingsItem?>? = null,


//	@field:SerializedName("added_by_status")
//	val addedByStatus: AddedByStatus? = null,

//	@field:SerializedName("parent_platforms")
//	val parentPlatforms: List<ParentPlatformsItem?>? = null,



//	@field:SerializedName("suggestions_count")
//	val suggestionsCount: Int? = null,

//	@field:SerializedName("stores")
//	val stores: List<StoresItem?>? = null,

//	@field:SerializedName("tags")
//	val tags: List<TagsItem?>? = null,


//	@field:SerializedName("tba")
//	val tba: Boolean? = null,


//	@field:SerializedName("esrb_rating")
//	val esrbRating: EsrbRating? = null,

//	@field:SerializedName("clip")
//	val clip: Any? = null,
//
//	@field:SerializedName("reviews_count")
//	val reviewsCount: Int? = null
)

