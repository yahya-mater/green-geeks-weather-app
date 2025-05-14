package com.example.greengeeksweatherapp.activites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greengeeksweatherapp.Adapters.TomorrowAdapter
import com.example.greengeeksweatherapp.Domains.Hourly
import com.example.greengeeksweatherapp.Domains.TomorrowDomain
import com.example.greengeeksweatherapp.R
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TomorrowActivity : AppCompatActivity() {

    private lateinit var adapterTomorrow: RecyclerView.Adapter<TomorrowAdapter.ViewHolder>
    private lateinit var recyclerView: RecyclerView


    private var tempType:String = "°C"
    private var speedType:String = "km\\h"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tomorrow)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initBackButton()

        fetchWeatherData("Aţ Ţafīlah,JO")
    }

    private fun initBackButton() {
        val backButton: ConstraintLayout = findViewById(R.id.back_btn)
        backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun initRecyclerView(items:ArrayList<TomorrowDomain>){


        recyclerView = findViewById(R.id.view2)
        recyclerView.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))

        adapterTomorrow = TomorrowAdapter(items)
        recyclerView.adapter = adapterTomorrow
        /**/

    }

    private fun fetchWeatherData(cityCountry:String){
        lifecycleScope.launch{
            try {
                val response=RetroFitclient.api.getWeatherData(cityAndCountry=cityCountry, apiKey = "82c97b3355c019e0db0ec722ac742d2f")
                Log.d("WeatherDebug", "API response: ${response}")


                val temp = response.main.temp.toInt()
                val pressure = response.main.pressure
                val humidity = response.main.humidity
                val Maindescription = response.weather[0].main//main description(e.g."Rain")
                val icon = response.weather[0].icon
                val description = response.weather[0].description//detailed description(e.g."light Rain ")
                val windSpeed = response.wind.speed
                val sunrise = response.sys.sunrise
                val sunset = response.sys.sunset

                setTomorrowWeatherInfo(Maindescription.toLowerCase().replace(" ","_"),description,temp.toString(),"?","?","0%",windSpeed.toString(),humidity.toString()+"%")

                val forecastRes=RetroFitclient.api.getForecastData(cityAndCountry=cityCountry, apiKey = "82c97b3355c019e0db0ec722ac742d2f")

                val items:ArrayList<TomorrowDomain> = ArrayList()
                var currentDay = SimpleDateFormat("EEEE", Locale.ENGLISH).format(Date(forecastRes.list[0].dt*1000))

                for (it in forecastRes.list) {
                    //val iconf = it.weather[0].icon

                    val tempf =it.main.temp.toInt()
                    val descriptionf = it.weather[0].main
                    val timeStamp = SimpleDateFormat("h:mm a", Locale.ENGLISH).format(Date(it.dt*1000))
                    val daytimeStamp = SimpleDateFormat("EEEE", Locale.ENGLISH).format(Date(it.dt*1000))

                    items.add(TomorrowDomain(daytimeStamp, descriptionf.toLowerCase().replace(" ","_") ,descriptionf.toLowerCase().replace(" ","_"),"",tempf.toString() + tempType))
                    Log.e("WeatherDebug", "descriptionf failed: ${descriptionf.toLowerCase().replace(" ","_")}")
                }
                initRecyclerView(items)



            }catch (e: Exception) {
                Log.e("WeatherDebug", "API call failed: ${e.message}", e)
                Toast.makeText(this@TomorrowActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setTomorrowWeatherInfo(
        WeatherIcon:String="",
        WeatherName:String="",
        WeatherTemp:String="",
        WeatherTempHigh:String="",
        WeatherTempLow:String="",
        WeatherRainPer:String="",
        WeatherWindSpeed:String="",
        WeatherHumidityPer:String=""
    ){
        val WeatherTomorrowIcon = findViewById<ImageView>(R.id.WeatherTomorrowIcon)
        val WeatherTomorrowName = findViewById<TextView>(R.id.WeatherTomorrowName)//description
        val WeatherTomorrowTemp = findViewById<TextView>(R.id.WeatherTomorrowTemp)
        //val WeatherTomorrowHighLowTemp = findViewById<TextView>(R.id.WeatherTomorrowHighLowTemp)

        val WeatherTomorrowRainValue = findViewById<TextView>(R.id.WeatherTomorrowRainValue)//humidity
        val WeatherTomorrowWindSpeed = findViewById<TextView>(R.id.WeatherTomorrowWindSpeed)
        val WeatherTomorrowHumidityValue = findViewById<TextView>(R.id.WeatherTomorrowHumidityValue)

        val drawableResourceId: Int = resources.getIdentifier(
            WeatherIcon,
            "drawable",
            packageName
        )

        if (drawableResourceId != 0) {
            WeatherTomorrowIcon.setImageResource(drawableResourceId)
        } else {
            Toast.makeText(this, "Drawable not found for: $WeatherIcon", Toast.LENGTH_SHORT).show()
        }

        WeatherTomorrowName.text = WeatherName
        WeatherTomorrowTemp.text = WeatherTemp + tempType
        //WeatherTodayHighLowTemp.text = "H:" + WeatherTempHigh + "  L:" + WeatherTempHigh
        WeatherTomorrowRainValue.text = WeatherRainPer
        WeatherTomorrowWindSpeed.text = WeatherWindSpeed + speedType
        WeatherTomorrowHumidityValue.text = WeatherHumidityPer
    }


}