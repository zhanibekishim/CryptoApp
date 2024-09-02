package com.jax.cryptoapp.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jax.cryptoapp.databinding.ActivityMainBinding
import com.jax.cryptoapp.presentation.adapters.CoinInfoAdapter
import com.jax.cryptoapp.presentation.detail.CoinDetailActivity

class CoinPriceListActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var adapter: CoinInfoAdapter

    private lateinit var viewModel: CoinPriceListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        setUpClickListeners()
        observeViewModel()
    }

    private fun initViews() {
        adapter = CoinInfoAdapter(this)
        binding.rvCoinPriceList.adapter = adapter
        viewModel = ViewModelProvider(this)[CoinPriceListViewModel::class.java]
    }

    private fun setUpClickListeners() {
        adapter.onCoinClickListener = {
            val intent = CoinDetailActivity.newIntent(this@CoinPriceListActivity, it.fromSymbol)
            startActivity(intent)
        }
    }

    private fun observeViewModel() {
        viewModel.priceList.observe(this) { fullCoinInfoList ->
            adapter.submitList(fullCoinInfoList)
        }
    }
}
