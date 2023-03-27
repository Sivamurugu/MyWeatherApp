package com.sivamurugu.myweather.repo

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(applicationContext)
        instance = this
    }

    companion object {
        lateinit var instance: MyApp
            private set
    }
}