package com.example.greengeeksweatherapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.greengeeksweatherapp.Domains.CityData
import com.example.greengeeksweatherapp.R

class CityAdapter(var cList:List<CityData>): RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    inner class CityViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        val nameTextView:TextView = itemView.findViewById(R.id.resultTxt)
    }

    fun setFilteredList(cList:List<CityData>){
        this.cList = cList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_result_item, parent, false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.nameTextView.text = cList[position].Name
    }

    override fun getItemCount(): Int {
        return cList.size
    }
}