package com.sivamurugu.myweather.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.sivamurugu.myweather.repo.AuthAppRepository


class LoginRegisterViewModel(application: Application) : AndroidViewModel(application) {
    private val authAppRepository: AuthAppRepository
    var userLiveData: MutableLiveData<FirebaseUser?> ?=null
    private var loggedOutLiveData: MutableLiveData<Boolean>? = null

    init {
        authAppRepository = AuthAppRepository(application)
        userLiveData = authAppRepository.userLiveData
        loggedOutLiveData = authAppRepository.loggedOutLiveData
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun login(email: String?, password: String?) {
        authAppRepository.login(email, password)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun register(email: String?, password: String?) {
        authAppRepository.register(email, password)
    }

    fun logOut() {
        authAppRepository.logOut()
    }

    fun getLoggedOutLiveData(): MutableLiveData<Boolean>? {
        return loggedOutLiveData
    }
}