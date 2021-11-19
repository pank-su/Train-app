package com.example.myapplication

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {
    @POST("login")
    suspend fun login(@Body requestBody: RequestBody): Response<ResponseBody>
    @POST("register")
    suspend fun register(@Body requestBody: RequestBody): Response<ResponseBody>
}