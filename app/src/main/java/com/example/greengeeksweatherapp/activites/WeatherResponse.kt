package com.example.greengeeksweatherapp.activites


data class WeatherResponse(
    val Coord:coord,
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind,
    val sys: Sys
)

data class coord(
    val lon:Long,
    val lat:Long
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val pressure: Int,
    val humidity: Int
)

data class Weather(
    val id:Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Wind(
    val speed: Double
)

data class Sys(
    val sunrise: Long,
    val sunset: Long
)
