package com.jax.cryptoapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jax.cryptoapp.R

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinPriceListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[CoinPriceListViewModel::class.java]
        viewModel.priceList.observe(this) {
            Log.d("TEST_OF_LOADING_DATA", it.toString())
        }
    }
}
