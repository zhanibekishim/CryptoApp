package com.jax.cryptoapp.data.mapper

import com.google.gson.Gson
import com.jax.cryptoapp.data.database.model.CoinInfoDbModel
import com.jax.cryptoapp.data.network.dto.CoinInfoDto
import com.jax.cryptoapp.data.network.dto.CoinInfoJsonContainerDto
import com.jax.cryptoapp.data.network.dto.CoinNamesListDto
import com.jax.cryptoapp.domain.entity.CoinInfo
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class CoinMapper @Inject constructor(){

    fun mapDtoToDbModel(dto: CoinInfoDto) = CoinInfoDbModel(
        fromSymbol = dto.fromSymbol,
        toSymbol = dto.toSymbol,
        price = dto.price,
        lastUpdate = dto.lastUpdate,
        highDay = dto.highDay,
        lowDay = dto.lowDay,
        lastMarket = dto.lastMarket,
        imageUrl = BASE_IMAGE_URL + dto.imageUrl
    )

    fun mapDbModelToEntity(dbModel: CoinInfoDbModel) = CoinInfo(
        fromSymbol = dbModel.fromSymbol,
        toSymbol = dbModel.toSymbol,
        price = dbModel.price,
        lastUpdate = convertTimestampToTime(dbModel.lastUpdate),
        highDay = dbModel.highDay,
        lowDay = dbModel.lowDay,
        lastMarket = dbModel.lastMarket,
        imageUrl = dbModel.imageUrl
    )

    fun jsonContainerDtoToListCoinInfo(
        coinPriceInfoRawData: CoinInfoJsonContainerDto
    ): List<CoinInfoDto> {
        val result = mutableListOf<CoinInfoDto>()
        val jsonObject = coinPriceInfoRawData.json ?: return result

        val keySetCoins = jsonObject.keySet()

        keySetCoins.forEach { coin ->
            val btcJson = jsonObject.getAsJsonObject(coin)
            val keySetCurrencies = btcJson.keySet()
            keySetCurrencies.forEach { currency ->
                val priceInfo = Gson().fromJson(
                    btcJson.getAsJsonObject(currency),
                    CoinInfoDto::class.java
                )
                result.add(priceInfo)
            }
        }
        return result
    }

    fun mapNamesListToString(namesListDto: CoinNamesListDto): String {
        return namesListDto.names?.map { coinNameContainerDto ->
            coinNameContainerDto.coinInfo?.name
        }?.joinToString(",") ?: ""
    }

    private fun convertTimestampToTime(timestamp: Long?): String {
        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val pattern = "HH:mm:ss"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    companion object {

        const val BASE_IMAGE_URL = "https://cryptocompare.com"
    }
}