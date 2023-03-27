package com.sivamurugu.myweather.repo

import android.app.Application
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class AuthAppRepository(private val application: Application) {
    private var firebaseAuth: FirebaseAuth?=null
    val userLiveData: MutableLiveData<FirebaseUser?>
    val loggedOutLiveData: MutableLiveData<Boolean>

    init {
        firebaseAuth = FirebaseAuth.getInstance()
        userLiveData = MutableLiveData()
        loggedOutLiveData = MutableLiveData()
        if (firebaseAuth!!.currentUser != null) {
            userLiveData.postValue(firebaseAuth!!.currentUser)
            loggedOutLiveData.postValue(false)
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun login(email: String?, password: String?) {
        firebaseAuth!!.signInWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener(
                application.mainExecutor
            ) { task ->
                if (task.isSuccessful) {
                    userLiveData.postValue(firebaseAuth!!.currentUser)
                } else {
                    Toast.makeText(
                        application.applicationContext,
                        "Login Failure: " + task.exception!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun register(email: String?, password: String?) {
        firebaseAuth!!.createUserWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener(
                application.mainExecutor
            ) { task ->
                if (task.isSuccessful) {
                    userLiveData.postValue(firebaseAuth!!.currentUser)
                } else {
                    Toast.makeText(
                        application.applicationContext,
                        "Registration Failure: " + task.exception!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    fun logOut() {
        firebaseAuth!!.signOut()
        loggedOutLiveData.postValue(true)
    }
}