package com.jax.cryptoapp.di

import com.jax.cryptoapp.data.workers.ChildWorkerFactory
import com.jax.cryptoapp.data.workers.RefreshDataWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(RefreshDataWorker::class)
    fun bindRefreshDataWorkerFactory(factory: RefreshDataWorker.Factory): ChildWorkerFactory

}