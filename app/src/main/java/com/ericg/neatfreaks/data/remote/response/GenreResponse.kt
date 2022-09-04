package com.ericg.neatfreaks.data.remote.response

import com.ericg.neatfreaks.model.Genre
import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("genres")
    val genres: List<Genre>
)