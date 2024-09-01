package com.jax.cryptoapp.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CoinInfoListOfDataDto (
   @SerializedName("Data")
   @Expose
   val data: List<DatumDto>? = null
)