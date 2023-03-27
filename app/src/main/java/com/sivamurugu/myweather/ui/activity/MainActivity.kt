package com.sivamurugu.myweather.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.sivamurugu.myweather.R
import com.sivamurugu.myweather.databinding.ActivityMainBinding
import com.sivamurugu.myweather.databinding.ActivityRegisterBinding
import com.sivamurugu.myweather.utils.Constant
import com.sivamurugu.myweather.viewmodel.LoginRegisterViewModel
import com.sivamurugu.myweather.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var loginRegisterViewModel: LoginRegisterViewModel? = null
    private var weatherViewModel: WeatherViewModel? = null
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        loginRegisterViewModel = ViewModelProviders.of(this).get(LoginRegisterViewModel::class.java)
        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)

        loginRegisterViewModel!!.getLoggedOutLiveData()!!.observe(this, object : Observer<Boolean?>{

            override fun onChanged(t: Boolean?) {
                if (t!!) {
                    Toast.makeText(applicationContext, "User Logged Out", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext,LoginActivity::class.java))
                    finish()
                }
            }

        })
        binding.tvLogout.setOnClickListener {
            loginRegisterViewModel!!.logOut();
        }
        binding.tvRefresh.setOnClickListener {
            callWeather()
        }


        callWeather()
    }

    private fun callWeather() {
        weatherViewModel!!.weatherLiveData.observe(this) { response ->
            checkWeather(response)
        }
        //Using Bangalore lat lon
        val url=Constant.BASEURL + Constant.CurrentWeather + "lat=" + "12.9719" + "&lon=" + "77.5937" + Constant.UnitsAppid
        lifecycleScope.launch {
            weatherViewModel!!.getWeather(url)
        }
    }

    private fun checkWeather(resp: String?) {
        val response =JSONObject(resp)
        val jsonArrayOne = response!!.getJSONArray("weather")
        val jsonObjectOne = jsonArrayOne.getJSONObject(0)
        val jsonObjectTwo = response.getJSONObject("main")
        val jsonObjectThree = response.getJSONObject("wind")
        val strWeather = jsonObjectOne.getString("main")
        val strDescWeather = jsonObjectOne.getString("description")
        val strSpeed = jsonObjectThree.getString("speed")
        val strHum = jsonObjectTwo.getString("humidity")
        val strNama = response.getString("name")
        val strTemp = jsonObjectTwo.getDouble("temp")
        val t=String.format(Locale.getDefault(), "%.0f°C", strTemp)
        binding.tvWeatherInfo.setText(strWeather+"\n"+strDescWeather+"\n"+"Speed  : "+strSpeed+"\n"+
                "Humidity : "+strHum+" %\n"+"Location : "+strNama+"\n"+"Temp : "+t)
    }
}