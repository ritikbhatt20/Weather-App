package com.example.myapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("weather")
    fun getweather(@Query("q") cityname: String,
                   @Query("appid") apikey: String): Call<MyData>
}