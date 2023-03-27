package com.sivamurugu.myweather.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseUser
import com.sivamurugu.myweather.R
import com.sivamurugu.myweather.databinding.ActivityLoginBinding
import com.sivamurugu.myweather.viewmodel.LoginRegisterViewModel


class LoginActivity : AppCompatActivity() {

    private var loginRegisterViewModel: LoginRegisterViewModel? = null
    private lateinit var binding: ActivityLoginBinding
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        FirebaseApp.initializeApp(this);
        loginRegisterViewModel = ViewModelProviders.of(this).get(LoginRegisterViewModel::class.java)

        loginRegisterViewModel!!.userLiveData!!.observe(this, object : Observer<FirebaseUser?> {
            override fun onChanged(firebaseUser: FirebaseUser?) {
                if (firebaseUser != null) {
                    Toast.makeText(
                        applicationContext,
                        "Login Success",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(applicationContext,MainActivity::class.java))
                    finish()
                } else {

                }
            }
        })
        binding.btnLogin.setOnClickListener {
            val email: String = binding.edtMail.getText().toString()
            val password: String = binding.edtPass.getText().toString()
            if (email.length > 0 && password.length > 0) {
                loginRegisterViewModel!!.login(email, password)
            } else {
                Toast.makeText(
                    this,
                    "Email Address and Password Must Be Entered",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.btnReg.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }
    }
}