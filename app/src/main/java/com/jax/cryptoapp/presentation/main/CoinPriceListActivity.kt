package com.jax.cryptoapp.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.jax.cryptoapp.R
import com.jax.cryptoapp.databinding.ActivityPriceListBinding
import com.jax.cryptoapp.presentation.CoinApp

import com.jax.cryptoapp.presentation.adapters.CoinInfoAdapter
import com.jax.cryptoapp.presentation.detail.CoinDetailActivity
import com.jax.cryptoapp.presentation.detail.CoinDetailFragment
import javax.inject.Inject

class CoinPriceListActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityPriceListBinding.inflate(layoutInflater)
    }
    private lateinit var adapter: CoinInfoAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        )[CoinPriceListViewModel::class.java]
    }

    private val component by lazy {
        (application as CoinApp).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        setUpClickListeners()
        observeViewModel()
    }

    private fun initViews() {
        adapter = CoinInfoAdapter(this)
        binding.rvCoinPriceList?.adapter = adapter
        binding.rvCoinPriceList?.itemAnimator = null
    }

    private fun setUpClickListeners() {
        adapter.onCoinClickListener = {
            if (isOnePaneMode()) {
                launchDetailActivity(it.fromSymbol)
            } else {
                launchDetailFragment(it.fromSymbol)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.coinInfoList.observe(this) { fullCoinInfoList ->
            adapter.submitList(fullCoinInfoList)
        }
    }

    private fun isOnePaneMode() = binding.fragmentContainer == null

    private fun launchDetailActivity(fromSymbol: String) {
        val intent = CoinDetailActivity.newIntent(
            this@CoinPriceListActivity,
            fromSymbol
        )
        startActivity(intent)
    }

    private fun launchDetailFragment(fromSymbol: String) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, CoinDetailFragment.newInstance(fromSymbol))
            .addToBackStack(null)
            .commit()
    }
}
