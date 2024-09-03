package com.jax.cryptoapp.di

import android.app.Application
import com.jax.cryptoapp.data.database.db.AppDatabase
import com.jax.cryptoapp.data.database.db.CoinInfoDao
import com.jax.cryptoapp.data.network.api.ApiFactory
import com.jax.cryptoapp.data.network.api.ApiService
import com.jax.cryptoapp.data.repository.CoinRepositoryImpl
import com.jax.cryptoapp.domain.repository.CoinRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    fun bindCoinRepository(impl: CoinRepositoryImpl): CoinRepository

    companion object {
        @Provides
        fun provideCoinInfoDao(
            application: Application
        ): CoinInfoDao {
            return AppDatabase.getInstance(application).coinPriceInfoDao()
        }

        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}