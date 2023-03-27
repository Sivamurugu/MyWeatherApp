package com.sivamurugu.myweather.di

import com.sivamurugu.myweather.network.iApiService
import com.tvsmotor.digiapp.dmsdigi.repositories.WeatherRepo
import com.tvsmotor.digiapp.dmsdigi.repositories.WeatherRepoImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(ViewModelComponent::class)
@Module
abstract class RepoModule {
    @Binds
    abstract fun bindMasterDataRepository(masterDataRepositoryImpl: WeatherRepoImpl): WeatherRepo

}

@InstallIn(SingletonComponent::class)
@Module
class RepoProvider {
    @Provides
    @Singleton
    fun provideMasterDataRepositoryImpl( api: iApiService): WeatherRepoImpl =
        WeatherRepoImpl(api)

}