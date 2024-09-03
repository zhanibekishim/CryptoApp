package com.jax.cryptoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.jax.cryptoapp.data.database.db.CoinInfoDao
import com.jax.cryptoapp.data.mapper.CoinMapper
import com.jax.cryptoapp.data.workers.RefreshDataWorker
import com.jax.cryptoapp.domain.entity.CoinInfo
import com.jax.cryptoapp.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val dao: CoinInfoDao,
    private val mapper: CoinMapper,
    private val application: Application
) : CoinRepository {

    override fun getCoinInfoList(): LiveData<List<CoinInfo>> {
        return dao.getPriceList().map { coinInfoDbModelList ->
            coinInfoDbModelList.map { coinInfoDbModel ->
                mapper.mapDbModelToEntity(coinInfoDbModel)
            }
        }
    }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> {
        return dao.getPriceInfoAboutCoin(fromSymbol).map { coinInfoDbModel ->
            mapper.mapDbModelToEntity(coinInfoDbModel)
        }
    }

    override suspend fun loadData() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            RefreshDataWorker.NAME,
            ExistingWorkPolicy.REPLACE,
            RefreshDataWorker.makeRequest()
        )
    }
}