package com.jax.cryptoapp.di

import android.app.Application
import com.jax.cryptoapp.presentation.CoinApp
import com.jax.cryptoapp.presentation.detail.CoinDetailFragment
import com.jax.cryptoapp.presentation.main.CoinPriceListActivity
import dagger.BindsInstance
import dagger.Component


@Component(
    modules = [DataModule::class,
        ViewModelModule::class,
        WorkerModule::class]
)
interface ApplicationComponent {

    fun inject(fragment: CoinDetailFragment)
    fun inject(activity: CoinPriceListActivity)
    fun inject(application: CoinApp)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}