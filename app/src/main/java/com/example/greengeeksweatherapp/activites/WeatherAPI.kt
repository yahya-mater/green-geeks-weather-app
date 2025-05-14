package com.example.greengeeksweatherapp.activites

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("data/2.5/weather")
    suspend fun getWeatherData(
        @Query("q")cityAndCountry:String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ):WeatherResponse

    @GET("data/2.5/forecast")
    suspend fun getForecastData(
        @Query("q")cityAndCountry:String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ):ForecastResponse

}