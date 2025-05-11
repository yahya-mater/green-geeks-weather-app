package com.example.greengeeksweatherapp.activites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greengeeksweatherapp.Adapters.HourlyAdapter
import com.example.greengeeksweatherapp.Domains.Hourly
import com.example.greengeeksweatherapp.R
import com.google.android.material.navigation.NavigationView

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

        initRecyclerView()
        setVariable()
        initMenu()
        menuHandler()


    }


    private fun menuHandler() {
        val navigationView = findViewById<NavigationView>(R.id.nav_view)

        val darkThemeItem = navigationView.menu.findItem(R.id.nav_dark_theme)
        darkThemeItem.title = "Dark Theme"
        val darkThemeLayout = darkThemeItem.actionView
        val darkThemeSwitch = darkThemeLayout?.findViewById<Switch>(R.id.setting_switch)

        val metricsItem = navigationView.menu.findItem(R.id.nav_metrics)
        metricsItem.title = "Metrics"
        val metricsLayout = metricsItem.actionView
        val metricsSwitch = metricsLayout?.findViewById<Switch>(R.id.setting_switch)

        //val prefs = getSharedPreferences("settings", MODE_PRIVATE)

        //darkThemeSwitch?.setOnCheckedChangeListener(null)
        //darkThemeSwitch?.isChecked = prefs.getBoolean("dark_theme", false)
        //darkThemeSwitch?.setOnTouchListener { _, _ -> true } // prevent drawer close
        darkThemeSwitch?.setOnClickListener {
            val isChecked = darkThemeSwitch.isChecked
            Toast.makeText(this, "Dark theme is ${if (isChecked) "ON" else "OFF"}", Toast.LENGTH_SHORT).show()
            //AppCompatDelegate.setDefaultNightMode(
            //    if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            //)
            //prefs.edit().putBoolean("dark_theme", isChecked).apply()
        }

        //metricsSwitch?.setOnCheckedChangeListener(null)
        //metricsSwitch?.isChecked = prefs.getBoolean("metrics", false)
        //metricsSwitch?.setOnTouchListener { _, _ -> true }
        metricsSwitch?.setOnClickListener {
            val isChecked = metricsSwitch.isChecked
            Toast.makeText(this, "Metrics is ${if (isChecked) "ON" else "OFF"}", Toast.LENGTH_SHORT).show()
            //prefs.edit().putBoolean("metrics", isChecked).apply()
        }
    }


    private fun initMenu() {
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerlayout)
        val menuBtn = findViewById<ImageButton>(R.id.menu_button)

        menuBtn.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)

        }

    }

    private fun setVariable() {
        val next7DaysButton:TextView = findViewById(R.id.nextBtn)
        next7DaysButton.setOnClickListener {
            startActivity(Intent(this, TomorrowActivity::class.java))
        }
    }

    private fun initRecyclerView(){
        val items:ArrayList<Hourly> = ArrayList()
        items.add(Hourly("10 pm", 6, "cloudy"))
        items.add(Hourly("10 pm", 6, "cloudy"))
        items.add(Hourly("10 pm", 6, "cloudy"))
        items.add(Hourly("10 pm", 6, "cloudy"))


        recyclerView = findViewById(R.id.view)
        recyclerView.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false))

        adapterHourly = HourlyAdapter(items)
        recyclerView.adapter = adapterHourly
        /**/

    }

}