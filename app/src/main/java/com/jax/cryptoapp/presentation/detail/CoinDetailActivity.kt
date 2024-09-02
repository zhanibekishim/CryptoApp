package com.jax.cryptoapp.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jax.cryptoapp.databinding.ActivityCoinDetailBinding
import com.jax.cryptoapp.presentation.main.CoinPriceListViewModel
import com.squareup.picasso.Picasso

class CoinDetailActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityCoinDetailBinding.inflate(layoutInflater)
    }
    private val viewModel by lazy {
        ViewModelProvider(this)[CoinPriceListViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val fromSymbol = getFromSymbolFromIntent() ?: return
        observeViewModel(fromSymbol)
    }
    private fun getFromSymbolFromIntent(): String? {
        return if (intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            intent.getStringExtra(EXTRA_FROM_SYMBOL)
        } else {
            finish()
            null
        }
    }

    private fun observeViewModel(fromSymbol: String){
        viewModel.getDetailsInfo(fromSymbol).observe(this) { coin ->
            binding.tvPrice.text = coin.price
            binding.tvMinPrice.text = coin.lowDay
            binding.tvMaxPrice.text = coin.highDay
            binding.tvLastMarket.text = coin.lastMarket
            binding.tvLastUpdate.text = coin.getFormattedTime()
            binding.tvFromSymbol.text = coin.fromSymbol
            binding.tvToSymbol.text = coin.toSymbol
            Picasso.get().load(coin.getFullImageUrl()).into(binding.ivLogoCoin)
        }
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"
        fun newIntent(context: Context, fromSymbol: String): Intent {
            return Intent(context, CoinDetailActivity::class.java).apply {
                putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            }
        }
    }
}