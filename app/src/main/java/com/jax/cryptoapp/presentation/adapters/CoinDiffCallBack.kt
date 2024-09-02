package com.jax.cryptoapp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.jax.cryptoapp.data.model.CoinPriceInfoDto

object CoinDiffCallback : DiffUtil.ItemCallback<CoinPriceInfoDto>() {
    override fun areItemsTheSame(oldItem: CoinPriceInfoDto, newItem: CoinPriceInfoDto): Boolean {
        return oldItem.fromSymbol == newItem.fromSymbol
    }
    override fun areContentsTheSame(oldItem: CoinPriceInfoDto, newItem: CoinPriceInfoDto): Boolean {
        return oldItem == newItem
    }
}
