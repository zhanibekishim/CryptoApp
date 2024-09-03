package com.jax.cryptoapp.data.network.dto

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinInfoJsonContainerDto (
    @SerializedName("RAW")
    @Expose
    val json: JsonObject? = null
)