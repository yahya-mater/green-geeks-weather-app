package com.example.greengeeksweatherapp.activites

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greengeeksweatherapp.Adapters.TomorrowAdapter
import com.example.greengeeksweatherapp.Domains.TomorrowDomain
import com.example.greengeeksweatherapp.R

class TomorrowActivity : AppCompatActivity() {

    private lateinit var adapterTomorrow: RecyclerView.Adapter<TomorrowAdapter.ViewHolder>
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tomorrow)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initRecyclerView()
        setVariable()
    }

    private fun setVariable() {
        val backButton: ConstraintLayout = findViewById(R.id.back_btn)
        backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun initRecyclerView(){
        val items:ArrayList<TomorrowDomain> = ArrayList()
        items.add(TomorrowDomain("sat", "cloudy", "cloudy", 25, 10))
        items.add(TomorrowDomain("sat", "cloudy", "cloudy", 25, 10))
        items.add(TomorrowDomain("sat", "cloudy", "cloudy", 25, 10))
        items.add(TomorrowDomain("sat", "cloudy", "cloudy", 25, 10))
        items.add(TomorrowDomain("sat", "cloudy", "cloudy", 25, 10))
        items.add(TomorrowDomain("sat", "cloudy", "cloudy", 25, 10))


        recyclerView = findViewById(R.id.view2)
        recyclerView.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))

        adapterTomorrow = TomorrowAdapter(items)
        recyclerView.adapter = adapterTomorrow
        /**/

    }
}