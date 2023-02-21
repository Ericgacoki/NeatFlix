package com.ericg.neatflix.data.remote.response

import com.google.gson.annotations.SerializedName

data class WatchProviderResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("results") val results: WatchProvider
)

data class WatchProvider(
    @SerializedName("US") var provider: Provider
)

data class Provider(
    @SerializedName("link") var link: String,
    @SerializedName("flatrate") var flatrate: List<Flatrate>,
    @SerializedName("buy") var buy: List<Buy>,
    @SerializedName("rent") var rent: List<Rent>
)

data class Flatrate(
    @SerializedName("display_priority") var displayPriority: Int,
    @SerializedName("logo_path") var logoPath: String,
    @SerializedName("provider_id") var providerId: Int,
    @SerializedName("provider_name") var providerName: String
)

data class Rent(
    @SerializedName("display_priority") var displayPriority: Int,
    @SerializedName("logo_path") var logoPath: String,
    @SerializedName("provider_id") var providerId: Int,
    @SerializedName("provider_name") var providerName: String
)

data class Buy(
    @SerializedName("display_priority") var displayPriority: Int,
    @SerializedName("logo_path") var logoPath: String,
    @SerializedName("provider_id") var providerId: Int,
    @SerializedName("provider_name") var providerName: String
)
