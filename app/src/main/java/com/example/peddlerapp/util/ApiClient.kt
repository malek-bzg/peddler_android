package com.example.peddlerapp.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL: String = "http://192.168.1.11:3000/api/"

    private val gson =  GsonBuilder().setLenient().create()


    private val httpClient = OkHttpClient.Builder().build()

    private val retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()


    val apiService = retrofit.create(ApiService::class.java)

}