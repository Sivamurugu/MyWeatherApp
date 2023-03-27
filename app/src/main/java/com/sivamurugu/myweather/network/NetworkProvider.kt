package com.sivamurugu.myweather.network

import android.app.Application
import com.sivamurugu.myweather.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import java.util.logging.Level
import java.util.logging.Logger
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MyOkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object NetworkProvider {

    @MyOkHttpClient
    @Provides
    fun provideWeatherOkHttpClient(
        application: Application,
        loggingInterceptor: HttpLoggingInterceptor,
        unauthorizedInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(180, TimeUnit.SECONDS)
            .readTimeout(300, TimeUnit.SECONDS)
            .writeTimeout(300, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(unauthorizedInterceptor)
            .build()
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        return when (BuildConfig.DEBUG) {
            true -> logging.apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
                Logger.getLogger(OkHttpClient::class.java.name).level = Level.FINE
            }

            else -> logging
        }
    }

    @Provides
    fun provideUnauthorizedInterceptor(): Interceptor {
        return Interceptor { chain ->
            chain.proceed(chain.request()).also { response ->

            }
        }
    }
}