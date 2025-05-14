package com.example.greengeeksweatherapp.activites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greengeeksweatherapp.Adapters.HourlyAdapter
import com.example.greengeeksweatherapp.Domains.Hourly
import com.example.greengeeksweatherapp.R
import com.example.greengeeksweatherapp.search_page
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var adapterHourly: RecyclerView.Adapter<HourlyAdapter.ViewHolder>
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawerlayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initNext7DaysButton()
        initMenu()
        menuHandler()

//        setTodaysWeatherInfo(
//            "snowy",
//            "Snowy",
//            "25C",
//            "28C",
//            "23C",
//            "22%",
//            "2km/h",
//            "18%"
//        )

//        val items:ArrayList<Hourly> = ArrayList()
//        items.add(Hourly("10 pm", "6C", "cloudy_sunny"))
//        items.add(Hourly("11 pm", "5C", "cloudy"))
//        items.add(Hourly("12 pm", "4C", "snowy"))
//        items.add(Hourly("1 am", "5C", "cloudy"))
//
//        initRecyclerView(items)



        fetchWeatherData("Aţ Ţafīlah,JO")



    }

    private fun setTodaysWeatherInfo(WeatherIcon:String="", WeatherName:String="", WeatherTemp:String="", WeatherTempHigh:String="", WeatherTempLow:String="", WeatherRainPer:String="", WeatherWindSpeed:String="",WeatherHumidityPer:String=""){
        val WeatherTodayIcon = findViewById<ImageView>(R.id.WeatherTodayIcon)
        val WeatherTodayName = findViewById<TextView>(R.id.WeatherTodayName)//description
        val WeatherTodayTemp = findViewById<TextView>(R.id.WeatherTodayTemp)
        val WeatherTodayHighLowTemp = findViewById<TextView>(R.id.WeatherTodayHighLowTemp)

        val WeatherTodayRainValue = findViewById<TextView>(R.id.WeatherTodayRainValue)//humidity
        val WeatherTodayWindSpeed = findViewById<TextView>(R.id.WeatherTodayWindSpeed)
        val WeatherTodayHumidityValue = findViewById<TextView>(R.id.WeatherTodayHumidityValue)

        val drawableResourceId: Int = resources.getIdentifier(
            WeatherIcon,
            "drawable",
            packageName
        )

        if (drawableResourceId != 0) {
            WeatherTodayIcon.setImageResource(drawableResourceId)
        } else {
            Toast.makeText(this, "Drawable not found for: $WeatherIcon", Toast.LENGTH_SHORT).show()
        }

        WeatherTodayName.text = WeatherName
        WeatherTodayTemp.text = WeatherTemp
        WeatherTodayHighLowTemp.text = "H:" + WeatherTempHigh + "  L:" + WeatherTempHigh
        WeatherTodayRainValue.text = WeatherRainPer
        WeatherTodayWindSpeed.text = WeatherWindSpeed
        WeatherTodayHumidityValue.text = WeatherHumidityPer

    }


    private fun initDarkThemeSwitch(){
        val navigationView = findViewById<NavigationView>(R.id.nav_view)

        val darkThemeItem = navigationView.menu.findItem(R.id.nav_dark_theme)
        darkThemeItem.title = "Dark Theme"
        val darkThemeLayout = darkThemeItem.actionView
        val darkThemeSwitch = darkThemeLayout?.findViewById<Switch>(R.id.setting_switch)


        // Load preferences
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val isDarkTheme = prefs.getBoolean("dark_theme", false)

        // Set the initial checked state based on the saved preference
        darkThemeSwitch?.isChecked = isDarkTheme
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkTheme)
                AppCompatDelegate.MODE_NIGHT_YES
            else
                AppCompatDelegate.MODE_NIGHT_NO
        )

        darkThemeSwitch?.setOnClickListener {
            val isChecked = darkThemeSwitch.isChecked
            Toast.makeText(
                this,
                "Dark theme is ${if (isChecked) "ON" else "OFF"}",
                Toast.LENGTH_SHORT
            ).show()

            AppCompatDelegate.setDefaultNightMode(
                if (isChecked)
                    AppCompatDelegate.MODE_NIGHT_YES
                else
                    AppCompatDelegate.MODE_NIGHT_NO
            )

            // Save the preference
            val prefs = getSharedPreferences("settings", MODE_PRIVATE)
            prefs.edit() { putBoolean("dark_theme", isChecked) }
        }
    }

    private fun initMetricsSwitch(){
        val navigationView = findViewById<NavigationView>(R.id.nav_view)

        val metricsItem = navigationView.menu.findItem(R.id.nav_metrics)
        metricsItem.title = "Metrics"
        val metricsLayout = metricsItem.actionView
        val metricsSwitch = metricsLayout?.findViewById<Switch>(R.id.setting_switch)

        metricsSwitch?.setOnClickListener {
            val isChecked = metricsSwitch.isChecked
            Toast.makeText(this, "Metrics is ${if (isChecked) "ON" else "OFF"}", Toast.LENGTH_SHORT).show()
            //prefs.edit().putBoolean("metrics", isChecked).apply()
        }
    }

    private fun menuHandler() {
        initDarkThemeSwitch()
        initMetricsSwitch()
    }


    private fun initMenu() {
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerlayout)
        val menuBtn = findViewById<ImageButton>(R.id.menu_button)
        val searchBtn = findViewById<ImageView>(R.id.search_button)

        menuBtn.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)

        }

        searchBtn.setOnClickListener {
            val intent = Intent(this, search_page::class.java)
            startActivity(intent)
        }

    }

    private fun initNext7DaysButton() {
        val next7DaysButton:TextView = findViewById(R.id.nextBtn)
        next7DaysButton.setOnClickListener {
            startActivity(Intent(this, TomorrowActivity::class.java))
        }
    }

    private fun initRecyclerView(items:ArrayList<Hourly>){
        recyclerView = findViewById(R.id.view)
        recyclerView.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false))

        adapterHourly = HourlyAdapter(items)
        recyclerView.adapter = adapterHourly
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

                setTodaysWeatherInfo("snowy",description,temp.toString(),"?","?","%",windSpeed.toString(),humidity.toString())

                val forecastRes=RetroFitclient.api.getForecastData(cityAndCountry=cityCountry, apiKey = "82c97b3355c019e0db0ec722ac742d2f")

                val items:ArrayList<Hourly> = ArrayList()
                var currentDay = SimpleDateFormat("EEEE", Locale.ENGLISH).format(Date(forecastRes.list[0].dt*1000))
                var i=0
                var TT="Today"
                for (it in forecastRes.list) {
                    //val iconf = it.weather[0].icon

                    val tempf =it.main.temp.toInt()
                    val descriptionf = it.weather[0].main
                    val timeStamp = "$TT\n"+SimpleDateFormat("h:mm a", Locale.ENGLISH).format(Date(it.dt*1000))
                    val daytimeStamp = SimpleDateFormat("EEEE", Locale.ENGLISH).format(Date(it.dt*1000))

                    if(currentDay != daytimeStamp){
                        TT="Tomorrow"
                        i+=1
                        if(i==10){
                            break
                        }
                    }

                    items.add(Hourly(timeStamp, tempf.toString(), descriptionf))

                }
                initRecyclerView(items)



            }catch (e: Exception) {
                Log.e("WeatherDebug", "API call failed: ${e.message}", e)
                Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }


}








