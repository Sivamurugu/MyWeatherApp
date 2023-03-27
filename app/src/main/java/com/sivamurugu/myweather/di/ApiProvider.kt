package com.sivamurugu.myweather.di

import com.google.gson.GsonBuilder
import com.sivamurugu.myweather.network.MyOkHttpClient
import com.sivamurugu.myweather.network.iApiService
import com.sivamurugu.myweather.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApiProvider
{
    @Provides
    @Singleton
    fun provideiApiService(@MyOkHttpClient okHttpClient: OkHttpClient): iApiService {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        // change your base URL
        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASEURL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

        //Creating object for our interface
        return retrofit.create(iApiService::class.java)
    }

}