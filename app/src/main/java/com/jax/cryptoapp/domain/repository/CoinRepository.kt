package com.jax.cryptoapp.domain.repository

import androidx.lifecycle.LiveData
import com.jax.cryptoapp.domain.entity.CoinInfo

interface CoinRepository {

    fun getCoinInfoList(): LiveData<List<CoinInfo>>

    fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo>

    suspend fun loadData()
}