package com.jax.cryptoapp.di

import androidx.lifecycle.ViewModel
import com.jax.cryptoapp.presentation.main.CoinPriceListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CoinPriceListViewModel::class)
    fun bindsCoinViewModel(impl: CoinPriceListViewModel): ViewModel
}