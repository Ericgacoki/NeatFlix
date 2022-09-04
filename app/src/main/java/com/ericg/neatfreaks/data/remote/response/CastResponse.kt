package com.ericg.neatfreaks.data.remote.response

import com.ericg.neatfreaks.model.Cast
import com.google.gson.annotations.SerializedName

data class CastResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("cast")
    val castResult: List<Cast>
)
