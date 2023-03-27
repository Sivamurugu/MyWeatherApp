package com.sivamurugu.myweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tvsmotor.digiapp.dmsdigi.repositories.WeatherRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repo: WeatherRepo
) : ViewModel() {
    protected val compositeDisposable = CompositeDisposable()

    protected val errorMutableLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String>
        get() = errorMutableLiveData

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    private val weatherMutableLiveData = MutableLiveData<String>()
    val weatherLiveData: LiveData<String>
        get() = weatherMutableLiveData


    fun getWeather(url: String) {
        compositeDisposable.add(
            repo.getWeather(url)
                .subscribe(
                    { response ->
                        weatherMutableLiveData.value=response
                    },
                    { exception ->
                        errorMutableLiveData.value =
                            "#error msg=${exception.message ?: ""}"
                    })
        )
    }

}