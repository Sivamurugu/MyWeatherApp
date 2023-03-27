package com.tvsmotor.digiapp.dmsdigi.repositories


import io.reactivex.Observable
import org.json.JSONObject

interface WeatherRepo {

    fun getWeather(
        url: String?
    ): Observable<String>

}