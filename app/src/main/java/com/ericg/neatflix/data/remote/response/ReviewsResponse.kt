package com.ericg.neatflix.data.remote.response

import com.google.gson.annotations.SerializedName

data class ReviewsResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Review>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)

data class Review(
    @SerializedName("id")
    val id: String,
    @SerializedName("author_details")
    val authorDetails: AuthorDetails,
    @SerializedName("content")
    val content: String,
    @SerializedName("created_at")
    val createdOn: String,
    @SerializedName("url")
    val url: String
)

data class AuthorDetails(
    @SerializedName("name")
    val name: String,
    @SerializedName("username")
    val userName: String,
    @SerializedName("avatar_path")
    val avatarPath: String?,
    @SerializedName("rating")
    val rating: Int?,
)
