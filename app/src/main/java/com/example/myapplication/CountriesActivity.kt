package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit




class CountriesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countries)
        val list: ArrayList<Country> = ArrayList()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.com/v2/")
            .build()
        val recyclerView = findViewById<RecyclerView>(R.id.country)
        recyclerView.layoutManager = LinearLayoutManager(this@CountriesActivity);
        var adapter = CountriesAdapter(this@CountriesActivity, list)
        recyclerView.adapter = adapter
        CoroutineScope(Dispatchers.Main).launch{
            var resp = retrofit.create(RestCountriesApi::class.java).getAllCountries()
            var json = JSONArray(resp.body()?.string())
            for (i in 0 until json.length()){
                var country = JSONObject(json.get(i).toString())
                list.add(Country(country.getString("name"), country.getInt("population"), JSONObject(country.getString("flags")).getString("png")))
            }
            recyclerView.adapter = CountriesAdapter(this@CountriesActivity, list)

        }



    }
}