package com.example.greengeeksweatherapp.activites

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greengeeksweatherapp.Adapters.HourlyAdapter
import com.example.greengeeksweatherapp.Domains.Hourly
import com.example.greengeeksweatherapp.R

class MainActivity : AppCompatActivity() {
    private lateinit var adapterHourly: RecyclerView.Adapter<HourlyAdapter.ViewHolder>
    private lateinit var recyclerView:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initRecyclerView()
        setVariable()
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