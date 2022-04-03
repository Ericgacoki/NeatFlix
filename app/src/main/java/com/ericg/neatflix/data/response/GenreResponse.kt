package com.ericg.neatflix.data.response

import com.ericg.neatflix.model.Genre
import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("genres")
    val genres: List<Genre>
)