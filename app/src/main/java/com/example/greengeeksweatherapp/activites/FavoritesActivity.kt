package com.example.greengeeksweatherapp.activites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greengeeksweatherapp.Adapters.CityAdapter
import com.example.greengeeksweatherapp.Adapters.TomorrowAdapter
import com.example.greengeeksweatherapp.Domains.CityData
import com.example.greengeeksweatherapp.Domains.TomorrowDomain
import com.example.greengeeksweatherapp.R
import com.google.android.material.textfield.TextInputEditText
import androidx.appcompat.widget.SearchView
import androidx.core.content.edit
import java.util.Locale
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FavoritesActivity : AppCompatActivity() {
    private lateinit var adapterCity: CityAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView

    private var resultsList:ArrayList<CityData> = ArrayList()
    private lateinit var regions: List<CityData>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_favorites)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }

        val items:ArrayList<CityData> = ArrayList()

        for (region in FavoriteManager.getAllFavorites(this)) {
            items.add(CityData(region,"",""))
        }

        initRecyclerView(items)

        val back_btn = findViewById<ConstraintLayout>(R.id.back_btn)
        back_btn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
    private fun getStates(): String {
        val inputStream = resources.openRawResource(R.raw.states)
        return inputStream.bufferedReader().use { it.readText() }
    }

    private fun parseRegions(): List<CityData> {
        val json = getStates()
        val gson = Gson()
        val type = object : TypeToken<List<CityData>>() {}.type
        return gson.fromJson(json, type)
    }

    private fun initRecyclerView(items:ArrayList<CityData>){
        resultsList = items
        recyclerView = findViewById(R.id.search_results)
        searchView = findViewById(R.id.city)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapterCity = CityAdapter(items) { selectedCity ->
            onCityItemClicked(selectedCity)
        }

        recyclerView.adapter = adapterCity


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })

    }
    private fun setCityName(name:String){
        // Save the preference
        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        prefs.edit() { putString("currentCity", name) }
    }

    private fun onCityItemClicked(selectedCity: CityData) {
        Toast.makeText(this, "Selected: ${selectedCity.name}", Toast.LENGTH_SHORT).show()
        setCityName(selectedCity.name)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun filterList(query: String?) {
        if(query != null){
            val filteredList = ArrayList<CityData>()
            for(i in resultsList){
                if(i.name.lowercase(Locale.ROOT).contains(query)){
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()){
                Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show()
            }else{
                adapterCity.setFilteredList(filteredList)
            }
        }
    }


}

