package com.jax.cryptoapp.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jax.cryptoapp.R
import com.jax.cryptoapp.data.model.CoinPriceInfoDto
import com.jax.cryptoapp.databinding.ItemCoinInfoBinding
import com.squareup.picasso.Picasso

class CoinInfoAdapter(private val context: Context) :
    ListAdapter<CoinPriceInfoDto, CoinInfoAdapter.CoinInfoViewHolder>(CoinDiffCallback) {

    var onCoinClickListener: ((CoinPriceInfoDto) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val binding =
            ItemCoinInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = getItem(position)

        with(holder.binding) {
            tvCurrency.text = context.resources.getString(
                R.string.symbols_template,
                coin.fromSymbol,
                coin.toSymbol
            )
            tvPrice.text = coin.price
            lastUpdate.text =
                context.resources.getString(R.string.last_update_template, coin.getFormattedTime())
            Picasso.get()
                .load(coin.getFullImageUrl())
                .error(R.drawable.no_internet)
                .into(imgLogo)

            root.setOnClickListener {
                onCoinClickListener?.invoke(coin)
            }
        }
    }

    inner class CoinInfoViewHolder(val binding: ItemCoinInfoBinding) : RecyclerView.ViewHolder(binding.root)
}
