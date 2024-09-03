package com.jax.cryptoapp.data.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkerParameters
import com.jax.cryptoapp.data.database.db.CoinInfoDao
import com.jax.cryptoapp.data.mapper.CoinMapper
import com.jax.cryptoapp.data.network.api.ApiService
import kotlinx.coroutines.delay
import javax.inject.Inject

class RefreshDataWorker(
    context: Context,
    params: WorkerParameters,
    private val apiService: ApiService,
    private val dao: CoinInfoDao,
    private val mapper: CoinMapper
) :
    CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        while (true) {
            val topCoins = apiService.getTopCoinsInfo()
            val fsyms = mapper.mapNamesListToString(topCoins)
            val coinsNames = apiService.getFullPriceList(fSyms = fsyms)
            val fullPriceListDto = mapper.jsonContainerDtoToListCoinInfo(coinsNames)
            val dbModelList = fullPriceListDto.map { coinInfoDto ->
                mapper.mapDtoToDbModel(coinInfoDto)
            }
            dao.insertPriceList(dbModelList)
            delay(30000)
        }
    }
    class Factory @Inject constructor(
        private val apiService: ApiService,
        private val dao: CoinInfoDao,
        private val mapper: CoinMapper
    ):ChildWorkerFactory{
        override fun create(
            context: Context,
            workerParameters: WorkerParameters
        ): ListenableWorker {
            return RefreshDataWorker(
                context,
                workerParameters,
                apiService,
                dao,
                mapper
            )
        }
    }

    companion object {
        const val NAME = "RefreshDataWorker"
        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequest.Builder(RefreshDataWorker::class.java).build()
        }
    }
}