package com.jax.cryptoapp.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.jax.cryptoapp.data.database.AppDatabase
import com.jax.cryptoapp.data.model.CoinPriceInfoDto
import com.jax.cryptoapp.data.model.CoinPriceInfoRawDataDto
import com.jax.cryptoapp.data.network.ApiFactory
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CoinPriceListViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()
    val priceList = db.coinPriceInfoDao().getPriceList()

    init {
        loadData()
    }

    private fun getDetailsInfo(fSym: String): LiveData<CoinPriceInfoDto> {
        return db.coinPriceInfoDao().getPriceInfoAboutCoin(fSym)
    }

    private fun loadData() {
        val disposable = ApiFactory.apiService.getTopCoinsInfo()
            .map { coinInfoListOfDataDto ->
                coinInfoListOfDataDto.data?.map { datumDto ->
                    datumDto.coinInfo?.name
                }?.joinToString(",").toString()
            }
            .flatMap { ApiFactory.apiService.getFullPriceList(fSyms = it) }
            .map { coinPriceInfoRawData -> getFullPriceListInfo(coinPriceInfoRawData) }
            .subscribeOn(Schedulers.io())
            .delaySubscription(30, TimeUnit.SECONDS)
            .repeat()
            .retry()
            .subscribe({ fullPriceListInfo ->
                db.coinPriceInfoDao().insertPriceList(fullPriceListInfo)

            }, { throwable ->
                Log.d("TEST_OF_LOADING_DATA", throwable.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    private fun getFullPriceListInfo(
        coinPriceInfoRawData: CoinPriceInfoRawDataDto
    ): List<CoinPriceInfoDto> {
        val result = mutableListOf<CoinPriceInfoDto>()
        val jsonObject = coinPriceInfoRawData.coinPriceInfoJsonObject ?: return result

        val keySetCoins = jsonObject.keySet()

        keySetCoins.forEach { coin ->
            val btcJson = jsonObject.getAsJsonObject(coin)
            val keySetCurrencies = btcJson.keySet()
            keySetCurrencies.forEach { currency ->
                val priceInfo = Gson().fromJson(
                    btcJson.getAsJsonObject(currency),
                    CoinPriceInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}