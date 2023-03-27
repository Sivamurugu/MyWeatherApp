package com.sivamurugu.myweather.network

import io.reactivex.Observable
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Url


interface iApiService {

    @GET()
    fun getWeather(@Url url:String
    ): Observable<String>

}