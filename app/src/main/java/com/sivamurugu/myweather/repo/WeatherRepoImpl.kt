package com.tvsmotor.digiapp.dmsdigi.repositories


import com.sivamurugu.myweather.network.iApiService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import javax.inject.Inject

class WeatherRepoImpl @Inject constructor(private val api: iApiService) : WeatherRepo {

    override fun getWeather(url: String?): Observable<String> =
        api.getWeather(url!!).observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())

}