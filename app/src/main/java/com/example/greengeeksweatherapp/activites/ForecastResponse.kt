package com.example.greengeeksweatherapp.activites

data class ForecastResponse(
    val list: List<ForecastList>
)

data class ForecastList(
    val dt: Long,
    val main: MainF,
    val weather: List<WeatherF>,
    val wind: WindF,
    val dt_txt: String
)

data class MainF(
    val temp: Double,

)

data class WeatherF(
    val main: String,
    val description: String,//not used
    val icon: String//not used for now
)
//not used
data class WindF(
    val speed: Double
)
