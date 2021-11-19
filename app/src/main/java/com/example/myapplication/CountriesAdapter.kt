package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CountriesAdapter(val context: Context, var data: ArrayList<Country>) :
    RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.activity_country, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        // holder.flagimg = item.flagImage
        holder.name.text = item.name
        holder.population.text = "Population: ${item.population} peoples"
        Picasso.get().load(item.flagImage).into(holder.flagimg);
    }

    override fun getItemCount(): Int {
        return data.size
    }



    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var name: TextView = v.findViewById(R.id.Name)
        var population: TextView = v.findViewById(R.id.Population)
        var flagimg: ImageView = v.findViewById(R.id.flagimg)
    }
}