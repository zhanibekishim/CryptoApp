package com.jax.cryptoapp.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DatumDto (
    @SerializedName("CoinInfo")
    @Expose
    val coinInfo: CoinInfoDto? = null
)