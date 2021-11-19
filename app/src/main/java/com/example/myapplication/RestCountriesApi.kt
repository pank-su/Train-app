package com.example.myapplication

import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RestCountriesApi {
    @GET("all?fields=name,population,flags")
    suspend fun getAllCountries(): Response<ResponseBody>
}