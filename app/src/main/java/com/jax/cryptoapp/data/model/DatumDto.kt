package com.jax.cryptoapp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DatumDto (
    @SerializedName("CoinInfo")
    @Expose
    val coinInfo: CoinInfoDto? = null
)