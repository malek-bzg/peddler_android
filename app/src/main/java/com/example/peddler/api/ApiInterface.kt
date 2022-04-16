package com.example.peddler.api

import android.util.Log
//import com.example.peddler.BuildConfig
import com.example.peddler.api.models.LoginResponse
import com.example.peddler.api.models.RegisterResponse
import com.example.peddler.api.models.User
import com.intuit.sdp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface ApiInterface {

    @POST("api/user/register")
    fun register(@Body user: User): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("api/user/login")
    fun login(@Field("email") email: String, @Field("password") password: String): Call<LoginResponse>

    companion object {
        private val BASE_URL =//"http://peddler:peddler-cred@cluster0.ialfx.mongodb.net/myFirstDatabase?retryWrites=true&w=majority"
             "http://192.168.1.11:3000/"


        private val loggingInterceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val api: ApiInterface = createRetrofit().create(ApiInterface::class.java)

        private fun getClient() : OkHttpClient {
            val client  = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)

            if (BuildConfig.DEBUG) {
                client.apply { this.addInterceptor(loggingInterceptor) }
            }
            return client.build()
        }

        private fun createRetrofit(): Retrofit {

            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .baseUrl(BASE_URL)
                .build()
        }
    }
}