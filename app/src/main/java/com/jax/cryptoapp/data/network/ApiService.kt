package com.jax.cryptoapp.data.network

import com.jax.cryptoapp.data.model.CoinInfoListOfDataDto
import com.jax.cryptoapp.data.model.CoinPriceInfoRawDataDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top/totalvolfull")
    fun getTopCoinsInfo(
        @Query(QUERY_PARAM_LIMIT) limit: Int = 100,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = CURRENCY
    ): Single<CoinInfoListOfDataDto>

    @GET("pricemultifull")
    fun getFullPriceList(
        @Query(QUERY_PARAM_FROM_SYMBOLS) fSyms: String,
        @Query(QUERY_PARAM_TO_SYMBOLS) tSyms: String = CURRENCY
    ): Single<CoinPriceInfoRawDataDto>

    companion object {
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYMBOL = "tsym"
        private const val QUERY_PARAM_TO_SYMBOLS = "tsyms"
        private const val QUERY_PARAM_FROM_SYMBOLS = "fsyms"
        private const val CURRENCY = "USD"
    }
}
